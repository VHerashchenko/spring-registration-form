package com.vh.springregistrationform.validator;

import com.vh.springregistrationform.dto.NoteDTO;
import com.vh.springregistrationform.entity.Note;
import com.vh.springregistrationform.service.RegFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoteValidator implements Validator {

    private final RegFormService regFormService;

    private static String USER_NAME_PARAMETER = "username";

    @Override
    public boolean supports(Class<?> aClass) {
        return Note.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        NoteDTO noteDTO = (NoteDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, USER_NAME_PARAMETER, "login.not.empty");
        if (noteDTO.getUsername().length() < 6 || noteDTO.getUsername().length() > 32) {
            errors.rejectValue(USER_NAME_PARAMETER, "size.username");
        }
        if (regFormService.findByUsername(noteDTO.getUsername()) != null) {
            errors.rejectValue(USER_NAME_PARAMETER, "duplicate.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "login.not.empty");
        if (noteDTO.getPassword().length() < 8 || noteDTO.getPassword().length() > 32) {
            errors.rejectValue("password", "size.password");
        }

        if (!noteDTO.getPasswordConfirm().equals(noteDTO.getPassword())) {
            errors.rejectValue("passwordConfirm", "different.password");
        }

    }
}
