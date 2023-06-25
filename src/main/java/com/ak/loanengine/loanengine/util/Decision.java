package com.ak.loanengine.loanengine.util;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Decision entity
 */
@Getter
public class Decision {
    private final boolean approved;
    private final BigDecimal amount;

    public Decision() {
        amount = new BigDecimal(0);
        approved = false;
    }

    public Decision(BigDecimal amount) {
        approved = !amount.equals(new BigDecimal(0));
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Decision that = (Decision) obj;
        return this.amount.equals(that.amount);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
