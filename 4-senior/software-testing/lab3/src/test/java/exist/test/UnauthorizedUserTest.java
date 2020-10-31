package exist.test;

import exist.page.MainPage;
import exist.page.RegistrationPage;
import exist.page.SuccessfulRegistrationPage;
import lab3.framework.browser.BrowserSingleton;
import lab3.utils.RandomGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class UnauthorizedUserTest {
    private WebDriver driver;

    @Before
    public void setup() {
        driver = BrowserSingleton.getInstance();
        driver.manage().window().maximize();
    }

    @After
    public void teardown() {
        BrowserSingleton.resetInstance();
    }

    @Test
    public void testUserRegistration() {
        driver.get("https://exist.ru/");
        MainPage mainPage = new MainPage();
        Assertions.assertTrue(mainPage.isLoaded());

        RegistrationPage registrationPage = mainPage.gotoRegister();
        Assertions.assertTrue(registrationPage.isLoaded());

        registrationPage.clickRegisterAsNatural();
        Assertions.assertTrue(registrationPage.isNaturalPersonRadioActive());

        registrationPage.selectRandomCityFromList();
        registrationPage.selectFirstOfficeFromList();
        registrationPage.enterLastName(RandomGenerator.getRandomCyrillicWord(7));
        registrationPage.enterFirstName(RandomGenerator.getRandomCyrillicWord(5));
        registrationPage.enterParentName(RandomGenerator.getRandomCyrillicWord(8));
        registrationPage.enterPhone(RandomGenerator.generateRandomPhoneNumber());
        registrationPage.enterEmail(RandomGenerator.generateRandomEmail(10));
        registrationPage.tickAgreementBox();

        SuccessfulRegistrationPage successPage = registrationPage.clickSubmitBtn();
        Assertions.assertTrue(successPage.isLoaded());
    }

}
