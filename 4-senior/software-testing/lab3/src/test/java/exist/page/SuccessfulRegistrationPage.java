package exist.page;

import lab3.framework.element.Element;

public class SuccessfulRegistrationPage {
    private Element successfulRegistrationContainer = new Element("//div[contains(@class,'success')]");

    public boolean isLoaded() {
        return successfulRegistrationContainer.isVisible();
    }
}
