package lab2;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class EquationSystemSolverTest {
    @Mock
    private ITrig mockITrig;

    @Mock
    private ILogarithm mockILogarithm;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "PositiveTestData")
    public Object[][] positiveTestData() {
        return new Object[][]
                {

                        {-1.570796, -1.0, 0.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 0.0},
                        {1.570796 - 2 * Math.PI, -1.0, 0.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 0.0},
                        {-1.570796 - 2 * Math.PI, -1.0, 0.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 0.0},
                        {-0.6, -0.56464247, 0.82533561, -0.68413681, 1.21162831, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, -4.20173},
                        {0.6 - 2 * Math.PI, -0.56464247, 0.82533561, -0.68413681, 1.21162831, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, -4.20173},
                        {-1.2, -0.93203909, 0.36235775, -2.57215162, 2.75970360, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, -0.441296},
                        {1.2 - 2 * Math.PI, -0.93203909, 0.36235775, -2.57215162, 2.75970360, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, -0.441296},
                        {-1.8, -0.97384763, -0.22720209, 4.28626167, -4.40136787, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 0.245397},
                        {1.8 - 2 * Math.PI, -0.97384763, -0.22720209, 4.28626167, -4.40136787, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 0.245397},
                        {-2.6, -0.51550137, -0.85688875, 0.60159661, -1.16701263, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 5.79313},
                        {2.6 - 2 * Math.PI, -0.51550137, -0.85688875, 0.60159661, -1.16701263, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Constant.eps, 5.79313},
                };
    }

    @Test(dataProvider = "PositiveTestData")
    public void testEquationSystemSolver(Double x, Double sin, Double cos, Double tan, Double sec,
                                         Double ln, Double log2, Double log3, Double log10, Double eps,
                                         Double expected) {

    }

    private void mockTrig(Double x, Double sin, Double cos, Double tan, Double sec, Double eps) {
        when(mockITrig.sin(eq(x), eq(eps))).thenReturn(sin);
        when(mockITrig.cos(eq(x), eq(eps))).thenReturn(cos);
        when(mockITrig.tan(eq(x), eq(eps))).thenReturn(tan);
        when(mockITrig.sec(eq(x), eq(eps))).thenReturn(sec);
    }

    private void mockLogarithm(Double x, Double ln, Double log2, Double log3, Double log10, Double eps) {
        when(mockILogarithm.ln(eq(x), eq(eps))).thenReturn(ln);
        when(mockILogarithm.log(eq(x), eq(2.0), eq(eps))).thenReturn(log2);
        when(mockILogarithm.log(eq(x), eq(3.0), eq(eps))).thenReturn(log3);
        when(mockILogarithm.log(eq(x), eq(10.0), eq(eps))).thenReturn(log10);
    }
}
