package lab1.math;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import lab1.util.DoubleUtil;

public class TestArctan {
    @BeforeClass
    public void setupData(ITestContext context) {
        context.setAttribute("eps", 10000000);
        context.setAttribute("roundPlaces", 6);
    }

    @DataProvider(name = "SmokeTestData")
    public Object[][] smokeTestData() {
        return new Object[][]
                {
                        {"Метод корректно работает на валидном входном наборе", 0.0}
                };
    }

    @DataProvider(name = "PositiveTestData")
    public Object[][] positiveTestData() {
        return new Object[][]
                {
                        {"Метод корректно вычисляет значение при положительном x на отрезке с ростом, близким к линейному", 0.2},
                        {"Метод корректно вычисляет значение при отрицательном x на отрезке с ростом, близким к линейному", -0.2},
                        {"Метод корректно вычисляет значение при положительном x на отрезке со сменой роста", 0.5},
                        {"Метод корректно вычисляет значение при отрицательном x на отрезке со сменой роста", -0.5},
                        {"Метод корректно вычисляет значение при положительном x на отрезке с ростом, близким к логарифмическому", 0.8},
                        {"Метод корректно вычисляет значение при отрицательном x на отрезке с ростом, близким к логарифмическому", -0.8},
                        {"Метод корректно вычисляет значение при положительном граничном значении x", 1.0},
                        {"Метод корректно вычисляет значение при отрицательном граничном значении x", -1.0}
                };
    }

    @DataProvider(name = "NegativeTestData")
    public Object[][] negativeTestData() {
        return new Object[][]
                {
                        {"Метод не принимает (minXValue - 0.01)", -1.01},
                        {"Метод не принимает (maxXValue + 0.01)", 1.01}
                };
    }

    @Test(dataProvider = "SmokeTestData")
    public void testArctan1Smoke(String testcaseName, Double x, ITestContext context) {
        testArctanBase(x, context);
    }

    @Test(dataProvider = "PositiveTestData")
    public void testArctan2Positive(String testcaseName, Double x, ITestContext context) {
        testArctanBase(x, context);
    }

    @Test(dataProvider = "NegativeTestData")
    public void testArctan3Negative(String testcaseName, Double x, ITestContext context) {
        Double actualValue = Arctan.computeTaylorSeries(x, (Integer) context.getAttribute("eps"));
        Assert.assertNull(actualValue, "Ошибка при вычислении.");
    }

    private void testArctanBase(Double x, ITestContext context) {
        Double actualValue = Arctan.computeTaylorSeries(x, (Integer) context.getAttribute("eps"));
        Double expectedValue = Math.atan(x);
        Assert.assertEquals(DoubleUtil.round(actualValue, (Integer) context.getAttribute("roundPlaces")),
                DoubleUtil.round(expectedValue, (Integer) context.getAttribute("roundPlaces")),
                "Ошибка при вычислении.");
    }
}
