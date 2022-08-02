package com.tushar.ems.service;

import com.tushar.ems.dao.EMSDao;
import com.tushar.ems.model.RegisterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private EMSDao emsDao;

    public UUID register(RegisterUser registerUser) throws ExecutionException, InterruptedException {
        logger.info("Registering user with details {}", registerUser);
        UUID customerId = UUID.randomUUID();
        Boolean flag = CompletableFuture.supplyAsync(() ->
                emsDao.createUser(registerUser, customerId)).get();
        logger.info("User registration flag {}", flag);
        if (flag)
            return customerId;
        else
            return null;
    }
}
