package lab2.impl;

import lab2.IBaseLogarithm;
import lab2.ILogarithm;

public class Logarithm implements ILogarithm {
    private IBaseLogarithm baseLogarithm;

    public Logarithm(IBaseLogarithm baseLogarithm) {
        this.baseLogarithm = baseLogarithm;
    }

    public double log(Double x, Double base, Double eps) {
        return x.isNaN() || x.isInfinite()
                || eps.isNaN() || eps.isInfinite()
                || base.isNaN() || base.isInfinite() || x <= eps
                    ? Double.NaN
                    : baseLogarithm.ln(x, eps) / baseLogarithm.ln(base, eps);
    }
}
