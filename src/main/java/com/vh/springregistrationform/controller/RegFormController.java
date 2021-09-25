package com.vh.springregistrationform.controller;

import com.vh.springregistrationform.dto.NoteDTO;
import com.vh.springregistrationform.entity.Note;
import com.vh.springregistrationform.service.impl.RegFormServiceImpl;
import com.vh.springregistrationform.validator.NoteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegFormController {

    private final RegFormServiceImpl regFormService;

    private final NoteValidator noteValidator;

    private final ModelMapper mapper;

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("noteForm", new NoteDTO());

        return "registration/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("noteForm") NoteDTO noteForm, BindingResult bindingResult) {
        noteValidator.validate(noteForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        Note note = mapper.map(noteForm, Note.class);

        regFormService.save(note);

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "error.login");

        if (logout != null)
            model.addAttribute("message", "logged.out");

        return "registration/login";
    }

    @GetMapping("/all-notes")
    public String getAllNotes(Model model){

        log.debug("findAllNotes");

        List<Note> notes = regFormService.findAllNotes();

        model.addAttribute("notes", mapper.map(notes, (new TypeToken<List<NoteDTO>>(){}).getType()));

        return "allNotes";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}