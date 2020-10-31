package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.framework.element.InputField;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RegistrationPage {
    private Element registrationHeader = new Element("//div[contains(@class,'page-content-wrapper')]//*[normalize-space(text())='Регистрация']");

    private Element naturalPersonBtn = new Element("//div[contains(@class,'type-buttons')]//*[@id='slbFizFace']");
    private Element naturalPersonRadio = new Element("//div[contains(@class,'radio')]//*[@id='TaxType_0']");
    private InputField cityInputField = new InputField("//div[@id='regform']//input[@id='txtCity']");
    private ElementList availableCitiesList = new ElementList("//*[@id='itemsCityList']//a");
    private Element officeDropdown = new Element("//div[@id='regform']//div[@id='ddlOfficeList']");
    private ElementList officeDropdownTableEntries = new ElementList("//table[@id='office-container']//tr[not(contains(@class,'header'))]");
    private InputField lastNameInputField = new InputField("//div[@id='regform']//input[@id='tbLastName']");
    private InputField firstNameInputField = new InputField("//div[@id='regform']//input[@id='tbFirstName']");
    private InputField parentNameInputField = new InputField("//div[@id='regform']//input[@id='tbParentName']");
    private InputField phoneInputField = new InputField("//div[@id='regform']//input[@id='tbMobileTelephone']");
    private InputField emailInputField = new InputField("//div[@id='regform']//input[@id='tbEmail']");
    private Element rulesTickbox = new Element("//div//*[@for='chkRules']");
    private Element submitBtn = new Element("//input[@id='btnSubmit']");

    public boolean isLoaded() {
        return registrationHeader.isVisible();
    }

    public void clickRegisterAsNatural() {
        this.naturalPersonBtn.click();
    }

    public boolean isNaturalPersonRadioActive() {
        if (!this.naturalPersonRadio.isExisting()) return false;
        return this.naturalPersonRadio.getAttribute("checked").equals("true");
    }

    public void enterCity(String city) {
        this.cityInputField.setValue(city);
    }

    public void selectRandomCityFromList() {
        this.cityInputField.setValue(RandomGenerator.getRandomCyrillicLetter());
        List<WebElement> elements = this.availableCitiesList.getElements();
        elements.get(RandomGenerator.getRandomNumber(elements.size())).click();
    }

    public void selectOffice(int officeNum) {
        this.officeDropdown.click();
        this.officeDropdownTableEntries.getElements().get(officeNum).click();
    }

    public void selectFirstOfficeFromList() {
        this.officeDropdown.click();
        List<WebElement> elements = this.officeDropdownTableEntries.getElements();
        elements.get(0).click();
    }

    public void enterFirstName(String firstName) {
        this.firstNameInputField.setValue(firstName);
    }

    public void enterLastName(String lastName) {
        this.lastNameInputField.setValue(lastName);
    }

    public void enterParentName(String parentName) {
        this.parentNameInputField.setValue(parentName);
    }

    public void enterPhone(String phoneNumber) {
        this.phoneInputField.setValue(phoneNumber);
    }

    public void enterEmail(String email) {
        this.emailInputField.setValue(email);
    }

    public void tickAgreementBox() {
        this.rulesTickbox.click();
    }

    public SuccessfulRegistrationPage clickSubmitBtn() {
        this.submitBtn.click();
        return new SuccessfulRegistrationPage();
    }
}
