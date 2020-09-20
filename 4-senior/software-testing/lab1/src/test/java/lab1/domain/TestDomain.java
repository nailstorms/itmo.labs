package lab1.domain;


import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDomain {
    @Test
    public void testCharacterGetHead() {
        Character arthur = new Character();
        Assert.assertNotNull(arthur.getHeadByPosition(Position.MIDDLE), "Нужная голова не была найдена.");
    }

    @Test
    public void testCharacterGetNonexistentHead() {
        Character arthur = new Character();
        Assert.assertNull(arthur.getHeadByPosition(Position.RIGHT), "Ненужная голова была найдена.");
        Assert.assertNull(arthur.getHeadByPosition(Position.LEFT), "Ненужная голова была найдена.");
    }

    @Test(expectedExceptions = MultipleEntitiesFetchException.class)
    public void testCharacterGetAllHeadsExc() {
        Character arthur = new Character();
        arthur.getHeadByPosition(Position.ALL);
    }

    @Test
    public void testCharacterGetHand() {
        Character arthur = new Character();
        Assert.assertNotNull(arthur.getHandByPosition(Position.RIGHT), "Нужная рука не была найдена.");
        Assert.assertNotNull(arthur.getHandByPosition(Position.LEFT), "Нужная рука не была найдена.");
    }

    @Test
    public void testCharacterGetNonexistentHand() {
        Character arthur = new Character();
        Assert.assertNull(arthur.getHandByPosition(Position.MIDDLE), "Ненужная рука была найдена.");
    }

    @Test(expectedExceptions = MultipleEntitiesFetchException.class)
    public void testCharacterGetAllHandsExc() {
        Character arthur = new Character();
        arthur.getHandByPosition(Position.ALL);
    }

    @Test
    public void testCharacterGetLeg() {
        Character arthur = new Character();
        Assert.assertNotNull(arthur.getLegByPosition(Position.RIGHT), "Нужная нога не была найдена.");
        Assert.assertNotNull(arthur.getLegByPosition(Position.LEFT), "Нужная нога не была найдена.");
    }

    @Test
    public void testCharacterGetNonexistentLeg() {
        Character arthur = new Character();
        Assert.assertNull(arthur.getLegByPosition(Position.MIDDLE), "Ненужная рука была найдена.");
    }

    @Test(expectedExceptions = MultipleEntitiesFetchException.class)
    public void testCharacterGetAllLegsExc() {
        Character arthur = new Character();
        arthur.getLegByPosition(Position.ALL);
    }

    @Test
    public void testOneHeadCharacterPickTeeth() {
        Character arthur = new Character();
        Assert.assertTrue(arthur.pickTeeth(Position.LEFT));
        Assert.assertEquals(arthur.getHeadByPosition(Position.MIDDLE).getMood(), HeadMood.PUZZLED);
        Assert.assertEquals(arthur.getHeadByPosition(Position.MIDDLE).getJaw().getStatus(), JawStatus.TEETH_PICKED);
        Assert.assertEquals(arthur.getHandByPosition(Position.LEFT).getStatus(), HandStatus.BUSY);
        Assert.assertEquals(arthur.getHandByPosition(Position.RIGHT).getStatus(), HandStatus.FREE);
    }

    @Test
    public void testTwoHeadCharacterPickTeeth() {
        Character ogre = new Character("Огр", "В комнате", CharacterMood.CALM, CharacterPosition.SITTING, 2);
        Assert.assertTrue(ogre.pickTeeth(Position.LEFT, Position.RIGHT));
        Assert.assertEquals(ogre.getHeadByPosition(Position.RIGHT).getMood(), HeadMood.PUZZLED);
        Assert.assertEquals(ogre.getHeadByPosition(Position.RIGHT).getJaw().getStatus(), JawStatus.TEETH_PICKED);
        Assert.assertEquals(ogre.getHeadByPosition(Position.LEFT).getMood(), HeadMood.NEUTRAL);
        Assert.assertEquals(ogre.getHeadByPosition(Position.LEFT).getJaw().getStatus(), JawStatus.IN_PLACE);
        Assert.assertEquals(ogre.getHandByPosition(Position.LEFT).getStatus(), HandStatus.BUSY);
        Assert.assertEquals(ogre.getHandByPosition(Position.RIGHT).getStatus(), HandStatus.FREE);
    }

    @Test
    public void testCharacterPickTeethWithNonexistentHand() {
        Character arthur = new Character();
        Assert.assertFalse(arthur.pickTeeth(Position.MIDDLE), "Артур поковырялся в зубах несуществующей рукой.");
    }

    @Test
    public void testCharacterPickNonexistentTeeth() {
        Character arthur = new Character();
        Assert.assertFalse(arthur.pickTeeth(Position.LEFT, Position.RIGHT), "Артур поковырялся в несуществующих зубах.");
    }

    @Test
    public void testCharacterPlaceOneLeg() {
        Character arthur = new Character();
        Assert.assertTrue(arthur.placeLeg(Position.LEFT));
        Assert.assertEquals(arthur.getLegByPosition(Position.LEFT).getStatus(), LegStatus.PLACED, "Нужная нога не была положена.");
        Assert.assertEquals(arthur.getLegByPosition(Position.RIGHT).getStatus(), LegStatus.FREE, "Ненужная нога была положена.");
    }

    @Test
    public void testCharacterPlaceBothLegs() {
        Character arthur = new Character();
        Assert.assertTrue(arthur.placeLeg(Position.ALL));
        Assert.assertEquals(arthur.getLegByPosition(Position.LEFT).getStatus(), LegStatus.PLACED, "Левая нога не была положена.");
        Assert.assertEquals(arthur.getLegByPosition(Position.RIGHT).getStatus(), LegStatus.PLACED, "Правая нога не была положена.");
    }

    @Test
    public void testCharacterPlaceNonexistentLeg() {
        Character arthur = new Character();
        Assert.assertFalse(arthur.placeLeg(Position.MIDDLE), "Несуществующая нога была положена.");
    }

    @Test
    public void testCharacterBecomeOverwhelmed() {
        Character arthur = new Character();
        arthur.insanityCounter = 1000;
        Assert.assertTrue(arthur.becomeOverwhelmed());
        Assert.assertEquals(arthur.getMood(), CharacterMood.OVERWHELMED, "Артуру удалось сохранить спокойствие.");
        Assert.assertEquals(arthur.getHeadByPosition(Position.MIDDLE).getJaw().getStatus(),
                JawStatus.HANGING,
                "Челюсть Артура не отвисла.");
    }

    @Test
    public void testCharacterCanNotBecomeOverwhelmed() {
        Character arthur = new Character();
        Assert.assertFalse(arthur.becomeOverwhelmed(), "Артур был ошеломлен на ровном месте.");
    }

    @Test
    public void testCharacterGetName() {
        Character arthur = new Character();
        Assert.assertEquals(arthur.getName(), "Артур", "Возвращено некорректное имя.");
    }

    @Test
    public void testCharacterGetLocation() {
        Character arthur = new Character();
        Assert.assertEquals(arthur.getLocation(), "В неизвестности", "Возвращена некорректная локация.");
    }

    @Test
    public void testCharacterSetLocation() {
        Character arthur = new Character();
        arthur.setLocation("test");
        Assert.assertEquals(arthur.getLocation(), "test", "Возвращена некорректная локация.");
    }

    @Test
    public void testCharacterGetMood() {
        Character arthur = new Character();
        Assert.assertEquals(arthur.getMood(), CharacterMood.CALM, "Возвращено некорректное настроение.");
    }

    @Test
    public void testCharacterSetMood() {
        Character arthur = new Character();
        arthur.setMood(CharacterMood.NERVOUS);
        Assert.assertEquals(arthur.getMood(), CharacterMood.NERVOUS, "Возвращено некорректное настроение.");
    }

    @Test
    public void testCharacterGetPosition() {
        Character arthur = new Character();
        Assert.assertEquals(arthur.getPosition(), CharacterPosition.STANDING, "Возвращено некорректное положение.");
    }

    @Test
    public void testCharacterSetPosition() {
        Character arthur = new Character();
        arthur.setPosition(CharacterPosition.SITTING);
        Assert.assertEquals(arthur.getPosition(), CharacterPosition.SITTING, "Возвращено некорректное положение.");
    }

}
