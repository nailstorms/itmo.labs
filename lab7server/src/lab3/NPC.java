package lab3;

import lab8.orm.PrimaryKey;
import lab8.orm.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

@Table(name = "npcs")

public class NPC implements Comparable<NPC>, Serializable {
    @PrimaryKey
    public int id;

    public String name;
    public double height;
    public double weight;

    public LocalDateTime dateOfBirth = LocalDateTime.now();
    public double x = 0;
    public double y = 0;
    public String color;

    public BeautyLevel beauty;
    public ChinSharpness chin;


    public NPC() {}

    public NPC (int id, String name, double height, double beauty, double chin){
        this.name = name;
        this.height = height;
        this.id = id;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }

    public NPC (String name, double height, double beauty, double chin){
        this.name = name;
        this.height = height;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }


    public NPC (int id, String name, double height, LocalDateTime date, double beauty, double chin){
        this.name = name;
        this.height = height;
        this.dateOfBirth = date;
        this.id = id;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }

    public NPC (String name, double height, LocalDateTime date, double beauty, double chin){
        this.name = name;
        this.height = height;
        this.dateOfBirth = date;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }

    public NPC (int id, String name, double height, LocalDateTime date, double beauty, double chin, double x, double y){
        this.name = name;
        this.height = height;
        this.x = x;
        this.y = y;
        this.dateOfBirth = date;
        this.id = id;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }

    public NPC (String name, double height, LocalDateTime date, double beauty, double chin, double x, double y){
        this.name = name;
        this.height = height;
        this.x = x;
        this.y = y;
        this.dateOfBirth = date;
        if (beauty > 100.0 || beauty < 0.0) throw new LevelException(beauty);
        else {
            if (beauty >= 66.7 && beauty <= 100.0)
                this.beauty = BeautyLevel.Beautiful;
            else if (beauty >= 33.3 && beauty <= 100.0)
                this.beauty = BeautyLevel.Normal;
            else
                this.beauty = BeautyLevel.Hideous;
        }

        if (chin > 100.0 || chin < 0.0) throw new LevelException(chin);
        else {
            if (chin <= 33.3)
                this.chin = ChinSharpness.Flat;
            else if (chin <= 66.7)
                this.chin = ChinSharpness.Normal;
            else
                this.chin = ChinSharpness.Sharp;
        }
    }

    public Integer getId() {
        return id;
    }

    public BeautyLevel getBeauty(){
        return beauty;
    }
    public ChinSharpness getChin(){
        return chin;
    }
    public String getNPCName() {
        return this.name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NPC npc = (NPC) o;

        if (this.beauty != npc.beauty) return false;
        if (this.chin != npc.chin) return false;

        return x == npc.x;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), beauty, chin, x, y);
    }

//    @Override
//    public String toString(){
//        return "ID: " + id + ". Имя: " + name + ". Рост: " + height + ". Вес: " + weight;
//    }

    @Override
    public String toString(){
        return id + "; " + name + "; " + height + "; " + weight + "; " + dateOfBirth + "; " + x + "; " + y + "; " + this.getBeauty() + "; " + this.getChin() + "; " + color;
    }

    @Override
    public int compareTo (NPC comp) {

        final int lesser = -1;
        final int equal = 0;
        final int greater = 1;

        if (this.equals(comp)) return equal;

        if (this.name.compareTo(comp.name) < 0) return lesser;
        if (this.name.compareTo(comp.name) > 0) return greater;

        return equal;
    }

    public int getWeight() {
        return (int) this.weight;
    }

    public int getHeight() {
        return (int) this.height;
    }

    public LocalDateTime getDateOfBirth() {
        return this.dateOfBirth;
    }

    public int getNpcX() {
        return (int) this.x;
    }

    public int getNpcY() {
        return (int) this.y;
    }

    public String getColor() {
        return this.color;
    }

    public void setId (int id) {
        this.id = id;
    }
}
