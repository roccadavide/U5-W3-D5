package daviderocca.U5_W3_D5.controller;

import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.exceptions.ValidationException;
import daviderocca.U5_W3_D5.payloads.LoginDTO;
import daviderocca.U5_W3_D5.payloads.LoginRespDTO;
import daviderocca.U5_W3_D5.payloads.NewUtenteDTO;
import daviderocca.U5_W3_D5.payloads.NewUtenteRespDTO;
import daviderocca.U5_W3_D5.services.AuthService;
import daviderocca.U5_W3_D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authService.checkCredentialAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }


   @PostMapping("/register")
   public NewUtenteRespDTO save(@RequestBody @Validated NewUtenteDTO payload, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Utente newDipendente = this.utenteService.saveUtente(payload);
            return new NewUtenteRespDTO(newDipendente.getUsername());
        }
   }


}
