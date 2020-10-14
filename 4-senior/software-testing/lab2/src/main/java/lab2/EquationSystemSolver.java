package lab2;

public class EquationSystemSolver {
    private ILogarithm log;
    private ITrig trig;

    public EquationSystemSolver(ILogarithm log, ITrig trig) {
        this.log = log;
        this.trig = trig;
    }

    public Double solve(Double x) {
        if (x.isNaN() || x.isInfinite()) return Double.NaN;
        else if (x <= 0) {
            double sin = trig.sin(x);
            double cos = trig.cos(x);
            double tan = trig.tan(x);
            double sec = trig.sec(x);

            double firstTerm = Math.pow(tan / tan / sec, 3) / Math.pow(sec, 2);
            double secondTerm = cos / Math.pow(sin, 3);

            return firstTerm + secondTerm;
        } else {
            double ln = log.ln(x);
            double log2 = log.log(x, 2.0);
            double log3 = log.log(x, 3.0);
            double log10 = log.log(x, 10.0);

            return ((((log10 + log3) * log3) - log3) + (ln - (ln + log2))) - (Math.pow((ln + log3) * log3, 2));
        }
    }
}
