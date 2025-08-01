package daviderocca.U5_W3_D5.controller;

import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.exceptions.BadRequestException;
import daviderocca.U5_W3_D5.payloads.NewUtenteDTO;
import daviderocca.U5_W3_D5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Page<Utente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "username") String sortBy) {
        return this.utenteService.getUtente(page, size, sortBy);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Utente getUtenteByUsername(@PathVariable String username) { return this.utenteService.findUtenteByUsername(username);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Utente createUtente(@RequestBody @Validated NewUtenteDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.utenteService.saveUtente(body);
    }


    //Creo questo per fare in modo di poter creare un organizzatore non dal DB
    @PostMapping("/pathsegreto")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createOrganizzatore(@RequestBody @Validated NewUtenteDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.utenteService.saveOrganizzatore(body);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Utente findUtenteByUsernameAndUpdate(@PathVariable String username, @RequestBody @Validated NewUtenteDTO body) {
        return this.utenteService.findUtenteByUsernameAndUpdate(username, body);
    }


    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public void deleteUtenteByUsername(@PathVariable String username) {
        this.utenteService.findUtenteByUsernameAndDelete(username);
    }


    @GetMapping("/me")
    public Utente getOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    @PutMapping("/me")
    public Utente updateOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated NewUtenteDTO payload) {
        return this.utenteService.findUtenteByUsernameAndUpdate(currentAuthenticatedUtente.getUsername(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utenteService.findUtenteByUsernameAndDelete(currentAuthenticatedUser.getUsername());
    }

}
