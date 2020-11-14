package lab3.framework.element;

public class InputField extends TextContainer {
    public InputField(String locator) {
        super(locator);
    }

    public void setValue(String value) {
        if (this.isClickable() && this.isVisible()) {
            this.getElement().clear();
            this.getElement().sendKeys(value);
        }
    }
}
