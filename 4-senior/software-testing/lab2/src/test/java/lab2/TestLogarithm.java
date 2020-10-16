package lab2;

import lab2.impl.BaseLogarithm;
import lab2.impl.Logarithm;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.when;

public class TestLogarithm {
    private static final double EPS = 1E-8;

    @Mock
    private IBaseLogarithm mockIBaseLogarithm;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "LogTestData")
    public Object[][] logTestData() {
        return new Object[][]
                {
                        {0.1, 0.1, -2.30258509, -2.30258509, 1.0},
                        {25.0, 5.0, 3.21887582, 1.60943791, 2.0},
                        {2.0, 8.0, 0.69314718, 2.07944154, 0.33333333},
                        {0.5, 2.0, -0.69314718, 0.69314718, -1.0},
                        {1.0, 1234.0, 0.0, 7.1180162, 0.0}
                };
    }

    @DataProvider(name = "LogNegativeTestData")
    public Object[][] logNegativeTestData() {
        return new Object[][]
                {
                        {-0.1, 0.1, Double.NaN, -2.30258509, Double.NaN, Constant.eps},
                        {0.1, -0.1, -2.30258509, Double.NaN, Double.NaN, Constant.eps},
                        {Double.NaN, 0.1, Double.NaN, -2.30258509, Double.NaN, Constant.eps},
                        {0.1, Double.NaN, -2.30258509, Double.NaN, Double.NaN, Constant.eps},
                        {0.1, 0.1, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {0.1, 0.1, Double.NaN, Double.NaN, Double.NaN, Double.POSITIVE_INFINITY},
                        {0.1, 0.1, Double.NaN, Double.NaN, Double.NaN, Double.NEGATIVE_INFINITY},
                        {Constant.eps, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Constant.eps}
                };
    }

    @Test(dataProvider = "LogTestData")
    public void testLogarithmLnStubbed(Double x, Double base, Double lnX, Double lnBase, Double expected) {
        when(mockIBaseLogarithm.ln(eq(x), anyDouble())).thenReturn(lnX);

        Assert.assertEquals(new Logarithm(mockIBaseLogarithm).ln(x, EPS), lnX, EPS);
    }

    @Test(dataProvider = "LogTestData")
    public void testLogarithmLogStubbed(Double x, Double base, Double lnX, Double lnBase, Double expected) {
        when(mockIBaseLogarithm.ln(eq(x), anyDouble())).thenReturn(lnX);
        when(mockIBaseLogarithm.ln(eq(base), anyDouble())).thenReturn(lnBase);

        Assert.assertEquals(new Logarithm(mockIBaseLogarithm).log(x, base, EPS), expected, EPS);
    }

    @Test(dataProvider = "LogNegativeTestData")
    public void testLogarithmNegativeStubbed(Double x, Double base, Double lnX, Double lnBase, Double expected, Double eps) {
        when(mockIBaseLogarithm.ln(eq(x), doubleThat(e -> !(e.isInfinite() || e.isNaN())))).thenReturn(lnX);
        when(mockIBaseLogarithm.ln(eq(x), doubleThat(e -> e.isInfinite() || e.isNaN()))).thenReturn(Double.NaN);
        when(mockIBaseLogarithm.ln(eq(base), doubleThat(e -> !(e.isInfinite() || e.isNaN())))).thenReturn(lnBase);
        when(mockIBaseLogarithm.ln(eq(base), doubleThat(e -> e.isInfinite() || e.isNaN()))).thenReturn(Double.NaN);

        Logarithm logarithm = new Logarithm(mockIBaseLogarithm);
        Assert.assertEquals(logarithm.ln(x, eps), lnX, eps);
        Assert.assertEquals(logarithm.log(x, base, eps), expected, eps);
    }

    @Test(dataProvider = "LogTestData")
    public void testLogarithmLn(Double x, Double base, Double lnX, Double lnBase, Double expected) {
        Assert.assertEquals(new Logarithm(new BaseLogarithm()).ln(x, EPS), lnX, EPS);
    }

    @Test(dataProvider = "LogTestData")
    public void testLogarithmLog(Double x, Double base, Double lnX, Double lnBase, Double expected) {
        Assert.assertEquals(new Logarithm(new BaseLogarithm()).log(x, base, EPS), expected, EPS);
    }

    @Test(dataProvider = "LogNegativeTestData")
    public void testLogarithmNegative(Double x, Double base, Double lnX, Double lnBase, Double expected, Double eps) {
        Logarithm logarithm = new Logarithm(new BaseLogarithm());
        Assert.assertEquals(logarithm.ln(x, eps), lnX, eps);
        Assert.assertEquals(logarithm.log(x, base, eps), expected, eps);
    }
}
