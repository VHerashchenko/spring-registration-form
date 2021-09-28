package com.vh.springregistrationform.validator;

import com.vh.springregistrationform.model.dto.UserDTO;
import com.vh.springregistrationform.model.entity.User;
import com.vh.springregistrationform.service.RegFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserValidator implements Validator {

    private final RegFormService regFormService;

    private static final String USER_NAME_PARAMETER = "username";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, USER_NAME_PARAMETER, "login.not.empty");
        if (userDTO.getUsername().length() < 6 || userDTO.getUsername().length() > 32) {
            errors.rejectValue(USER_NAME_PARAMETER, "size.username");
        }
        if (regFormService.findByUsername(userDTO.getUsername()) != null) {
            errors.rejectValue(USER_NAME_PARAMETER, "duplicate.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "login.not.empty");
        if (userDTO.getPassword().length() < 8 || userDTO.getPassword().length() > 32) {
            errors.rejectValue("password", "size.password");
        }

        if (!userDTO.getPasswordConfirm().equals(userDTO.getPassword())) {
            errors.rejectValue("passwordConfirm", "different.password");
        }

    }
}
