package com.samuel.workershop.service;

import com.samuel.workershop.repository.CepRepository;
import com.samuel.workershop.model.Address;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CepService {

    private final CepRepository cepRepository;

    public void findCep(String cep) {
        Address address = cepRepository.findByCep(cep);
        log.info("Address: " + address);
    }

}
