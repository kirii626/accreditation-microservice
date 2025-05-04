package com.accenture.accreditation_service.services.validations;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidRoleType {

    public void validateAdminRole(HttpServletRequest request) {
        String roleType = request.getHeader("X-Role");
        System.out.println("X-Role recibido en validateAdminRole: " + roleType);

        if (roleType == null || !roleType.equals("ADMIN")) {
            throw new RuntimeException("Access denied: Admin role required.");
        }
    }

    public void validateUserRole(HttpServletRequest request) {
        String roleType = request.getHeader("X-Role");
        System.out.println("X-Role recibido en validateAdminRole: " + roleType);

        if (roleType == null || !roleType.equals("USER")) {
            throw new RuntimeException("Access denied: User role required.");
        }
    }
}
