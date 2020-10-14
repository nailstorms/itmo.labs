package lab2.impl;

import lab2.Constant;
import lab2.IBaseLogarithm;
import lab2.ILogarithm;

public class Logarithm implements ILogarithm {
    private IBaseLogarithm baseLogarithm;

    public Logarithm(IBaseLogarithm baseLogarithm) {
        this.baseLogarithm = baseLogarithm;
    }

    public Double ln(Double x) {
        return baseLogarithm.ln(x);
    }

    public Double log(Double x, Double base) {
        return x.isNaN() || x.isInfinite()
                || base.isNaN() || base.isInfinite() || x <= Constant.eps
                    ? Double.NaN
                    : baseLogarithm.ln(x) / baseLogarithm.ln(base);
    }
}
