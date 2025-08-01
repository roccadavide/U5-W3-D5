package daviderocca.U5_W3_D5.services;

import daviderocca.U5_W3_D5.entities.Evento;
import daviderocca.U5_W3_D5.entities.Prenotazione;
import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.exceptions.BadRequestException;
import daviderocca.U5_W3_D5.exceptions.NotFoundException;
import daviderocca.U5_W3_D5.payloads.NewPrenotazioneDTO;
import daviderocca.U5_W3_D5.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrenotazioneService {


    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UtenteService utenteService;

    public Page<Prenotazione> getPrenotazione(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(NewPrenotazioneDTO payload){

        Evento eventoAssociato = eventoService.findEventoById(payload.idEvento());
        Utente utenteAssociato = utenteService.findUtenteByUsername(payload.usernameUtente());

        if (eventoAssociato.getPostiDisponibili() <= 0) {
            throw new RuntimeException("Posti esauriti per questo evento");
        }

        boolean alreadyBooked = prenotazioneRepository.existsByEventoAndUtente(eventoAssociato, utenteAssociato);
        if (alreadyBooked) {
            throw new RuntimeException("Hai già prenotato questo evento");
        }

        Prenotazione newPrenotazione = new Prenotazione();
        newPrenotazione.setDataPrenotazione(payload.dataPrenotazione() != null
                ? payload.dataPrenotazione()
                : LocalDate.now());
        newPrenotazione.setUtente(utenteAssociato);
        newPrenotazione.setEvento(eventoAssociato);

        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(newPrenotazione);

        eventoAssociato.setPostiDisponibili(eventoAssociato.getPostiDisponibili() - 1);
        eventoService.saveEvento2(eventoAssociato);

        log.info("La prenotazione con ID: " + savedPrenotazione.getId() + " è stata correttamente salvata nel DB!");

        return savedPrenotazione;
    }


    public Prenotazione findPrenotazioneById(Long prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findPrenotazioneByIdAndUpdate(Long prenotazioneId, NewPrenotazioneDTO payload) {
        Prenotazione found = findPrenotazioneById(prenotazioneId);

        Evento eventoAssociato = eventoService.findEventoById(payload.idEvento());
        Utente utenteAssociato = utenteService.findUtenteByUsername(payload.usernameUtente());

        found.setDataPrenotazione(payload.dataPrenotazione());
        found.setUtente(utenteAssociato);
        found.setEvento(eventoAssociato);

        Prenotazione modifiedPrenotazione = this.prenotazioneRepository.save(found);

        log.info("La prenotazione con ID: " + modifiedPrenotazione.getId() + " è stata modificata correttamente!");

        return modifiedPrenotazione;

    }

    public void findPrenotazioneByIdAndDelete(Long prenotazioneId) {
        Prenotazione found = findPrenotazioneById(prenotazioneId);
        this.prenotazioneRepository.delete(found);
        log.info("La prenotazione con ID: " + found.getId() + " è stata cancellata correttamente dal DB!");
    }

    public List<Prenotazione> findPrenotazioniByUtente(String username) {
        Utente found = utenteService.findUtenteByUsername(username);
        return prenotazioneRepository.findByUtente(found);
    }

}
