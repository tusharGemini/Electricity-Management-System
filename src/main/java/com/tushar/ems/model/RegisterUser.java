package com.tushar.ems.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class RegisterUser {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
}
