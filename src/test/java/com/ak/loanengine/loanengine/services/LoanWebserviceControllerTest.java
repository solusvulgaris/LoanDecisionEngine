package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.util.Decision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoanWebserviceControllerTest {
    private final LoanService loanService = Mockito.mock(LoanService.class);
    private final LoanWebserviceController loanWebserviceController = new LoanWebserviceController(loanService);

    @Test
    void getDecision() {
        when(loanService.calculateLoan(anyString(), anyInt(), anyInt())).thenReturn(new Decision(new BigDecimal(2000)));

        Decision expectedDecision = new Decision(new BigDecimal(2000));
        Decision actualDecision = loanWebserviceController.getDecision("49002010976", 2000, 12);
        Assertions.assertEquals(expectedDecision, actualDecision);
    }

}
