package com.accenture.accreditation_service.services.validations;

import com.accenture.accreditation_service.config.JwtUtils;
import com.accenture.accreditation_service.exceptions.ForbiddenAccessException;
import com.accenture.accreditation_service.exceptions.InvalidAuthorizationHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidRoleTypeTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ValidRoleType validRoleType;

    @Test
    void validateAdminRole_shouldPass_whenRoleIsAdmin() {
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("ADMIN");

        assertDoesNotThrow(() -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowInvalidAuthorizationHeaderException_whenHeaderIsMissing() {
        when(request.getHeader("Authorization")).thenReturn(null);

        assertThrows(InvalidAuthorizationHeaderException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowInvalidAuthorizationHeaderException_whenHeaderIsMalformed() {
        when(request.getHeader("Authorization")).thenReturn("BadToken abc");

        assertThrows(InvalidAuthorizationHeaderException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowForbiddenAccessException_whenRoleIsNotAdmin() {
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("USER");

        assertThrows(ForbiddenAccessException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowForbiddenAccessException_whenRoleIsNull() {
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn(null);

        assertThrows(ForbiddenAccessException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateUserRole_shouldPass_whenRoleIsUser() {
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("USER");

        assertDoesNotThrow(() -> validRoleType.validateUserRole(request));
    }

    @Test
    void validateUserRole_shouldThrowInvalidAuthorizationHeaderException_whenHeaderIsNull() {
        when(request.getHeader("Authorization")).thenReturn(null);

        assertThrows(InvalidAuthorizationHeaderException.class,
                () -> validRoleType.validateUserRole(request));
    }

    @Test
    void validateUserRole_shouldThrowForbiddenAccessException_whenRoleIsNotUser() {
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("ADMIN");

        assertThrows(ForbiddenAccessException.class,
                () -> validRoleType.validateUserRole(request));
    }
}

