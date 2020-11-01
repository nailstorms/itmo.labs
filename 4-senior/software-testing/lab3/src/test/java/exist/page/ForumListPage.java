package exist.page;

import lab3.framework.element.ElementList;
import lab3.framework.element.TextContainer;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ForumListPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Форум']");

    private ElementList forumsList = new ElementList("//div[@class='forumtitle']");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public ForumPage gotoRandomForum() {
        List<WebElement> elements = this.forumsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String forumName = randomElement.getText();
        randomElement.click();
        return new ForumPage(forumName);
    }
}
