package com.vh.springregistrationform.validator;

import com.vh.springregistrationform.model.enums.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {

    private static final String ROLE_NAME = "role";

    @Override
    public boolean supports(Class<?> aClass) {
        return Permission.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors){
        String str = (String) o;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equalsIgnoreCase(str));

        if (!hasUserRole) {
            errors.rejectValue(ROLE_NAME, "error.message.role");
        }
    }
}