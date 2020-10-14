package lab2.impl;

import lab2.Constant;
import lab2.IBaseTrig;

import static java.lang.Math.abs;
import static java.lang.Math.PI;

public class BaseTrig implements IBaseTrig {
    public Double sin(Double x) {
        if (x.isInfinite() || x.isNaN())
            return Double.NaN;

        double x1 = x % (2 * PI);
        if (abs(abs(x1) - PI) < Constant.eps || abs(abs(x1) - 2 * PI) < Constant.eps || abs(abs(x1) - 0.0) < Constant.eps) {
            return 0.0;
        }
        return calc(x1, x1, 1.0, 0.0);
    }

    private Double calc(Double x, Double cur, Double n, Double res) {
        if (Math.abs(cur) < Constant.eps) return res;
        else return calc(x, cur * (-x * x / (2.0 * n * (2.0 * n + 1.0))), n + 1, res + cur);
    }
}