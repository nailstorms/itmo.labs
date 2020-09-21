package lab1.domain;

public class Leg {
    private Position position;
    private LegStatus status;

    public Leg(Position position) {
        this.position = position;
        this.status = LegStatus.FREE;
    }

    public Position getPosition() {
        return position;
    }

    public LegStatus getStatus() {
        return status;
    }

    public void setStatus(LegStatus status) {
        this.status = status;
    }
}
