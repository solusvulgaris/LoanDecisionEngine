package com.ak.loanengine.loanengine.util;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
@Getter
public class Loan {
    private String personalCode;
    @Min(2000)
    @Max(10000)
    private int loanAmount;
    @Min(12)
    @Max(60)
    private int loanPeriod;
    private Decision decision;
}
