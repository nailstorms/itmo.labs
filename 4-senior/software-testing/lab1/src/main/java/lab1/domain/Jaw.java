package lab1.domain;

class Jaw {
    private JawStatus status;

    public Jaw() {
        this.status = JawStatus.IN_PLACE;
    }

    public JawStatus getStatus() {
        return status;
    }

    public void setStatus(JawStatus status) {
        this.status = status;
    }
}
