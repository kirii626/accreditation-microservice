package com.accenture.accreditation_service.services.validations;

import com.accenture.accreditation_service.config.JwtUtils;
import com.accenture.accreditation_service.exceptions.ForbiddenAccessException;
import com.accenture.accreditation_service.exceptions.InvalidAuthorizationHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidRoleType {

    private final JwtUtils jwtUtils;

    public void validateAdminRole(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidAuthorizationHeaderException();
        }

        String token = authHeader.substring(7);
        String roleType = jwtUtils.extractRole(token);

        if (roleType == null || !roleType.equals("ADMIN")) {
            throw new ForbiddenAccessException();
        }
    }

    public void validateUserRole(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidAuthorizationHeaderException();
        }

        String token = authHeader.substring(7);
        String roleType = jwtUtils.extractRole(token);

        if (roleType == null || !roleType.equals("USER")) {
            throw new ForbiddenAccessException();
        }
    }
}
