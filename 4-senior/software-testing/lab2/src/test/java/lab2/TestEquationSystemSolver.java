package lab2;

import lab2.impl.BaseLogarithm;
import lab2.impl.BaseTrig;
import lab2.impl.Logarithm;
import lab2.impl.Trig;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class TestEquationSystemSolver {
    private static final Double PRECISION = 1E-5;

    @Mock
    private ILogarithm mockILogarithm;

    @Mock
    private ITrig mockITrig;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "EquationSystemTestData")
    public Object[][] equationSystemTestData() {
        return new Object[][]
                {
                        {-1.570796, -1.0, 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {1.570796 - 2 * Math.PI, 1.0, 0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {-0.6, -0.56464247, 0.82533561, -0.68413681, 1.21162831, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -4.20173},
                        {-0.6 - 2 * Math.PI, -0.56464247, 0.82533561, -0.68413681, 1.21162831, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -4.20173},
                        {-1.2, -0.93203909, 0.36235775, -2.57215162, 2.75970360, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -0.4413},
                        {-1.2 - 2 * Math.PI, -0.93203909, 0.36235775, -2.57215162, 2.75970360, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -0.4413},
                        {-1.56, -0.99994172, 0.01079612, -92.62049632, 92.62589453, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -0.0108},
                        {-1.56 - 2 * Math.PI, -0.99994172, 0.01079612, -92.62049632, 92.62589453, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -0.0108},
                        {-1.58, -0.99995765, -0.00920354, 108.6492036, -108.65380547, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0.0092},
                        {-1.58 - 2 * Math.PI, -0.99995765, -0.00920354, 108.6492036, -108.65380547, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0.0092},
                        {-1.8, -0.97384763, -0.22720209, 4.28626167, -4.40136787, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0.2454},
                        {-1.8 - 2 * Math.PI, -0.97384763, -0.22720209, 4.28626167, -4.40136787, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0.2454},

                        {0.0, 0.0, 1.0, 0.0, 1.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Constant.eps, 0.0, 1.0, 0.0, 1.0, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {0.34441, 0.33764138, 0.94127482, 0.35870648, 1.06238898, -1.06592247, -1.53780106, -0.97024444, -0.46292425, -0.00435},
                        {0.344565, 0.33778728, 0.94122248, 0.35888143, 1.06244806, -1.06547253, -1.53715193, -0.96983489, -0.46272884, 1.0E-5},
                        {0.499, 0.47854771, 0.87806154, 0.54500475, 1.13887232, -0.69514918, -1.00288827, -0.63275205, -0.30189945, 1.52105},
                        {0.499263, 0.47877863, 0.87793566, 0.54534591, 1.13903563, -0.69462226, -1.0021382, -0.63227243, -0.30167061, 1.52106},
                        {0.5, 0.47942553, 0.87758256, 0.54630248, 1.13949393, -0.69314718, -1.0, -0.63092975, -0.30102999, 1.52104},
                        {1.0, 0.84147098, 0.54030231, 1.55740772, 1.85081572, 0.0, 0.0, 0.0, 0.0, 0.0},
                        {1.42017, 0.98867729, 0.1500574, 6.58866079, 6.66411667, 0.35077658, 0.50606364, 0.31929061, 0.15234033, -0.72054},
                        {20.5635, 0.35124526, 0.93628348, 0.37514841, 1.06805259, 3.02351766, 4.36201393, 2.75212398, 1.31309704, -248.58613}
                };
    }

    @DataProvider(name = "EquationSystemNegativeTestData")
    public Object[][] equationSystemNegativeTestData() {
        return new Object[][]
                {
                        {Double.NaN, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Double.POSITIVE_INFINITY, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Double.NEGATIVE_INFINITY, Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Constant.eps, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Constant.eps, Double.POSITIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                        {Constant.eps, Double.NEGATIVE_INFINITY, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                };
    }

    @Test(dataProvider = "EquationSystemTestData")
    public void testEquationSystemSolverStubbed(Double x, Double sin, Double cos, Double tan, Double sec,
                                         Double ln, Double log2, Double log3, Double log10,
                                         Double expected) {
        mockTrig(x, sin, cos, tan, sec);
        mockLogarithm(x, ln, log2, log3, log10);
        EquationSystemSolver solver = new EquationSystemSolver(mockILogarithm, mockITrig);
        Assert.assertEquals(solver.solve(x, Constant.eps), expected, PRECISION);
    }

    @Test(dataProvider = "EquationSystemTestData")
    public void testEquationSystemSolverLogStub(Double x, Double sin, Double cos, Double tan, Double sec,
                                                Double ln, Double log2, Double log3, Double log10,
                                                Double expected) {
        mockLogarithm(x, ln, log2, log3, log10);
        EquationSystemSolver solver = new EquationSystemSolver(mockILogarithm, new Trig(new BaseTrig()));
        Assert.assertEquals(solver.solve(x, Constant.eps), expected, PRECISION);
    }

    @Test(dataProvider = "EquationSystemTestData")
    public void testEquationSystemSolverTrigStub(Double x, Double sin, Double cos, Double tan, Double sec,
                                                Double ln, Double log2, Double log3, Double log10,
                                                Double expected) {
        mockTrig(x, sin, cos, tan, sec);
        EquationSystemSolver solver = new EquationSystemSolver(new Logarithm(new BaseLogarithm()), mockITrig);
        Assert.assertEquals(solver.solve(x, Constant.eps), expected, PRECISION);
    }

    @Test(dataProvider = "EquationSystemTestData")
    public void testEquationSystemSolver(Double x, Double sin, Double cos, Double tan, Double sec,
                                                 Double ln, Double log2, Double log3, Double log10,
                                                 Double expected) {
        EquationSystemSolver solver = new EquationSystemSolver(new Logarithm(new BaseLogarithm()), new Trig(new BaseTrig()));
        Assert.assertEquals(solver.solve(x, Constant.eps), expected, PRECISION);
    }

    @Test(dataProvider = "EquationSystemNegativeTestData")
    public void testEquationSystemSolverNegative(Double x, Double eps,
                                                    Double sin, Double cos, Double tan, Double sec,
                                                    Double ln, Double log2, Double log3, Double log10,
                                                    Double expected) {
        EquationSystemSolver solver = new EquationSystemSolver(new Logarithm(new BaseLogarithm()), new Trig(new BaseTrig()));
        Assert.assertEquals(solver.solve(x, Constant.eps), expected, PRECISION);
    }

    private void mockTrig(Double x, Double sin, Double cos, Double tan, Double sec) {
        when(mockITrig.sin(eq(x), anyDouble())).thenReturn(sin);
        when(mockITrig.cos(eq(x), anyDouble())).thenReturn(cos);
        when(mockITrig.tan(eq(x), anyDouble())).thenReturn(tan);
        when(mockITrig.sec(eq(x), anyDouble())).thenReturn(sec);
    }

    private void mockLogarithm(Double x, Double ln, Double log2, Double log3, Double log10) {
        when(mockILogarithm.ln(eq(x), anyDouble())).thenReturn(ln);
        when(mockILogarithm.log(eq(x), eq(2.0), anyDouble())).thenReturn(log2);
        when(mockILogarithm.log(eq(x), eq(3.0), anyDouble())).thenReturn(log3);
        when(mockILogarithm.log(eq(x), eq(10.0), anyDouble())).thenReturn(log10);
    }
}
