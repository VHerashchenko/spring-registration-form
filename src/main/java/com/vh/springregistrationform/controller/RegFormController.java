package com.vh.springregistrationform.controller;

import com.vh.springregistrationform.model.dto.UserDTO;
import com.vh.springregistrationform.model.entity.User;
import com.vh.springregistrationform.model.enums.Permission;
import com.vh.springregistrationform.service.SecurityService;
import com.vh.springregistrationform.service.impl.RegFormServiceImpl;
import com.vh.springregistrationform.validator.RoleValidator;
import com.vh.springregistrationform.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegFormController {

    private final RegFormServiceImpl regFormService;

    private final UserValidator noteValidator;

    private final ModelMapper mapper;

    private final SecurityService securityService;

    private final RoleValidator roleValidator;

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
    @PreAuthorize("hasAnyAuthority('read', 'write')")
    public String getAllNotes(Model model){

        log.debug("findAllNotes");

        List<User> notes = regFormService.findAllNotes();

        model.addAttribute("notes", mapper.map(notes, (new TypeToken<List<UserDTO>>(){}).getType()));

        return "allNotes";
    }

    @DeleteMapping("/all-notes/{id}")
    public String deleteById(@PathVariable Integer id){
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "role");
        roleValidator.validate(Permission.WRITE.name(), bindingResult);

        if (bindingResult.hasErrors()){
            return "redirect:/all-notes";
        }

        regFormService.deleteById(id);

        return "redirect:/all-notes";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}