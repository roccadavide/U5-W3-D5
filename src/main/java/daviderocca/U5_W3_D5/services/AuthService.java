package daviderocca.U5_W3_D5.services;

import daviderocca.U5_W3_D5.exceptions.UnauthorizedException;
import daviderocca.U5_W3_D5.payloads.LoginDTO;
import daviderocca.U5_W3_D5.tools.JWTTools;
import daviderocca.U5_W3_D5.entities.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialAndGenerateToken(LoginDTO body) {
        Utente found = this.utenteService.findUtenteByEmail(body.email());

        if(bcrypt.matches(body.password(), found.getPassword())) {
            String accessToken = jwtTools.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
