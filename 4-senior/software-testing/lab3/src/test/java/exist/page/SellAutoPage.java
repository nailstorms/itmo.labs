package exist.page;

import lab3.framework.browser.BrowserSingleton;
import lab3.framework.element.Element;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class SellAutoPage {
    private Element logoContainer = new Element("//div[@class='logo']//*[contains(@alt,'Логотип')]");

    private Element salePostsBtn = new Element("//div[@class='mainmenu']//*[@class='dashboard__go_to_exist' and normalize-space(text())='Объявления']");

    public SellAutoPage() {
        String firstHandle = BrowserSingleton.getInstance().getWindowHandle();
        new WebDriverWait(BrowserSingleton.getInstance(), BrowserSingleton.getWaitTime()).
                until(ExpectedConditions.numberOfWindowsToBe(2));
        ArrayList<String> allHandles = BrowserSingleton.getTabs();
        for (String winHandle : allHandles) {
            if (!firstHandle.equalsIgnoreCase(winHandle)) {
                BrowserSingleton.getInstance().switchTo().window(winHandle);
            }
        }
    }

    public boolean isLoaded() {
        return logoContainer.isVisible();
    }

    public AutoSalesPage gotoSales() {
        this.salePostsBtn.click();
        return new AutoSalesPage();
    }
}
