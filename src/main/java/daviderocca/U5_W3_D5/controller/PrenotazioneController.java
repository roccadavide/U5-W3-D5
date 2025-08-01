package daviderocca.U5_W3_D5.controller;

import daviderocca.U5_W3_D5.entities.Prenotazione;
import daviderocca.U5_W3_D5.entities.Utente;
import daviderocca.U5_W3_D5.exceptions.BadRequestException;
import daviderocca.U5_W3_D5.payloads.NewPrenotazioneDTO;
import daviderocca.U5_W3_D5.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;



    @GetMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioneService.getPrenotazione(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getPrenotazioneById(@PathVariable Long prenotazioneId) { return this.prenotazioneService.findPrenotazioneById(prenotazioneId);}


    @GetMapping("/me")
    public List<Prenotazione> getMyPrenotations(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        List<Prenotazione> found = prenotazioneService.findPrenotazioniByUtente(currentAuthenticatedUtente.getUsername());
        return found;
    }

    @PostMapping("/{eventoId}/prenota")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@RequestBody @Validated NewPrenotazioneDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.prenotazioneService.savePrenotazione(body);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione findPrenotazioneByIdAndUpdate(@PathVariable Long prenotazioneId, @RequestBody @Validated NewPrenotazioneDTO body) {
        return this.prenotazioneService.findPrenotazioneByIdAndUpdate(prenotazioneId, body);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazioneById(@PathVariable Long prenotazioneId) {
        this.prenotazioneService.findPrenotazioneByIdAndDelete(prenotazioneId);
    }

}

