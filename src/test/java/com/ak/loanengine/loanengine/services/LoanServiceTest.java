package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.Segment;
import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.exceptions.UserNotFoundException;
import com.ak.loanengine.loanengine.util.Decision;
import org.junit.jupiter.api.Assertions;
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

    public static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1000, true, 2000),
                Arguments.of(100, false, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void getDecision(int modifier, boolean approved, int actualAmount) {
        when(user.isDebt()).thenReturn(false);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));
        when(segment.getModifier()).thenReturn(modifier);
        when(segmentRepository.findById(anyLong())).thenReturn(Optional.of(segment));

        Decision actualDecision = loanService.calculateLoan("49002010976", 2000, 12);
        Assertions.assertEquals(approved, actualDecision.isApproved());

        Decision expectedDecision = new Decision(new BigDecimal(actualAmount));
        Assertions.assertEquals(expectedDecision, actualDecision);
    }

    @Test
    void ifUserHasDebtTest() {
        when(user.isDebt()).thenReturn(true);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        Decision actualDecision = loanService.calculateLoan("49002010965", 2000, 12);
        Assertions.assertFalse(actualDecision.isApproved());
        Assertions.assertEquals(0, actualDecision.getAmount().intValue());
    }

    @Test
    @DisplayName("No such user personal code in DB")
    void noSuchUserTest() {
        final Class<UserNotFoundException> expectedExceptionClass = UserNotFoundException.class;
        final String expectedExceptionMessage = "There is no user with personal code: 123";

        final Exception actualException = Assertions.assertThrows(
                expectedExceptionClass,
                () -> loanService.calculateLoan("123", 2000, 12)
        );
        Assertions.assertEquals(expectedExceptionClass, actualException.getClass(), "Exception class");
        Assertions.assertEquals(expectedExceptionMessage, actualException.getMessage(), "Exception message");
    }

}
