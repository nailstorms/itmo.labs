package lab3;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public abstract class AbstractCharacters {
    Integer id;
    String name;
    double height;
    double weight;

    LocalDate dateOfBirth = LocalDate.now();
    double x = 0;
    double y = 0;


    public String getName() {
        return name;
    }
    public AbstractCharacters(String name){
        this.name = name;
    }
    public AbstractCharacters(String name, double height){
        this.name = name;
        this.height = height;
    }
    public AbstractCharacters(String name, double height, double weight){
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    public double getX() {return x;}
    public double getY() {return y;}

    public AbstractCharacters(String name, double height, LocalDate date){
        this.name = name;
        this.height = height;
        this.dateOfBirth = date;
    }
    public AbstractCharacters(String name, double height, double weight, LocalDate date){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.dateOfBirth = date;
    }

    public AbstractCharacters(String name, double height, LocalDate date, double x, double y){
        this.name = name;
        this.height = height;
        this.x = x;
        this.y = y;
        this.dateOfBirth = date;
    }

    public AbstractCharacters(String name, double height, double weight, LocalDate date, double x, double y){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.x = x;
        this.y = y;
        this.dateOfBirth = date;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCharacters that = (AbstractCharacters) o;

        if (height != that.height) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) height;
        return result;
    }

    @Override
    public String toString(){
        return "ID: " + id + ". Name: " + name + ". Height: " + height + ". Weight: " + weight;
    }
}
