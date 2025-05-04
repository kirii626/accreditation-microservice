package com.accenture.accreditation_service.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoIdUsernameEmail {

    private Long userId;

    private String username;

    private String email;
}
