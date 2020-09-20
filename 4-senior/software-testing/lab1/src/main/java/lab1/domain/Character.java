package lab1.domain;

import java.util.ArrayList;

public class Character {
    private static final int insanityThreshold = 3;

    private String name;
    private String location;
    private CharacterMood mood;
    private CharacterPosition position;
    private ArrayList<Head> heads;
    private ArrayList<Hand> hands;
    private ArrayList<Leg> legs;

    public int insanityCounter = 0;

    public Character() {
        this.name = "Артур";
        this.location = "В неизвестности";
        this.mood = CharacterMood.CALM;
        this.position = CharacterPosition.STANDING;

        this.heads = new ArrayList<>();
        this.heads.add(new Head());
        this.hands = new ArrayList<>();
        this.hands.add(new Hand(Position.LEFT));
        this.hands.add(new Hand(Position.RIGHT));
        this.legs = new ArrayList<>();
        this.legs.add(new Leg(Position.LEFT));
        this.legs.add(new Leg(Position.RIGHT));
    }

    public Character(String name, String location, CharacterMood mood, CharacterPosition position, int headCount) {
        this.name = name;
        this.location = location;
        this.mood = mood;
        this.position = position;

        this.heads = new ArrayList<>();
        for (int i = 0; i < headCount; i++) {
            this.heads.add(new Head(Position.values()[i % 3]));
        }
        this.hands = new ArrayList<>();
        this.hands.add(new Hand(Position.LEFT));
        this.hands.add(new Hand(Position.RIGHT));
        this.legs = new ArrayList<>();
        this.legs.add(new Leg(Position.LEFT));
        this.legs.add(new Leg(Position.RIGHT));
    }

    public Head getHeadByPosition(Position position) {
        if (position.equals(Position.ALL))
            throw new MultipleEntitiesFetchException();
        for (Head head : heads) {
            if (head.getPosition().equals(position))
                return head;
        }
        return null;
    }

    public Hand getHandByPosition(Position position) {
        if (position.equals(Position.ALL))
            throw new MultipleEntitiesFetchException();
        for (Hand hand : hands) {
            if (hand.getPosition().equals(position))
                return hand;
        }
        return null;
    }

    public Leg getLegByPosition(Position position) {
        if (position.equals(Position.ALL))
            throw new MultipleEntitiesFetchException();
        for (Leg leg : legs) {
            if (leg.getPosition().equals(position))
                return leg;
        }
        return null;
    }

    public boolean pickTeeth(Position handPosition) {
        return this.pickTeeth(handPosition, Position.MIDDLE);
    }

    public boolean pickTeeth(Position handPosition, Position headPosition) {
        for (Hand hand : hands) {
            if (hand.getPosition().equals(handPosition) && !hand.getStatus().equals(HandStatus.BUSY)) {
                hand.setStatus(HandStatus.BUSY);
                if (this.getHeadByPosition(headPosition) != null) {
                    this.getHeadByPosition(headPosition).setMood(HeadMood.PUZZLED);
                    this.getHeadByPosition(headPosition).getJaw().setStatus(JawStatus.TEETH_PICKED);
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

    public boolean placeLeg(Position legPosition) {
        if (legPosition.equals(Position.ALL)) {
            for (Leg leg : legs) {
                leg.setStatus(LegStatus.PLACED);
            }
            return true;
        } else {
            for (Leg leg : legs) {
                if (leg.getPosition().equals(legPosition)) {
                    if (!leg.getStatus().equals(LegStatus.PLACED))
                        leg.setStatus(LegStatus.PLACED);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean becomeOverwhelmed() {
        if (this.insanityCounter >= insanityThreshold) {
            this.setMood(CharacterMood.OVERWHELMED);
            for (Head head : heads) {
                head.getJaw().setStatus(JawStatus.HANGING);
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CharacterMood getMood() {
        return mood;
    }

    public void setMood(CharacterMood mood) {
        this.mood = mood;
    }

    public CharacterPosition getPosition() {
        return position;
    }

    public void setPosition(CharacterPosition position) {
        this.position = position;
    }

}
