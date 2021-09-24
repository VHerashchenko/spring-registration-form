package com.vh.springregistrationform.controller;

import com.vh.springregistrationform.dto.NoteDTO;
import com.vh.springregistrationform.entity.Note;
import com.vh.springregistrationform.service.RegFormService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RegFormController {

    private final RegFormService regFormService;

    private final ModelMapper mapper;

    @Autowired
    public RegFormController(RegFormService regFormService, ModelMapper mapper) {
        this.regFormService = regFormService;
        this.mapper = mapper;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("noteForm", new NoteDTO());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("noteForm") NoteDTO noteForm) {

        Note note = mapper.map(noteForm, Note.class);

        log.info("{}", note);

        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping("/")
    public String mainPage(Model model){
        return "index";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}