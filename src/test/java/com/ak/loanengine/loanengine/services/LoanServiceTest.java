package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoanServiceTest {
    private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoanService loanService = new LoanService(segmentRepository, userRepository);
    private final User user = Mockito.mock(User.class);

    @Test
    void getDecision() {
        BigDecimal expectedLoanAmount = new BigDecimal(200);
        BigDecimal actualLoanAmount = loanService.calculateLoan("123", "200", "3");
        Assertions.assertEquals(expectedLoanAmount, actualLoanAmount);
    }

    @Test
    void ifUserHasDebt() {
        when(user.isDebt()).thenReturn(true);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        BigDecimal actualLoanAmount = loanService.calculateLoan("123", "200", "3");
        Assertions.assertEquals(0, actualLoanAmount.intValue());
    }
}
