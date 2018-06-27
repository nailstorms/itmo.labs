package lab3;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

public class NPC extends AbstractCharacters implements Actions, Comparable<NPC>, Serializable {
    String color;

    OffsetDateTime creationTime = OffsetDateTime.now();


    static class Characteristics {
        BeautyLevel beauty;
        ChinSharpness chin;
        Characteristics (double beauty, double chin){
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
    }
    private Characteristics levels;

    public NPC (int id, String name, double height, double beauty, double chin){
        super(name,height);
        this.id = id;
        this.levels = new Characteristics(beauty, chin);
    }

    public NPC (String name, double height, double beauty, double chin){
        super(name,height);
        this.levels = new Characteristics(beauty, chin);
    }


    public NPC (int id, String name, double height, LocalDate date, double beauty, double chin){
        super(name,height,date);
        this.id = id;
        this.levels = new Characteristics(beauty, chin);
    }

    public NPC (String name, double height, LocalDate date, double beauty, double chin){
        super(name,height,date);
        this.levels = new Characteristics(beauty, chin);
    }

    public NPC (int id, String name, double height, LocalDate date, double beauty, double chin, double x, double y){
        super(name,height,date,x,y);
        this.id = id;
        this.levels = new Characteristics(beauty, chin);
    }

    public NPC (String name, double height, LocalDate date, double beauty, double chin, double x, double y){
        super(name,height,date,x,y);
        this.levels = new Characteristics(beauty, chin);
    }

    public Integer getId() {
        return id;
    }

    public BeautyLevel getBeauty(){
        return levels.beauty;
    }
    public ChinSharpness getChin(){
        return levels.chin;
    }
    public String getNPCName() {
        return this.name;
    }

    @Override
    public void cuddle(String person, double xMain, double yMain) throws DistanceException{
        double distanceFromMains =
                        Math.sqrt(Math.pow(this.x-xMain,2)+Math.pow(this.y-yMain,2));

        String extent;
        if(distanceFromMains > 25) throw new DistanceException();

            if (distanceFromMains > 9) extent = "";
            else if (distanceFromMains > 4) extent = " тесно";
            else extent = " ещё теснее";


            if (!person.equals("Герцогиня") && !person.equals("Алиса") && (name.equals("Алиса") || name.equals("Герцогиня"))) {
                System.out.println(name + extent + " прижалась к какому-то незнакомцу.");
            } else {
                if (name.equals("Герцогиня")) {
                    if (person.equals("Алиса")) {
                        System.out.println(name + extent + " прижалась к Алисе.");
                    }
                } else {
                    if (person.equals("Алиса")) {
                        System.out.println("Незнакомец" + extent + " прижался к Алисе.");
                    } else {
                        if (person.equals("Герцогиня")) {
                            System.out.println("Незнакомец" + extent + " прижался к Герцогине.");
                        } else System.out.println("Незнакомец" + extent + " прижался к другому незнакомцу.");
                    }
                }
            }

    }

    @Override
    public void talkAboutMorals(boolean moral){
        if(!moral)
            System.out.println("- Что ты, что ты, деточка, - говорит " + name + ", - во всем есть мораль, только надо уметь ее найти!");
        else
            System.out.println("- Так вот же, - отвечает " + name + ", - попробуй только поискать и ты сможешь найти мораль, я знаю это!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NPC npc = (NPC) o;

        if (this.levels.beauty != npc.levels.beauty) return false;
        if (this.levels.chin != npc.levels.chin) return false;

        return x == npc.x;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), levels, x, y);
    }

//    @Override
//    public String toString(){
//        return "ID: " + id + ". Имя: " + name + ". Рост: " + height + ". Вес: " + weight;
//    }

    @Override
    public String toString(){
        return id + " " + name + " " + height + " " + weight + " " + dateOfBirth + " " + x + " " + y + " " + this.getBeauty() + " " + this.getChin() + " " + color;
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
}
