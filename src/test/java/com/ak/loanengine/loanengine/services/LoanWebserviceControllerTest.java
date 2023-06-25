package com.ak.loanengine.loanengine.services;

import com.ak.loanengine.loanengine.data.SegmentRepository;
import com.ak.loanengine.loanengine.data.UserRepository;
import com.ak.loanengine.loanengine.util.Decision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class LoanWebserviceControllerTest {
    private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoanService loanService = new LoanService(segmentRepository, userRepository);
    private final LoanWebserviceController loanWebserviceController = new LoanWebserviceController(loanService);

    @Test
    void getDecision() {
        Decision expectedDecision = new Decision(new BigDecimal(200));
        Decision actualDecision = loanWebserviceController.getDecision("123", "200", "3");
        Assertions.assertEquals(expectedDecision, actualDecision);
    }
}
