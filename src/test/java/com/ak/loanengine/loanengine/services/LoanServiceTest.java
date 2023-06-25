package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class LoanServiceTest {
    private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoanService loanService = new LoanService(segmentRepository, userRepository);

    @Test
    void getDecision() {
        BigDecimal expectedLoanAmount = new BigDecimal(200);
        BigDecimal actualLoanAmount = loanService.calculateLoan("123", "200", "3");
        Assertions.assertEquals(expectedLoanAmount, actualLoanAmount);
    }
}
