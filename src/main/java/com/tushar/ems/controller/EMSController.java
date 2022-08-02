package com.tushar.ems.controller;

import com.tushar.ems.model.BillDetails;
import com.tushar.ems.model.Grievance;
import com.tushar.ems.model.RegisterUser;
import com.tushar.ems.service.ElectricityService;
import com.tushar.ems.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class EMSController {
    private static final Logger logger = LoggerFactory.getLogger(EMSController.class);
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ElectricityService electricityService;

    /**
     * api to register customer
     *
     * @param registerUser object
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser registerUser) throws ExecutionException, InterruptedException {
        UUID customerId = registrationService.register(registerUser);
        if (customerId != null) {
            logger.info("Registered user with ID {}", customerId);
            return new ResponseEntity<>("User has been registered with ID " + customerId, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Error while registering user", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * api to calculate the electricity bill amount
     *
     * @param noOfUnits
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/calculate")
    public BillDetails calculateBill(@RequestParam Integer noOfUnits) throws ExecutionException,
            InterruptedException {
        logger.info("Calculate bill for units {}", noOfUnits);
        return electricityService.calculateBill(noOfUnits).get();
    }

    /**
     * api to register customer grievance
     *
     * @param grievance object
     * @return response
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping("/grievance")
    public ResponseEntity<String> grievance(@RequestBody Grievance grievance) throws ExecutionException, InterruptedException {
        logger.info("Register grievance with details {}", grievance);
        UUID grievanceId = electricityService.registerGrievance(grievance);
        if (grievanceId != null) {
            logger.info("Registered grievance with ID {}", grievanceId);
            return new ResponseEntity<>("Grievance has been registered with ID " + grievanceId, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Error while registering grievance", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
