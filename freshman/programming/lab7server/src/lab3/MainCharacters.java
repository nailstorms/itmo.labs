package lab3;

public class MainCharacters extends AbstractCharacters implements State {
    private boolean MoralSearchAbility;

    static private class MoodChangeHistory {
        int mood;
        Enum reason;
        MoodChangeHistory(int mood){
            this.mood = mood;
        }
    }
    public int getMood(){
        return RudenessLevel.mood;
    }
    public Enum getReason(){
        return RudenessLevel.reason;
    }
    MoodChangeHistory RudenessLevel = new MoodChangeHistory(1);

    public boolean getMoralSearchAbility(){
        return MoralSearchAbility;
    }

    public MainCharacters (String name, int height, boolean moral){
        super(name, height);
        this.MoralSearchAbility = moral;
    }

    public DialogueContinuation c = new DialogueContinuation (){
        @Override
        public void beginDialogue(){
            System.out.print("- ");
        }
    };


    public class BeginSpeech implements AliceMoralsBeginning {
        public void saySmthAboutMorals() {
            if(!MoralSearchAbility)
                System.out.println("А может быть, никакая, - произносит " + name + ", набравшись отваги. ");
            else
                System.out.println("Хм, действительно, может быть она и есть...- говорит " + name + ", призадумавшись. ");
        }
    };

    @Override
    public void like(NPC character){
        class PronounSwitcher {
            private String grammaticalGender;

            private PronounSwitcher() {
                grammaticalGender = character.name.equals("Герцогиня") ? "female" : "male";
            }

            String getNominativePronoun() {
                if (grammaticalGender.equals("female")) return "она";
                else return "он";
            }

            String getGenitivePronoun() {
                if (grammaticalGender.equals("female")) return "ее";
                else return "его";
            }
        }
        PronounSwitcher p = new PronounSwitcher();

        if(name.equals("Алиса")){ //Алиса очень добрая
            RudenessLevel.mood = 0;
        }
        if(character.getBeauty() == BeautyLevel.Hideous)
        {
            RudenessLevel.reason = BeautyLevel.Hideous;
            System.out.print(name + " против этого -");
            RudenessLevel.mood++;
            if(character.name.equals("Герцогиня"))
                System.out.print(" во-первых, потому, что " + character.name + " чересчур некрасивая,");
            else
                System.out.print(" во-первых, потому, что Незнакомец был чересчур некрасив,");
            if(height - character.height < 16 && height - character.height > 9)
            {
               System.out.print(" а во-вторых, " + p.getNominativePronoun() + " как раз такого роста, что подбородок " + p.getGenitivePronoun() + " пришелся на плечо," );
                if(character.getChin() == ChinSharpness.Sharp){
                    RudenessLevel.reason = ChinSharpness.Sharp;
                    RudenessLevel.mood++;
                    if(character.name.equals("Герцогиня"))
                        System.out.println(" а подбородочек у нее был нестерпимо острый.");
                    else
                        System.out.println(" а подбородок у него был нестерпимо острый.");
                }
                else {if(character.getChin() == ChinSharpness.Flat){
                    RudenessLevel.reason = ChinSharpness.Flat;
                    RudenessLevel.mood++;
                    System.out.println(" а подбородок был настолько плоским, что возникало неприятное ощущение, как если бы на плечо положили камень.");
                }
                else System.out.println(" но хотя бы с подбородком все нормально.");}
            }
            else
                System.out.println(" но с ростом было все в порядке, и " + name + " не против.");
        }
        else
        {System.out.print("Впрочем, " + name + " не особо против");
        if(character.getBeauty() == BeautyLevel.Beautiful)
            System.out.println(", ведь "+ character.name + " может похвастать красотой.");
        else System.out.println(".");}
    }

    @Override
    public void beRude(int stateOfMind){
        if(stateOfMind>0 && stateOfMind<3)
            System.out.println("Но " + name + " не желает грубить, поэтому выбор был - молчание.");
        if (stateOfMind>=3)
            System.out.println("Раздалось громкое: 'Отстань!'");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MainCharacters that = (MainCharacters) o;

        if (MoralSearchAbility != that.MoralSearchAbility) return false;
        return RudenessLevel.equals(that.RudenessLevel);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (MoralSearchAbility ? 1 : 0);
        result = 31 * result + RudenessLevel.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return "Имя: " + name + ". Рост: " + height + ". Уровень грубости: " + getMood();
    }
}
