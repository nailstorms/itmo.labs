package lab2;

import lab2.impl.BaseTrig;
import lab2.impl.Trig;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class TestTrig {
    private static final double EPS = 1E-8;

    @Mock
    private IBaseTrig mockIBaseTrig;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "TrigTestData")
    public Object[][] trigTestData() {
        return new Object[][]
                {
                        {3.66519143, -0.5, -0.86602541, 0.57735027, -1.15470054},
                        {3.14159265, 0.0, -1.0, 0.0, -1.0},
                        {1.57079632, 1.0, 0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
                        {0.52359878, 0.5, 0.86602541, 0.57735027, 1.15470054},
                        {0.0, 0.0, 1.0, 0.0, 1.0},
                        {-0.52359878, -0.5, 0.86602541, -0.57735027, 1.15470054},
                        {-1.57079632, -1.0, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY},
                        {-3.14159265, 0.0, -1.0, 0.0, -1.0},
                        {-3.66519143, 0.5, -0.86602541, -0.57735027, -1.15470054},
                };
    }

    @DataProvider(name = "TrigNegativeTestData")
    public Object[][] trigNegativeTestData() {
        return new Object[][]
                {
                        {0.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {0.0, Double.POSITIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {0.0, Double.NEGATIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Double.NaN, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Double.POSITIVE_INFINITY, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Double.NEGATIVE_INFINITY, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN}
                };
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSinStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);

        Assert.assertEquals(new Trig(mockIBaseTrig).sin(x, EPS), sin, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigCosStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(new Trig(mockIBaseTrig).cos(x, EPS), cos, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigTanStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(new Trig(mockIBaseTrig).tan(x, EPS), tan, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSecStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(new Trig(mockIBaseTrig).sec(x, EPS), sec, EPS);
    }

    @Test(dataProvider = "TrigNegativeTestData")
    public void testTrigNegativeStubbed(Double x, Double eps, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), doubleThat(e -> !(e.isInfinite() || e.isNaN())))).thenReturn(sin);
        when(mockIBaseTrig.sin(eq(x), doubleThat(e -> e.isInfinite() || e.isNaN()))).thenReturn(Double.NaN);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), doubleThat(e -> !(e.isInfinite() || e.isNaN())))).thenReturn(cos);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), doubleThat(e -> e.isInfinite() || e.isNaN()))).thenReturn(Double.NaN);

        Trig trig = new Trig(mockIBaseTrig);
        Assert.assertEquals(trig.sin(x, eps), sin);
        Assert.assertEquals(trig.cos(x, eps), cos);
        Assert.assertEquals(trig.tan(x, eps), tan);
        Assert.assertEquals(trig.sec(x, eps), sec);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSin(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(new Trig(new BaseTrig()).sin(x, EPS), sin, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigCos(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(new Trig(new BaseTrig()).cos(x, EPS), cos, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigTan(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(new Trig(new BaseTrig()).tan(x, EPS), tan, EPS);
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSec(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(new Trig(new BaseTrig()).sec(x, EPS), sec, EPS);
    }

    @Test(dataProvider = "TrigNegativeTestData")
    public void testTrigNegative(Double x, Double eps, Double sin, Double cos, Double tan, Double sec) {
        Trig trig = new Trig(new BaseTrig());
        Assert.assertEquals(trig.sin(x, eps), sin);
        Assert.assertEquals(trig.cos(x, eps), cos);
        Assert.assertEquals(trig.tan(x, eps), tan);
        Assert.assertEquals(trig.sec(x, eps), sec);
    }
}
