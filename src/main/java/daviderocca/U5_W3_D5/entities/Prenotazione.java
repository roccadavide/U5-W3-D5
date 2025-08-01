package daviderocca.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate dataPrenotazione;

    @ManyToOne
    @JoinColumn(name = "username_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;


}
