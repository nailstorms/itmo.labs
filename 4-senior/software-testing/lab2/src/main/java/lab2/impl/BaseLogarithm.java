package lab2.impl;

import lab2.IBaseLogarithm;

public class BaseLogarithm implements IBaseLogarithm {
    public Double ln(Double x, Double eps) {
        if (x <= 0 || x.isNaN() || eps.isInfinite() || eps.isNaN())
            return Double.NaN;
        if (x == Double.POSITIVE_INFINITY)
            return Double.POSITIVE_INFINITY;
        if (x < 1) {
            double xTemp = x - 1;
            double nom = xTemp;
            double res = xTemp;
            int i = 2;
            while (Math.abs(nom) >= eps) {
                nom *= -xTemp;
                res += nom / i;
                i++;
            }
            return res;
        } else {
            double xTemp = x / (x - 1);
            double nom = xTemp;
            double res = 0.0;
            int i = 1;
            while (Math.abs(nom) >= eps) {
                nom = 1.0 / (i * Math.pow(xTemp, i));
                res += nom;
                i++;
            }
            return res;
        }
    }
}
