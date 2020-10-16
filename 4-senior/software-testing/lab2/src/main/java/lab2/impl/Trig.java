package lab2.impl;

import lab2.Constant;
import lab2.IBaseTrig;
import lab2.ITrig;

import java.io.*;

public class Trig implements ITrig {
    private IBaseTrig baseTrig;

    public Trig(IBaseTrig baseTrig) {
        this.baseTrig = baseTrig;
    }

    public Double sin(Double x, Double eps) {
        return baseTrig.sin(x, eps);
    }

    public Double cos(Double x, Double eps) {
        return this.sin(x + Math.PI / 2, eps);
    }

    public Double tan(Double x, Double eps) {
        return this.cos(x, eps) == 0.0
                ? (this.sin(x, eps) < 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY)
                : this.sin(x, eps) / this.cos(x, eps);
    }

    public Double sec(Double x, Double eps) {
        return this.cos(x, eps) == 0.0
                ? Double.POSITIVE_INFINITY
                : 1.0 / this.cos(x, eps);
    }

    public boolean writeToCSV(String filepath, Double leftBound, Double rightBound, Double step) {
        try {
            FileWriter fw = new FileWriter(filepath);
            fw.write("x,sin,cos,tan,sec\n");
            double tempVar = leftBound;
            while (tempVar < rightBound) {
                fw.append(String.format("%s,%s,%s,%s,%s\n",
                        tempVar,
                        this.sin(tempVar, Constant.eps),
                        this.cos(tempVar, Constant.eps),
                        this.tan(tempVar, Constant.eps),
                        this.sec(tempVar, Constant.eps)
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
