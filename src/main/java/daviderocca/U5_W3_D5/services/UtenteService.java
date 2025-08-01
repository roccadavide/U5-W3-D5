package daviderocca.U5_W3_D5.services;

import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.enums.TipoUtente;
import daviderocca.U5_W3_D5.exceptions.BadRequestException;
import daviderocca.U5_W3_D5.exceptions.NotFoundException;
import daviderocca.U5_W3_D5.payloads.NewUtenteDTO;
import daviderocca.U5_W3_D5.repositories.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Utente> getUtente(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return utenteRepository.findAll(pageable);
    }

    public Utente saveUtente(NewUtenteDTO payload){

        if(utenteRepository.existsByUsername(payload.username())) throw new BadRequestException("Username già in uso! Scegline un altro!");
        if(utenteRepository.existsByEmail(payload.email())) throw new BadRequestException("Email già in uso! Scegline un altra!");

        Utente newUtente = new Utente();
        newUtente.setUsername(payload.username());
        newUtente.setNome(payload.nome());
        newUtente.setCognome(payload.cognome());
        newUtente.setEmail(payload.email());
        newUtente.setPassword(bcrypt.encode(payload.password()));
        newUtente.setTipoUtente(TipoUtente.UTENTE_NORMALE);


        Utente savedUtente = this.utenteRepository.save(newUtente);

        log.info("L'utente con username: " + savedUtente.getUsername() + " è stato correttamente salvato nel DB!");

        return savedUtente;
    }

    public Utente findUtenteByUsername(String username) {
        return this.utenteRepository.findById(username).orElseThrow(() -> new NotFoundException(username));
    }

    public Utente findUtenteByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Utente findUtenteByUsernameAndUpdate(String username, NewUtenteDTO payload) {
        Utente found = findUtenteByUsername(username);

        if(utenteRepository.existsByUsername(payload.username())) throw new BadRequestException("Username già in uso! Scegline un altro!");
        if(utenteRepository.existsByEmail(payload.email())) throw new BadRequestException("Email già in uso! Scegline un altra!");

        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());

        Utente modifiedUtente = this.utenteRepository.save(found);

        log.info("L'utente con username: " + modifiedUtente.getUsername() + " è stato modificato correttamente!");

        return modifiedUtente;

    }

    public void findUtenteByUsernameAndDelete(String username) {
        Utente found = findUtenteByUsername(username);
        this.utenteRepository.delete(found);
        log.info("L'utente con username: " + found.getUsername() + " è stato cancellato correttamente dal DB!");
    }

}
