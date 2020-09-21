package lab1.domain;

class Hand {
    private Position position;
    private HandStatus status;

    public Hand(Position position) {
        this.position = position;
        this.status = HandStatus.FREE;
    }

    public Position getPosition() {
        return position;
    }

    public HandStatus getStatus() {
        return status;
    }

    public void setStatus(HandStatus status) {
        this.status = status;
    }
}
