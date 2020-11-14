package exist.test;

import exist.page.*;
import lab3.framework.browser.BrowserSingleton;
import lab3.utils.ConfigReader;
import lab3.utils.RandomGenerator;
import org.json.simple.JSONObject;
import org.junit.*;
import org.openqa.selenium.WebDriver;


public class AuthorizedUserTest {
    private static JSONObject credentialsData;
    private static JSONObject testData;
    private WebDriver driver;

    @BeforeClass
    public static void setupData() {
        credentialsData = ConfigReader.readJSONConfig("src/test/resources/credentials.json");
        testData = ConfigReader.readJSONConfig("src/test/resources/data.json");
    }

    @Before
    public void setup() {
        driver = BrowserSingleton.getInstance();
        driver.manage().window().maximize();
    }

    @After
    public void resetDriver() {
        BrowserSingleton.resetInstance();
    }

    @Test
    public void testPersonalPage() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        PersonalPage personalPage = mainPage.gotoPersonalPage();
        Assert.assertTrue(personalPage.isLoaded());

        Assert.assertEquals(credentialsData.get("name").toString(), personalPage.getCurrentUserNameFromPage());
    }

    @Test
    public void testGotoBasket() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        BasketPage basketPage = mainPage.gotoBasket();
        Assert.assertTrue(basketPage.isLoaded());

        Assert.assertTrue(basketPage.isBasketEmpty());
    }

    @Test
    public void testGotoOrders() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        OrderHistoryPage orderHistoryPage = mainPage.gotoOrderHistory();
        Assert.assertTrue(orderHistoryPage.isLoaded());

        Assert.assertEquals("0", orderHistoryPage.getCurrentOrderCountStr());
        Assert.assertEquals("0", orderHistoryPage.getArchivedOrderCountStr());
    }


    @Test
    public void testGotoForum() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        ForumListPage forumListPage = mainPage.gotoForums();
        Assert.assertTrue(forumListPage.isLoaded());

        ForumPage forumPage = forumListPage.gotoRandomForum();
        Assert.assertTrue(forumPage.isLoaded());
        Assert.assertTrue(forumPage.isCorrectForum());

        ForumDiscussionPage forumDiscussionPage = forumPage.gotoRandomForumDiscussion();
        Assert.assertTrue(forumDiscussionPage.isLoaded());
        Assert.assertEquals(forumDiscussionPage.forumDiscussionNameExpected, forumDiscussionPage.getDiscussionName());
    }

    @Test
    public void testAddToAndRemoveFromBasket() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        BasketPage basketPage = this.addToBasket(mainPage);

        basketPage.clickDeleteProductBtn();
        Assert.assertTrue(basketPage.isBasketEmpty());
    }

    @Test
    public void testProceedToOrder() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        BasketPage basketPage = this.addToBasket(mainPage);
        String selectedProductName = basketPage.getProductName();
        String selectedProductPrice = basketPage.getProductPrice();

        OrderPage orderPage = basketPage.gotoOrder();
        Assert.assertTrue(orderPage.isLoaded());

        Assert.assertEquals(selectedProductName, orderPage.getOrderedProductName());
        Assert.assertEquals(selectedProductPrice, orderPage.getOrderedProductPrice());
        Assert.assertEquals(credentialsData.get("name").toString(), orderPage.getClientName());
        Assert.assertEquals(credentialsData.get("username").toString(), orderPage.getClientPhone());
        Assert.assertEquals(credentialsData.get("email").toString(), orderPage.getClientEmail());

        driver.navigate().back();
        basketPage.clickDeleteProductBtn();
        Assert.assertTrue(basketPage.isBasketEmpty());
    }

    @Test
    public void addToAndRemoveCarFromGarage() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        GaragePage garagePage = this.addCarToGarage(mainPage);

        garagePage.removeCarFromGarage();
        Assert.assertTrue(garagePage.isRemoveCarModalVisible());

        garagePage.clickRemoveConfirm();
        Assert.assertTrue(garagePage.isGarageEmpty());
    }

    @Test
    public void addAndRemoveVINRequest() {
        driver.get(testData.get("mainUrl").toString());
        MainPage mainPage = this.authenticateUser();

        GaragePage garagePage = this.addCarToGarage(mainPage);

        VinRequestPage vinRequestPage = garagePage.gotoVinRequests();
        Assert.assertTrue(vinRequestPage.isLoaded());

        vinRequestPage.selectCar();
        Assert.assertTrue(vinRequestPage.isOnVinRequestPage());

        vinRequestPage.selectPart(testData.get("part").toString());
        String addedPart = vinRequestPage.getAddedPartText();
        Assert.assertEquals(testData.get("part").toString(), addedPart);

        vinRequestPage.enterMileage(RandomGenerator.generateRandomNumber(5));

        VinRequestsListPage vinRequestsListPage = vinRequestPage.submitRequest();
        Assert.assertTrue(vinRequestsListPage.isLoaded());

        Assert.assertEquals(addedPart, vinRequestsListPage.getFirstRequestPartName().trim());
        Assert.assertEquals(testData.get("requestStatus").toString(), vinRequestsListPage.getFirstRequestStatus().trim());

        vinRequestsListPage.cancelRequest();
        Assert.assertNotEquals(addedPart, vinRequestsListPage.getFirstRequestPartName().trim());

        vinRequestsListPage.clickGarageBtn();
        Assert.assertTrue(vinRequestsListPage.isCarNameVisible());

        garagePage = vinRequestsListPage.gotoGarage();
        Assert.assertTrue(garagePage.isLoaded());

        garagePage.removeCarFromGarage();
        Assert.assertTrue(garagePage.isRemoveCarModalVisible());

        garagePage.clickRemoveConfirm();
        Assert.assertTrue(garagePage.isGarageEmpty());
    }

    private MainPage authenticateUser() {
        MainPage mainPage = new MainPage();
        Assert.assertTrue(mainPage.isLoaded());

        mainPage.clickLoginBtn();
        Assert.assertTrue(mainPage.isLoginFormVisible());

        mainPage.enterUsername(credentialsData.get("username").toString());
        mainPage.enterPassword(credentialsData.get("password").toString());
        mainPage.clickAuthorizationBtn();

        Assert.assertEquals(mainPage.getCurrentUserName(), credentialsData.get("username").toString());
        return mainPage;
    }

    private BasketPage addToBasket(MainPage mainPage) {
        mainPage.clickCatalog();
        Assert.assertTrue(mainPage.isCatalogMenuVisible());

        MotorOilPage motorOilPage = mainPage.gotoMotorOilCatalog();
        Assert.assertTrue(motorOilPage.isLoaded());

        String firstProductName = motorOilPage.getFirstProductName();
        motorOilPage.clickFirstProduct();
        Assert.assertTrue(motorOilPage.isSelectedProductWindowVisible());

        motorOilPage.switchToCurrentProductFrame();
        Assert.assertEquals(firstProductName, motorOilPage.getSelectedProductName());
        String selectedProductPrice = motorOilPage.getSelectedProductPrice();
        motorOilPage.clickAddToBasketBtn();

        motorOilPage.switchToMainWindow();
        motorOilPage.closeSelectedProductWindow();
        Assert.assertTrue(motorOilPage.isSelectedProductWindowInvisible());

        BasketPage basketPage = motorOilPage.gotoBasket();
        Assert.assertTrue(basketPage.isLoaded());

        Assert.assertEquals(firstProductName, basketPage.getProductName().replaceAll("\n", " "));
        Assert.assertEquals(selectedProductPrice, basketPage.getProductPrice());

        return basketPage;
    }

    private GaragePage addCarToGarage(MainPage mainPage) {
        mainPage.clickGarageBtn();
        Assert.assertTrue(mainPage.isGarageMenuVisible());

        AddNewCarPage addNewCarPage = mainPage.gotoAddNewCar();
        Assert.assertTrue(addNewCarPage.isLoaded());

        addNewCarPage.enterCarVin(credentialsData.get("vin").toString());
        Assert.assertTrue(addNewCarPage.isCarFound());
        Assert.assertEquals(testData.get("carName").toString(), addNewCarPage.getFoundCarName());

        addNewCarPage.selectFoundCar();
        Assert.assertTrue(addNewCarPage.isFoundCarMenuInvisible());

        Assert.assertEquals(testData.get("carReleaseYear").toString(), addNewCarPage.getCarReleaseYear());
        Assert.assertEquals(testData.get("carVendor").toString(), addNewCarPage.getCarProducer());
        Assert.assertEquals(testData.get("carModel").toString(), addNewCarPage.getCarModel());
        Assert.assertEquals(testData.get("carModelType").toString(), addNewCarPage.getCarModelType());
        Assert.assertEquals(testData.get("carEngine").toString(), addNewCarPage.getCarEngine());
        Assert.assertEquals(testData.get("carPower").toString(), addNewCarPage.getCarPower());

        GaragePage garagePage = addNewCarPage.saveCar();
        Assert.assertTrue(garagePage.isLoaded());

        Assert.assertEquals(testData.get("carName").toString(), garagePage.getCarName());
        Assert.assertTrue(garagePage.getCarReleaseYear().contains(testData.get("carReleaseYear").toString()));

        return garagePage;
    }
}
