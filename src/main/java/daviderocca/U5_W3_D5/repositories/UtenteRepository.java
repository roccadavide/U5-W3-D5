package daviderocca.U5_W3_D5.repositories;

import daviderocca.U5_W3_D5.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Utente> findByEmail(String email);
}
