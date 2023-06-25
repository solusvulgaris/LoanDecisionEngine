package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
        when(user.isDebt()).thenReturn(false);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        BigDecimal expectedLoanAmount = new BigDecimal(200);
        BigDecimal actualLoanAmount = loanService.calculateLoan("49002010976", "200", "3");
        Assertions.assertEquals(expectedLoanAmount, actualLoanAmount);
    }

    @Test
    void ifUserHasDebtTest() {
        when(user.isDebt()).thenReturn(true);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        BigDecimal actualLoanAmount = loanService.calculateLoan("49002010965", "200", "3");
        Assertions.assertEquals(0, actualLoanAmount.intValue());
    }

    @Test
    @DisplayName("No such user personal code in DB")
    void noSuchUserTest() {
        final Class<UserNotFoundException> expectedExceptionClass = UserNotFoundException.class;
        final String expectedExceptionMessage = "There is no user with personal code: 123";

        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                () -> loanService.calculateLoan("123", "200", "3")
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }
}
