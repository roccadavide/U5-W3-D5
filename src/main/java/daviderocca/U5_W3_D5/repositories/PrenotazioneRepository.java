package daviderocca.U5_W3_D5.repositories;

import daviderocca.U5_W3_D5.entities.Evento;
import daviderocca.U5_W3_D5.entities.Prenotazione;
import daviderocca.U5_W3_D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByEventoAndUtente(Evento evento, Utente utente);

    List<Prenotazione> findByUtente(Utente utente);
}
