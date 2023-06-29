package com.ak.loanengine.loanengine.util;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Decision entity
 */
@Getter
public class Decision implements Comparable<Decision> {
    private final boolean approved;
    private final BigDecimal amount;
    private final int period;

    public Decision() {
        approved = false;
        amount = new BigDecimal(0);
        period = 0;
    }

    public Decision(BigDecimal amount, int period) {
        approved = !amount.equals(BigDecimal.valueOf(0.0));
        this.amount = amount;
        this.period = period;
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
        return this.period == that.period
                && this.amount.equals(that.amount);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Decision o) {
        if (this.equals(o)) return 0;

        if (this.getAmount().equals(o.getAmount())) {
            return -Integer.compare(this.getPeriod(), o.getPeriod());
        }
        return this.getAmount().compareTo(o.getAmount());
    }
}
