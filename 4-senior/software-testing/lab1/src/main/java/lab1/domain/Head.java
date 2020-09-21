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

    public Position getPosition() {
        return position;
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
}
