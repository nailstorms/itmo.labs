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
import static lab2.util.DoubleUtil.*;

public class TestTrig {
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

    @Test(dataProvider = "TrigTestData")
    public void testTrigSinStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);

        Assert.assertEquals(round(new Trig(mockIBaseTrig).sin(x, eps), 6), round(sin, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigCosStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(round(new Trig(mockIBaseTrig).cos(x, eps), 6), round(cos, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigTanStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(round(new Trig(mockIBaseTrig).tan(x, eps), 6), round(tan, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSecStubbed(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockIBaseTrig.sin(eq(x), anyDouble())).thenReturn(sin);
        when(mockIBaseTrig.sin(eq(x + Math.PI / 2), anyDouble())).thenReturn(cos);

        Assert.assertEquals(round(new Trig(mockIBaseTrig).sec(x, eps), 6), round(sec, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSin(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(round(new Trig(new BaseTrig()).sin(x, eps), 6), round(sin, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigCos(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(round(new Trig(new BaseTrig()).cos(x, eps), 6), round(cos, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigTan(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(round(new Trig(new BaseTrig()).tan(x, eps), 6), round(tan, 6));
    }

    @Test(dataProvider = "TrigTestData")
    public void testTrigSec(Double x, Double sin, Double cos, Double tan, Double sec) {
        Assert.assertEquals(round(new Trig(new BaseTrig()).sec(x, eps), 6), round(sec, 6));
    }



    private static double eps = Constant.eps/100;
}
