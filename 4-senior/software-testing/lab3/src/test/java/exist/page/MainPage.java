package exist.page;

import lab3.framework.element.Element;

public class MainPage {
    private Element logoContainer = new Element("//*[contains(@title,'Автозапчасти EXIST.RU')]");

    private Element registrationBtn = new Element("//div[contains(@class,'header-login__registration')]");
    private Element loginBtn = new Element("//div[contains(@class,'header-login__name')]");
    private Element catalogBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Каталог')]");
    private Element catalogContainer = new Element("//div[contains(@class,'catalogs-float__container')]");
    private Element generalCatalogBtn = new Element("//div[contains(@class,'catalogs-float__container')]//*[normalize-space(text())='Общий каталог']");
    private Element vinRequestBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Запрос по VIN')]");
    private Element sellAutoBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Продажа авто')]");
    private Element autoPointsBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'АвтоТочки')]");
    private Element forumBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Форум')]");
    private Element clubBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Клуб')]");
    private Element garageBtn = new Element("//div[contains(@class,'garage')]//*[contains(text(),'Гараж')]");
    private Element gotoGarageBtn = new Element("//div[contains(@class,'garage-switch')]//*[contains(@class,'garageLinkPanel')]");
    private Element searchInputField = new Element("//input[contains(@class,'search-input')]");

    public boolean isLoaded() {
        return logoContainer.isVisible();
    }

    public RegistrationPage gotoRegister() {
        this.registrationBtn.click();
        return new RegistrationPage();
    }

    public LoginPage gotoLogin() {
        this.loginBtn.click();
        return new LoginPage();
    }

    public void clickCatalog() {
        this.catalogBtn.click();
    }

    public boolean isCatalogMenuVisible() {
        return this.catalogContainer.isVisible();
    }

    // TODO lolol
    public void gotoGeneralCatalog() {
        this.generalCatalogBtn.click();
    }
}
