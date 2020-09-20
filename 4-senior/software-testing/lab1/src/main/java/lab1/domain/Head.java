package lab1.domain;

class Head {
    private Position position;
    private HeadMood mood;
    private Jaw jaw;

    public Head() {
        this.position = Position.MIDDLE;
        this.mood = HeadMood.NEUTRAL;
        this.jaw = new Jaw();
    }

    public Head(Position position) {
        this.position = position;
        this.mood = HeadMood.NEUTRAL;
        this.jaw = new Jaw();
    }

    public Head(HeadMood mood) {
        this.position = Position.MIDDLE;
        this.mood = mood;
        this.jaw = new Jaw();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public HeadMood getMood() {
        return mood;
    }

    public void setMood(HeadMood mood) {
        this.mood = mood;
    }

    public Jaw getJaw() {
        return jaw;
    }

    public void setJaw(Jaw jaw) {
        this.jaw = jaw;
    }
}
