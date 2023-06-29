package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.Segment;
import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import com.ak.loanengine.loanengine.util.Decision;
import com.ak.loanengine.loanengine.util.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoanServiceTest {
    private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoanService loanService = new LoanService(segmentRepository, userRepository);
    private final Segment segment = Mockito.mock(Segment.class);
    private final User user = Mockito.mock(User.class);
    private static final Loan loan = new Loan();

    @BeforeAll
    static void setUpAll() {
        loan.setPersonalCode("49002010976");
        loan.setLoanAmount(2000);
        loan.setLoanPeriod(12);
    }

    public static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1000, false, BigDecimal.valueOf(10000.0)),
                Arguments.of(100, true, BigDecimal.valueOf(0.0))
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void getDecision(int modifier, boolean isDebt, BigDecimal actualAmount) {
        when(user.isDebt()).thenReturn(isDebt);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));
        when(segment.getModifier()).thenReturn(modifier);
        when(segmentRepository.findById(anyLong())).thenReturn(Optional.of(segment));

        Decision actualDecision = loanService.calculateLoan(loan);
        Decision expectedDecision = new Decision(actualAmount, loan.getLoanPeriod());
        Assertions.assertEquals(expectedDecision, actualDecision);
    }

    @Test
    void ifUserHasDebtTest() {
        when(user.isDebt()).thenReturn(true);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        Decision actualDecision = loanService.calculateLoan(loan);
        Assertions.assertFalse(actualDecision.isApproved());
        Assertions.assertEquals(0, actualDecision.getAmount().intValue());
    }

    @Test
    @DisplayName("No such user personal code in DB")
    void noSuchUserTest() {
        final Class<UserNotFoundException> expectedExceptionClass = UserNotFoundException.class;
        final String expectedExceptionMessage = "There is no user with personal code: 123";

        loan.setPersonalCode("123");
        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                () -> loanService.calculateLoan(loan)
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }

    public static Stream<Arguments> calculationTestData() {
        return Stream.of(
                Arguments.of(100, 2000, 12, BigDecimal.valueOf(6000.0), 60),
                Arguments.of(100, 2000, 60, BigDecimal.valueOf(6000.0), 60),
                Arguments.of(100, 10000, 12, BigDecimal.valueOf(6000.0), 60),
                Arguments.of(100, 10000, 60, BigDecimal.valueOf(6000.0), 60),
                Arguments.of(300, 2000, 12, BigDecimal.valueOf(10000.0), 34),
                Arguments.of(300, 10000, 60, BigDecimal.valueOf(10000.0), 34),
                Arguments.of(300, 2000, 12, BigDecimal.valueOf(10000.0), 34),
                Arguments.of(300, 10000, 60, BigDecimal.valueOf(10000.0), 34),
                Arguments.of(1000, 2000, 12, BigDecimal.valueOf(10000.0), 12),
                Arguments.of(1000, 2000, 60, BigDecimal.valueOf(10000.0), 12),
                Arguments.of(1000, 10000, 12, BigDecimal.valueOf(10000.0), 12),
                Arguments.of(1000, 10000, 60, BigDecimal.valueOf(10000.0), 12)
        );
    }

    @ParameterizedTest
    @MethodSource("calculationTestData")
    @DisplayName("test calculate loan")
    void calculateLoan(float creditModifier, float desiredLoanAmount, float loanPeriod, BigDecimal actualAmount, int actualPeriod) {
        loanService.setInitialLoanAmount(desiredLoanAmount);
        loanService.setInitialLoanPeriod(loanPeriod);
        Decision decision = loanService.calculate(creditModifier, desiredLoanAmount, loanPeriod);
        Assertions.assertEquals(actualAmount, decision.getAmount());
        Assertions.assertEquals(actualPeriod, decision.getPeriod());
    }

}
