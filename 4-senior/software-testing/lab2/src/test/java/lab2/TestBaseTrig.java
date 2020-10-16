package lab2;

import lab2.impl.BaseTrig;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBaseTrig {
    private static final double EPS = 1E-8;

    @DataProvider(name = "BaseTrigTestData")
    public Object[][] baseTrigTestData() {
        return new Object[][]
                {
                        {3.66519143, -0.5},
                        {3.14159265, 0.0},
                        {1.57079632, 1.0},
                        {0.52359878, 0.5},
                        {0.0, 0.0},
                        {-0.52359878, -0.5},
                        {-1.57079632, -1.0},
                        {-3.14159265, 0.0},
                        {-3.66519143, 0.5},
                };
    }

    @DataProvider(name = "BaseTrigNegativeTestData")
    public Object[][] baseTrigNegativeTestData() {
        return new Object[][]
                {
                        {0.0, Double.NaN, Double.NaN},
                        {0.0, Double.POSITIVE_INFINITY, Double.NaN},
                        {0.0, Double.NEGATIVE_INFINITY, Double.NaN},
                        {Double.NaN, Constant.eps, Double.NaN},
                        {Double.POSITIVE_INFINITY, Constant.eps, Double.NaN},
                        {Double.NEGATIVE_INFINITY, Constant.eps, Double.NaN}
                };
    }

    @Test(dataProvider = "BaseTrigTestData")
    public void testBaseTrigSin(Double x, Double sin) {
        Assert.assertEquals(new BaseTrig().sin(x, EPS), sin, EPS);
    }

    @Test(dataProvider = "BaseTrigNegativeTestData")
    public void testTrigNegative(Double x, Double eps, Double sin) {
        Assert.assertEquals(new BaseTrig().sin(x, eps), sin);
    }
}
