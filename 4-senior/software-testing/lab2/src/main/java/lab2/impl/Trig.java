package lab2.impl;

import lab2.IBaseTrig;
import lab2.ITrig;

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
}
