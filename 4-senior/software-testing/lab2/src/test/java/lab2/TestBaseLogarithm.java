package lab2;

import lab2.impl.BaseLogarithm;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBaseLogarithm {
    private static final double EPS = 1E-8;

    @DataProvider(name = "BaseLogTestData")
    public Object[][] baseLogTestData() {
        return new Object[][]
                {
                        {0.1, -2.30258509},
                        {25.0, 3.21887582},
                        {2.0, 0.69314718},
                        {0.5, -0.69314718},
                        {1.0, 0.0},
                        {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY}
                };
    }

    @DataProvider(name = "BaseLogNegativeTestData")
    public Object[][] baseLogNegativeTestData() {
        return new Object[][]
                {
                        {-0.1, Double.NaN, Constant.eps},
                        {Double.NaN, Double.NaN, Constant.eps},
                        {Double.NEGATIVE_INFINITY, Double.NaN, Constant.eps},
                        {0.1, Double.NaN, Double.NaN},
                        {0.1, Double.NaN, Double.POSITIVE_INFINITY},
                        {0.1, Double.NaN, Double.NEGATIVE_INFINITY},
                        {Constant.eps, Double.NaN, Constant.eps}
                };
    }

    @Test(dataProvider = "BaseLogTestData")
    public void testBaseLogarithmLn(Double x, Double expected) {
        Assert.assertEquals(new BaseLogarithm().ln(x, EPS), expected, EPS);
    }

    @Test(dataProvider = "BaseLogNegativeTestData")
    public void testBaseLogarithmNegative(Double x, Double expected, Double eps) {
        Assert.assertEquals(new BaseLogarithm().ln(x, eps), expected, eps);
    }
}
