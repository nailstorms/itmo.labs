package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class ForumDiscussionPage {
    private Element forumDiscussionsContainer = new Element("//div[contains(@class,'page-blocks')]");

    private TextContainer forumDiscussionNameContainer = new TextContainer("//div[contains(@class,'content-page')]//h1");

    public String forumDiscussionNameExpected;

    public ForumDiscussionPage (String name) {
        this.forumDiscussionNameExpected = name;
    }

    public boolean isLoaded() {
        return this.forumDiscussionsContainer.isVisible();
    }

    public String getDiscussionName() {
        return this.forumDiscussionNameContainer.getText().trim();
    }
}
