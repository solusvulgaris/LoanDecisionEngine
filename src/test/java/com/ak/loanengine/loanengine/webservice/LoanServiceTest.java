package com.ak.loanengine.loanengine.webservice;

import com.ak.loanengine.loanengine.util.Decision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class LoanServiceTest {
    private final LoanService loanService =
            new LoanService();
    @Test
    void retrieveBooking() {
        Decision expectedDecision = new Decision(new BigDecimal(200));
        Decision actualDecision = loanService.getDecision("123", "200", "3");
        Assertions.assertEquals(expectedDecision, actualDecision);
    }
}
