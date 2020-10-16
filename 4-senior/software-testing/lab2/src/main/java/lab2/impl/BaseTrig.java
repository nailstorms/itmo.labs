package lab2.impl;

import lab2.IBaseTrig;

import static java.lang.Math.abs;
import static java.lang.Math.PI;

public class BaseTrig implements IBaseTrig {
    public Double sin(Double x, Double eps) {
        if (x.isInfinite() || x.isNaN() || eps.isInfinite() || eps.isNaN())
            return Double.NaN;

        double x1 = x % (2 * PI);
        if (abs(abs(x1) - PI) < eps || abs(abs(x1) - 2 * PI) < eps || abs(abs(x1) - 0.0) < eps) {
            return 0.0;
        }
        return calc(x1, x1, eps, 1.0, 0.0);
    }

    private Double calc(Double x, Double cur, Double eps, Double n, Double res) {
        if (Math.abs(cur) < eps) return res;
        else return calc(x, cur * (-x * x / (2.0 * n * (2.0 * n + 1.0))), eps, n + 1, res + cur);
    }
}