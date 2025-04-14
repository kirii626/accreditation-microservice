package com.accenture.accreditation_service.client;

import com.accenture.accreditation_service.client.dtos.SalePointDtoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sale-point-service", url = "${sale-point-service.url}")
public interface SalePointClient {

    @GetMapping("/by-id/{salePointId}")
    SalePointDtoOutput getSalePointById(@PathVariable("salePointId") Long salePointId);

}
