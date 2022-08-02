package com.tushar.ems.service;

import com.tushar.ems.dao.EMSDao;
import com.tushar.ems.model.BillDetails;
import com.tushar.ems.model.Grievance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ElectricityService {
    private static final Logger logger = LoggerFactory.getLogger(ElectricityService.class);

    @Autowired
    private EMSDao emsDao;

    public CompletableFuture<BillDetails> calculateBill(Integer noOfUnits) {
        logger.info("Calculated bill for units {}", noOfUnits);
        return CompletableFuture.supplyAsync(() -> calculateBillAmount(noOfUnits))
                .thenApply(this::billWithGreenTax)
                .thenApply(this::totalBillAmountWithGST)
                .thenApply(totalBillAmount ->
                {
                    logger.info("Calculated bill details {}", totalBillAmount);
                    BillDetails.builder().totalBillAmount(totalBillAmount).build();
                    return BillDetails.builder().totalBillAmount(totalBillAmount).build();
                });
    }

    private double totalBillAmountWithGST(Double billAmountWithGreenTax) {
        double totalBillAmountWithGST = billAmountWithGreenTax + billAmountWithGreenTax * 18 / 100;
        logger.info("Calculated total bill amount with GST {}", totalBillAmountWithGST);
        return totalBillAmountWithGST;
    }

    private double billWithGreenTax(double billAmount) {
        double billAmountWithGreenTax = billAmount + billAmount * 3 / 100;
        logger.info("Calculated bill amount with green tax {}", billAmountWithGreenTax);
        return billAmountWithGreenTax;
    }

    private double calculateBillAmount(Integer noOfUnits) {
        double billAmountWithoutTax = 0;
        if (noOfUnits >= 0 && noOfUnits <= 100)
            billAmountWithoutTax = noOfUnits * 5;
        else if (noOfUnits > 100 && noOfUnits <= 400)
            billAmountWithoutTax = noOfUnits * 6;
        else if (noOfUnits > 400) {
            billAmountWithoutTax = noOfUnits * 8;
        }
        logger.info("Bill amount without tax {}", billAmountWithoutTax);
        return billAmountWithoutTax;
    }

    public UUID registerGrievance(Grievance grievance) throws ExecutionException, InterruptedException {
        logger.info("Registering grievance with details {}", grievance);
        UUID grievanceId = UUID.randomUUID();
        Boolean flag = CompletableFuture.supplyAsync(() ->
                emsDao.registerGrievance(grievance, grievanceId)).get();
        logger.info("Grievance registration flag {}", flag);
        if (flag) {
            return grievanceId;
        } else
            return null;
    }

}
