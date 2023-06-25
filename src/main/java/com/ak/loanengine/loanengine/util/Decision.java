package com.ak.loanengine.loanengine.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Decision entity
 */
public class Decision {
    private final boolean value;
    private final BigDecimal amount;

    public boolean isValue() {
        return value;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Decision(BigDecimal amount) {
        value = !amount.equals(new BigDecimal(0));
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
