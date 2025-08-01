package daviderocca.U5_W3_D5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUtenteDTO (@NotEmpty(message = "L'username è obbligatorio")
@Size(min = 2, max = 30, message = "L'username deve essere di lunghezza compresa tra 2 e 30")
String username,
@NotEmpty(message = "Il nome è obbligatorio")
@Size(min = 2, max = 30, message = "Il nome deve essere di lunghezza compresa tra 2 e 30")
String nome,
@NotEmpty(message = "Il cognome è obbligatorio")
@Size(min = 2, max = 30, message = "Il cognome deve essere di lunghezza compresa tra 2 e 30")
String cognome,
@NotEmpty(message = "L'email è obbligatoria")
@Email(message = "L'indirizzo email inserito non è nel formato giusto")
String email,
@NotEmpty(message = "La password è obbligatoria")
@Size(min = 2, max = 30, message = "La password deve essere di lunghezza compresa tra 2 e 30")
String password) {
}
