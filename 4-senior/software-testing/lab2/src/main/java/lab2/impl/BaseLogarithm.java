package lab2.impl;

import lab2.IBaseLogarithm;

public class BaseLogarithm implements IBaseLogarithm {
    public Double ln(Double x, Double eps) {
        if (x <= 0 || x <= eps || x.isNaN())
            return Double.NaN;
        if (x == Double.POSITIVE_INFINITY)
            return Double.POSITIVE_INFINITY;
        else {
            double xTemp = (x - 1) / (x + 1);
            double nom = xTemp;
            double res = 0.0;
            int i = 3;
            while (Math.abs(2 * nom) > eps / 10) {
                res += 2 * nom;
                nom *= xTemp * xTemp / i * (i - 2);
                i += 2;
            }
            return res;
        }
    }
}
