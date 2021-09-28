package com.vh.springregistrationform.controller;

import com.vh.springregistrationform.dto.UserDTO;
import com.vh.springregistrationform.entity.User;
import com.vh.springregistrationform.service.SecurityService;
import com.vh.springregistrationform.service.impl.RegFormServiceImpl;
import com.vh.springregistrationform.validator.UserValidator;
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

    private final UserValidator noteValidator;

    private final ModelMapper mapper;

    private final SecurityService securityService;

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
        model.addAttribute("noteForm", new UserDTO());

        return "registration/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("noteForm") UserDTO userForm, BindingResult bindingResult) {
        noteValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration/registration";
        }

        User user = mapper.map(userForm, User.class);

        regFormService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

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

        List<User> notes = regFormService.findAllNotes();

        model.addAttribute("notes", mapper.map(notes, (new TypeToken<List<UserDTO>>(){}).getType()));

        return "allNotes";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}