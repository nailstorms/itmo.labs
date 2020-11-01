package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.InputField;
import lab3.framework.element.TextContainer;

public class MainPage {
    private Element logoContainer = new Element("//*[contains(@title,'Автозапчасти EXIST.RU')]");

    private Element registrationBtn = new Element("//div[contains(@class,'header-login__registration')]");
    private Element loginBtn = new Element("//div[contains(@class,'header-login__name')]");
    private Element loginFormContainer = new Element("//div[contains(@class,'header-login__form')]");
    private InputField usernameInputField = new InputField("//div[contains(@class,'header-login__form')]//input[@name='login']");
    private InputField passwordInputField = new InputField("//div[contains(@class,'header-login__form')]//input[@name='pass']");
    private Element authorizationBtn = new Element("//div[contains(@class,'header-login__form')]//input[@id='btnLogin']");
    private TextContainer usernameContainer = new TextContainer("//div[contains(@class,'header-login__name')]//span");
    private Element catalogBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Каталог')]");
    private Element catalogContainer = new Element("//div[contains(@class,'catalogs-float__container')]");
    private Element generalCatalogBtn = new Element("//div[contains(@class,'catalogs-float__container')]//*[normalize-space(text())='Общий каталог']");
    private Element motorOilCatalogBtn = new Element("//div[contains(@class,'catalogs-float__container')]//*[normalize-space(text())='Масла моторные']");
    private Element sellAutoBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Продажа авто')]");
    private Element autoPointsBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'АвтоТочки')]");
    private Element forumBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Форум')]");
    private Element clubBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Клуб')]");
    private Element garageBtn = new Element("//div[contains(@class,'garage')]//*[contains(text(),'Гараж')]");
    private Element garageMenuContainer = new Element("//div[contains(@class,'carSelectionMode')]");
    private Element addNewCarBtn = new Element("//div[contains(@class,'carSelectionMode')]//*[@id='addNew']");
    private Element gotoBasketBtn = new Element("//*[contains(@class,'shop-functions__cart')]");
    private Element gotoOrdersBtn = new Element("//*[contains(@class,'shop-functions__orders')]");
    private InputField searchInputField = new InputField("//form[@id='search-form']//input[contains(@class,'search-input')]");
    private Element searchArticleSuggestionContainer = new Element("//form[@id='search-form']//*[contains(normalize-space(text()),'Поиск по Артикулу')]");

    public boolean isLoaded() {
        return logoContainer.isVisible();
    }

    public RegistrationPage gotoRegister() {
        this.registrationBtn.click();
        return new RegistrationPage();
    }

    public void clickLoginBtn() {
        this.loginBtn.click();
    }

    public void enterUsername(String username) {
        this.usernameInputField.setValue(username);
    }

    public void enterPassword(String password) {
        this.passwordInputField.setValue(password);
    }

    public void clickAuthorizationBtn() {
        this.authorizationBtn.click();
    }

    public boolean isLoginFormVisible() {
        return this.loginFormContainer.isVisible();
    }

    public String getCurrentUserName() {
        return this.usernameContainer.getText();
    }

    public void clickCatalog() {
        this.catalogBtn.click();
    }

    public boolean isCatalogMenuVisible() {
        return this.catalogContainer.isVisible();
    }

    public GeneralCatalogPage gotoGeneralCatalog() {
        this.generalCatalogBtn.click();
        return new GeneralCatalogPage();
    }

    public MotorOilPage gotoMotorOilCatalog() {
        this.motorOilCatalogBtn.click();
        return new MotorOilPage();
    }

    public SearchResultPage searchForQuery(String query) {
        this.searchInputField.setValue(query);
        this.searchArticleSuggestionContainer.click();
        return new SearchResultPage(query);
    }

    public BasketPage gotoBasket() {
        this.gotoBasketBtn.click();
        return new BasketPage();
    }

    public OrderHistoryPage gotoOrderHistory() {
        this.gotoOrdersBtn.click();
        return new OrderHistoryPage();
    }

    public void clickGarageBtn() {
        this.garageBtn.click();
    }

    public boolean isGarageMenuVisible() {
        return this.garageMenuContainer.isVisible();
    }

    public AddNewCarPage gotoAddNewCar() {
        this.addNewCarBtn.clickJs();
        return new AddNewCarPage();
    }
}
