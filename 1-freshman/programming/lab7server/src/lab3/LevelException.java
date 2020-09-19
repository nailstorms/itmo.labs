package lab3;

public class LevelException extends RuntimeException {
    public LevelException() { }
    public LevelException(double level) {
        super("Уровень " + Double.toString(level) + " выходит за рамки доступных уровней.");
    }
}