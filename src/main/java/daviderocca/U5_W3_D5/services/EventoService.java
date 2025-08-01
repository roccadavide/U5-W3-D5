package daviderocca.U5_W3_D5.services;

import daviderocca.U5_W3_D5.entities.Evento;
import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.exceptions.NotFoundException;
import daviderocca.U5_W3_D5.payloads.NewEventoDTO;
import daviderocca.U5_W3_D5.repositories.EventoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteService utenteService;

    public Page<Evento> getEventi(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventoRepository.findAll(pageable);
    }

    public Evento saveEvento(NewEventoDTO payload){

        Utente utenteAssociato = utenteService.findUtenteByUsername(payload.usernameUtente());

        Evento newEvento = new Evento();
        newEvento.setTitolo(payload.titolo());
        newEvento.setDescrizione(payload.descrizione());
        newEvento.setDataEvento(payload.dataEvento());
        newEvento.setLuogo(payload.luogo());
        newEvento.setPostiDisponibili(payload.postiDisponibili());
        newEvento.setUtente(utenteAssociato);

        Evento savedEvento = this.eventoRepository.save(newEvento);

        log.info("L'evento con ID: " + savedEvento.getId() + " è stato correttamente salvato nel DB!");

        return savedEvento;
    }

    public Evento saveEvento2(Evento evento) {
        return eventoRepository.save(evento);
    }


    public Evento findEventoById(Long eventoId) {
        return this.eventoRepository.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
    }

    public Evento findEventoByIdAndUpdate(Long eventoId, NewEventoDTO payload) {
        Evento found = findEventoById(eventoId);

        Utente utenteAssociato = utenteService.findUtenteByUsername(payload.usernameUtente());

        Evento newEvento = new Evento();
        newEvento.setTitolo(payload.titolo());
        newEvento.setDescrizione(payload.descrizione());
        newEvento.setDataEvento(payload.dataEvento());
        newEvento.setLuogo(payload.luogo());
        newEvento.setPostiDisponibili(payload.postiDisponibili());
        newEvento.setUtente(utenteAssociato);

        Evento modifiedEvento = this.eventoRepository.save(found);

        log.info("L'evento con ID: " + modifiedEvento.getId() + " è stato modificato correttamente!");

        return modifiedEvento;

    }

    public void findEventoByIdAndDelete(Long eventoId) {
        Evento found = findEventoById(eventoId);
        this.eventoRepository.delete(found);
        log.info("L'evento con ID: " + found.getId() + " è stato cancellato correttamente dal DB!");
    }

    public Evento assegnaUtente(Long eventoId, String username) {
        Evento evento = findEventoById(eventoId);

        Utente utente = utenteService.findUtenteByUsername(username);

        evento.setUtente(utente);
        return eventoRepository.save(evento);
    }

}
