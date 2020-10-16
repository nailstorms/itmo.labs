package lab2;

import java.io.*;

public class EquationSystemSolver {
    private ILogarithm log;
    private ITrig trig;

    public EquationSystemSolver(ILogarithm log, ITrig trig) {
        this.log = log;
        this.trig = trig;
    }

    public Double solve(Double x, Double eps) {
        if (x.isInfinite() || x.isNaN() || eps.isInfinite() || eps.isNaN()) return Double.NaN;
        else if (x <= 0) {
            double sin = trig.sin(x, eps);
            double cos = trig.cos(x, eps);
            double tan = trig.tan(x, eps);
            double sec = trig.sec(x, eps);

            double firstTerm = Math.pow(tan / tan / sec, 3) / Math.pow(sec, 2);
            double secondTerm = cos / Math.pow(sin, 3);

            return firstTerm + secondTerm;
        } else {
            double ln = log.ln(x, eps);
            double log2 = log.log(x, 2.0, eps);
            double log3 = log.log(x, 3.0, eps);
            double log10 = log.log(x, 10.0, eps);

            return ((((log10 + log3) * log3) - log3) + (ln - (ln + log2))) - (Math.pow((ln + log3) * log3, 2));
        }
    }

    public boolean writeToCSV(String filepath, Double leftBound, Double rightBound, Double step) {
        try {
            FileWriter fw = new FileWriter(filepath);
            fw.write("x,result\n");
            double tempVar = leftBound;
            while (tempVar < rightBound) {
                fw.append(String.format("%s,%s\n", tempVar, this.solve(tempVar, Constant.eps)));
                tempVar += step;
            }
            fw.flush();
            fw.close();
            return true;
        } catch (IOException exc) {
            return false;
        }
    }
}
