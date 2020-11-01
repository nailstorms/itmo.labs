package lab3.framework.element;

public class TextContainer extends Element {
    public TextContainer(String locator) {
        super(locator);
    }

    public String getText() {
        if (this.isExisting())
            return this.getElement().getText();
        return "";
    }
}
