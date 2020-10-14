package lab2.impl;

import lab2.IBaseTrig;
import lab2.ITrig;

public class Trig implements ITrig {
    private IBaseTrig baseTrig;

    public Trig(IBaseTrig baseTrig) {
        this.baseTrig = baseTrig;
    }

    public Double sin(Double x) {
        return baseTrig.sin(x);
    }

    public Double cos(Double x) {
        return baseTrig.sin(x + Math.PI / 2);
    }

    public Double tan(Double x) {
        return this.cos(x) == 0.0
                ? (this.sin(x) < 0 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY)
                : this.sin(x) / this.cos(x);
    }

    public Double sec(Double x) {
        return this.cos(x) == 0.0
                ? Double.POSITIVE_INFINITY
                : 1.0 / this.cos(x);
    }
}
