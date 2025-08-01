package daviderocca.U5_W2_D5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("La risorsa con id " + id + " non è stata trovata!");
    }
    public NotFoundException(String username) {
        super("La risorsa con username " + username + " non è stata trovata!");
    }
}