package exist.test;

import exist.page.*;
import lab3.framework.browser.BrowserSingleton;
import lab3.utils.ConfigReader;
import lab3.utils.RandomGenerator;
import org.json.simple.JSONObject;
import org.junit.*;
import org.openqa.selenium.WebDriver;

public class UnauthorizedUserTest {
    private static JSONObject credentialsData;
    private static JSONObject testData;
    private WebDriver driver;

    @BeforeClass
    public static void setupData() {
        credentialsData = ConfigReader.readJSONConfig("src/test/resources/credentials.json");
        testData = ConfigReader.readJSONConfig("src/test/resources/data.json");
    }

    @Before
    public void setupDriver() {
        driver = BrowserSingleton.getInstance();
        driver.manage().window().maximize();
    }

    @After
    public void resetDriver() {
        BrowserSingleton.resetInstance();
    }

    @Test
    public void testUserRegistration() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        RegistrationPage registrationPage = mainPage.gotoRegister();
        Assert.assertTrue(registrationPage.isLoaded());

        registrationPage.clickRegisterAsNatural();
        Assert.assertTrue(registrationPage.isNaturalPersonRadioActive());

        registrationPage.selectRandomCityFromList();
        registrationPage.selectFirstOfficeFromList();
        registrationPage.enterLastName(RandomGenerator.getRandomCyrillicWord(7));
        registrationPage.enterFirstName(RandomGenerator.getRandomCyrillicWord(5));
        registrationPage.enterParentName(RandomGenerator.getRandomCyrillicWord(8));
        registrationPage.enterPhone(RandomGenerator.generateRandomPhoneNumber());
        registrationPage.enterEmail(RandomGenerator.generateRandomEmail(10));
        registrationPage.tickAgreementBox();

        SuccessfulRegistrationPage successPage = registrationPage.clickSubmitBtn();
        Assert.assertTrue(successPage.isLoaded());
    }

    @Test
    public void testUserAuthorization() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        mainPage.clickLoginBtn();
        Assert.assertTrue(mainPage.isLoginFormVisible());

        mainPage.enterUsername(credentialsData.get("username").toString());
        mainPage.enterPassword(credentialsData.get("password").toString());
        mainPage.clickAuthorizationBtn();

        Assert.assertEquals(mainPage.getCurrentUserName(), credentialsData.get("username").toString());
    }

    @Test
    public void testUserAuthorizationInvalid() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        mainPage.clickLoginBtn();
        Assert.assertTrue(mainPage.isLoginFormVisible());

        mainPage.enterUsername(RandomGenerator.generateRandomEmail(5));
        mainPage.enterPassword(RandomGenerator.generateRandomNumber(10));
        mainPage.clickAuthorizationBtn();

        Assert.assertTrue(mainPage.isAuthorizationFailed());
    }

    @Test
    public void testGeneralCatalogSearch() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        mainPage.clickCatalog();
        Assert.assertTrue(mainPage.isCatalogMenuVisible());

        GeneralCatalogPage generalCatalogPage = mainPage.gotoGeneralCatalog();
        Assert.assertTrue(generalCatalogPage.isLoaded());

        int selectedTransportTypeIndex = generalCatalogPage.clickRandomTransportType();

        VendorAutoPage vendorAutoPage = generalCatalogPage.clickRandomVendor(selectedTransportTypeIndex);
        Assert.assertTrue(vendorAutoPage.isLoaded());

        Assert.assertEquals(vendorAutoPage.getCurrentVendorFromPage(), vendorAutoPage.currentVendorExpected);
    }

    @Test
    public void testQuerySearch() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        SearchResultPage searchResultPage = mainPage.searchForQuery(testData.get("searchSample").toString());
        Assert.assertTrue(searchResultPage.isLoaded());

        Assert.assertTrue(searchResultPage.isResultCorrect());
    }

    @Test
    public void testGotoSellAutoService() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        SellAutoPage sellAutoPage = mainPage.gotoSellAutoService();
        Assert.assertTrue(sellAutoPage.isLoaded());
    }

    @Test
    public void testSearchForAutoSales() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        SellAutoPage sellAutoPage = mainPage.gotoSellAutoService();
        Assert.assertTrue(sellAutoPage.isLoaded());

        AutoSalesPage autoSalesPage = sellAutoPage.gotoSales();
        Assert.assertTrue(autoSalesPage.isLoaded());

        String selectedMake = autoSalesPage.selectRandomMake();
        String selectedModel = autoSalesPage.selectRandomModel();
        autoSalesPage.searchForCar();
        String carMakeModel = !selectedMake.equals("")
                ? (!selectedModel.equals("") ? selectedMake + " " + selectedModel : selectedMake)
                : "";
        Assert.assertTrue(autoSalesPage.isResultListCorrect(carMakeModel));
    }
}
