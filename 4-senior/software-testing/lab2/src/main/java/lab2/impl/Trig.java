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
        return baseTrig.sin(x + Math.PI / 2, eps);
    }

    public Double tan(Double x, Double eps) {
        return this.cos(x, eps) == 0.0 ? Double.NaN : this.sin(x, eps) / this.cos(x, eps);
    }

    public Double sec(Double x, Double eps) {
        return this.cos(x, eps) == 0.0 ? Double.NaN : 1.0 / this.cos(x, eps);
    }
}
