package daviderocca.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titolo;

    private String descrizione;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    private String luogo;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "username_utente")
    private Utente utente;


    public Evento(String titolo, String descrizione, LocalDate dataEvento, String luogo, int postiDisponibili) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
    }
}
