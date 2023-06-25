package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.User;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.util.Decision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoanWebserviceControllerTest {
    private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoanService loanService = new LoanService(segmentRepository, userRepository);
    private final LoanWebserviceController loanWebserviceController = new LoanWebserviceController(loanService);
    private final User user = Mockito.mock(User.class);

    @Test
    void getDecision() {
        when(user.isDebt()).thenReturn(false);
        when(userRepository.findByCode(anyString())).thenReturn(Optional.of(user));

        Decision expectedDecision = new Decision(new BigDecimal(200));
        Decision actualDecision = loanWebserviceController.getDecision("49002010976", "200", "3");
        Assertions.assertEquals(expectedDecision, actualDecision);
    }
}
