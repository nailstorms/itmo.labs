package lab2.util;

import lab2.Constant;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtil {
    public static Double round(double value) {
        return round(value, Constant.roundPlaces);
    }

    public static Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (value.isNaN() || value.isInfinite()) return value;

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
