package daviderocca.U5_W3_D5.controller;

import daviderocca.U5_W3_D5.entities.Evento;
import daviderocca.U5_W3_D5.exceptions.BadRequestException;
import daviderocca.U5_W3_D5.payloads.NewEventoDTO;
import daviderocca.U5_W3_D5.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public Page<Evento> getEventi(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "eventoId") String sortBy) {
        return this.eventoService.getEventi(page, size, sortBy);
    }

    @GetMapping("/{eventoId}")
    public Evento getEventoById(@PathVariable long eventoId) { return this.eventoService.findEventoById(eventoId);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento createEvento(@RequestBody @Validated NewEventoDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.eventoService.saveEvento(body);
    }

    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE_EVENTI')")
    public Evento findEventoByIdAndUpdate(@PathVariable long eventoId, @RequestBody @Validated NewEventoDTO body) {
        return this.eventoService.findEventoByIdAndUpdate(eventoId, body);
    }


    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAutority('ORGANIZZATORE_EVENTI')")
    public void deleteEventoById(@PathVariable long eventoId) {
        this.eventoService.findEventoByIdAndDelete(eventoId);
    }

}
