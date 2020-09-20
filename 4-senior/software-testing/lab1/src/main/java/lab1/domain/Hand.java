package lab1.domain;

class Hand {
    private Position position;
    private HandStatus status;

    public Hand() {
        this.position = Position.LEFT;
        this.status = HandStatus.FREE;
    }

    public Hand(Position position) {
        this.position = position;
        this.status = HandStatus.FREE;
    }

    public Hand(Position position, HandStatus status) {
        this.position = position;
        this.status = status;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public HandStatus getStatus() {
        return status;
    }

    public void setStatus(HandStatus status) {
        this.status = status;
    }
}
