package com.tushar.ems.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BillDetails {
    private double totalBillAmount;
}
