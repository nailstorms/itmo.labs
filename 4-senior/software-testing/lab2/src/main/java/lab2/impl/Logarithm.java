package lab2.impl;

import lab2.Constant;
import lab2.IBaseLogarithm;
import lab2.ILogarithm;

import java.io.*;

public class Logarithm implements ILogarithm {
    private IBaseLogarithm baseLogarithm;

    public Logarithm(IBaseLogarithm baseLogarithm) {
        this.baseLogarithm = baseLogarithm;
    }

    public Double ln(Double x, Double eps) {
        return baseLogarithm.ln(x, eps);
    }

    public Double log(Double x, Double base, Double eps) {
        return x.isInfinite() || x.isNaN() || eps.isInfinite() || eps.isNaN()
                || base.isNaN() || base.isInfinite() || x <= eps
                    ? Double.NaN
                    : baseLogarithm.ln(x, eps) / baseLogarithm.ln(base, eps);
    }

    public boolean writeToCSV(String filepath, Double leftBound, Double rightBound, Double step) {
        try {
            FileWriter fw = new FileWriter(filepath);
            fw.write("x,ln,log2,log3,log10\n");
            double tempVar = leftBound;
            while (tempVar < rightBound) {
                fw.append(String.format("%s,%s,%s,%s,%s\n",
                        tempVar,
                        this.ln(tempVar, Constant.eps),
                        this.log(tempVar, 2.0, Constant.eps),
                        this.log(tempVar, 3.0, Constant.eps),
                        this.log(tempVar, 10.0, Constant.eps)
                ));
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
