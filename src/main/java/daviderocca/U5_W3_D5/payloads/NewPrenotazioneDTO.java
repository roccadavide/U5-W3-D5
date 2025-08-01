package daviderocca.U5_W3_D5.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewPrenotazioneDTO(@NotNull(message = "La data di prenotazione è obbligatoria!")
                                 LocalDate dataPrenotazione,
                                 @NotNull(message = "L'ID dell'evento è obbligatorio")
                                 Long idEvento,
                                 @NotNull(message = "L'username dell'utente è obbligatorio")
                                 String usernameUtente) {
}
