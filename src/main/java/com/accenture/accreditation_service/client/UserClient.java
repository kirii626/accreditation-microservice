package com.accenture.accreditation_service.client;

import com.accenture.accreditation_service.client.dtos.UserDtoIdUsernameEmail;
import com.accenture.accreditation_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "admin-service",
        url = "${user.admin.service.url}",
        configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/user-by-id/{userId}")
    UserDtoIdUsernameEmail getUserById(@PathVariable("userId") Long userId);
}


