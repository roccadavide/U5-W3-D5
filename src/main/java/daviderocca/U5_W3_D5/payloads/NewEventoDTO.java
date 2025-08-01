package daviderocca.U5_W3_D5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEventoDTO(@NotEmpty(message = "Il titolo è obbligatorio")
                           @Size(min = 2, max = 30, message = "Il titolo deve essere di lunghezza compresa tra 2 e 30")
                           String titolo,
                           @NotNull(message = "La descrizione è obbligatoria")
                           @Size(min = 2, max = 100, message = "La descrizione deve essere di lunghezza compresa tra 2 e 100")
                           String descrizione,
                           @NotNull(message = "La data dell'evento è obbligatoria")
                           LocalDate dataEvento,
                           @NotNull(message = "il luogo è obbligatorio")
                           @Size(min = 2, max = 20, message = "Il luogo deve essere di lunghezza compresa tra 2 e 100")
                           String luogo,
                           @NotNull(message = "I posti disponibili sono obbligatori")
                           int postiDisponibili,
                           @NotNull(message = "L'username del dipendente è obbligatorio")
                           String usernameUtente) {
}
