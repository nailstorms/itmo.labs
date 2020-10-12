package lab2.impl;

import lab2.IBaseTrig;

public class BaseTrig implements IBaseTrig {
    public double sin(Double x, Double eps) {
        if (x.isInfinite() || x.isNaN() || eps.isInfinite() || eps.isNaN())
            return Double.NaN;

        double n = 1.0;
        double sum = 0.0;
        int i = 1;

        do
        {
            sum += n;
            n *= -1.0 * x * x / ((2 * i - 1) * (2 * i));
            i++;
        }
        while (Math.abs(n) >= eps);
        return sum;
    }
}
