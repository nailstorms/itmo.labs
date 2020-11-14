package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.framework.element.TextContainer;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ForumPage {
    private Element forumDiscussionsContainer = new Element("//div[contains(@class,'page-blocks')]");

    private String forumNameContainerLocatorTemplate = "//div[contains(@class,'content-page')]//*[normalize-space(text())='%s']";

    private ElementList firstPageDiscussionsList = new ElementList("//div[@id='forumtree']//div[@class='message-subject']");

    public String forumNameExpected;

    public ForumPage (String name) {
        this.forumNameExpected = name;
    }

    public boolean isLoaded() {
        return this.forumDiscussionsContainer.isVisible();
    }

    public boolean isCorrectForum() {
        TextContainer forumNameContainer = new TextContainer(String.format(forumNameContainerLocatorTemplate, forumNameExpected.replace("'", "\\'")));
        return forumNameContainer.isExisting() && forumNameContainer.isVisible();
    }

    public ForumDiscussionPage gotoRandomForumDiscussion() {
        List<WebElement> elements = this.firstPageDiscussionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String discussionName = randomElement.getText();
        randomElement.click();
        return new ForumDiscussionPage(discussionName);
    }
}
