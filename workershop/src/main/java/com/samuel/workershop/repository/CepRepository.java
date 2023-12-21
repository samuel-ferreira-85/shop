package com.samuel.workershop.repository;

import com.samuel.workershop.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${viacep}")
public interface CepRepository {

    @GetMapping("/{cep}/json")
    Address findByCep(@PathVariable("cep") String cep);

}
