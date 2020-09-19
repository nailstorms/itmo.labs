package lab1.math;

import static java.lang.Math.pow;
import static java.lang.Math.abs;

public class Arctan {

    public static Double computeTaylorSeries(Double x, int eps) {
        if (abs(x) <= 1) {
            double result = 0;

            for (int i = 0; i < eps; i++) {
                result += (pow(-1, i) / (2 * i + 1)) * pow(x, (2 * i + 1));
            }
            return result;
        } else {
            return null;
        }
    }

}
