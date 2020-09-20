package lab1.domain;

public class Leg {
    private Position position;
    private LegStatus status;

    public Leg() {
        this.position = Position.LEFT;
        this.status = LegStatus.FREE;
    }

    public Leg(Position position) {
        this.position = position;
        this.status = LegStatus.FREE;
    }

    public Leg(Position position, LegStatus status) {
        this.position = position;
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LegStatus getStatus() {
        return status;
    }

    public void setStatus(LegStatus status) {
        this.status = status;
    }
}
