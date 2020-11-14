package exist.page;

import lab3.framework.element.TextContainer;

public class PersonalPage {
    private TextContainer currentPageContainer = new TextContainer("//*[contains(@class,'menu-curent-node') and normalize-space(text())='Личный кабинет']");

    private TextContainer currentUserNameContainer = new TextContainer("//*[contains(@id,'hlClientName')]");

    public boolean isLoaded() {
        return currentPageContainer.isVisible();
    }

    public String getCurrentUserNameFromPage() {
        return this.currentUserNameContainer.getText();
    }
}
