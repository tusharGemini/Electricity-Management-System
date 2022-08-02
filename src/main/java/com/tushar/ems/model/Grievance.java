package com.tushar.ems.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class Grievance {
    private String subject;
    private String summery;
    private UUID customerId;
}
