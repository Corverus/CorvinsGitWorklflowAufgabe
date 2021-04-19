package fight.player.meeple;


import fight.Meeple;
import fight.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends Meeple {
    private static final int NEW_PLAYER_XP = 0;
    private static final int NEW_PLAYER_LEVEL = 1;
    private static final int NEW_PLAYER_XPCAP = 12;
    private int xp;
    private int xpCap;
    private List<Skill> unlockableSkills = new ArrayList();
    private int ID;

    public Player(String name, int maxLP, int atk, int def, int speed, Skill mySkill) {
        super(name, maxLP, atk, def, speed, mySkill);
        setLevel(NEW_PLAYER_LEVEL);
        setXp(NEW_PLAYER_XP);
        setXpCap(NEW_PLAYER_XPCAP);
        setAlive(true);
    }
    public Player(int ID){
        generatePlayer(ID);
    }

    public Player generatePlayer(int ID) {
        switch (ID) {
            case 1:
                return new SpiderMan();
            default:
                return null;
        }
    }

    public void levelUp(int times) {
        while (times > 0) {
            levelUp();
            times--;
        }
    }

    public void levelUp() {
        while (xp >= xpCap) {
            System.out.println(getName() + " ist ein Level aufgestiegen.\n");
            setLevel(getLevel() + 1);
            setXp(getXp() - getXpCap());
            setXpCap(getXpCap() + 5);
            setLP(getMaxLP() + levelUpIncrease(1, 4));
            setAtk(getAtk() + levelUpIncrease(0, 2));
            setDef(getDef() + levelUpIncrease(0, 1));
            setSpeed(getSpeed() + levelUpIncrease(1, 3));
            checkAddSkill();
        }
    }

    public int levelUpIncrease(int min, int max) {
        Random number = new Random();
        int increase = number.nextInt((max - min) + 1) + min;
        return increase;
    }

    public void checkAddSkill() {
        for (Skill mySkill : unlockableSkills) {
            if (mySkill.getLevelRequirement() == getLevel()) {
                getMySkills().add(mySkill);
                System.out.println("Du hast eine neue Fähigkeit freigeschaltet.\n");
            }
        }
    }

    public List<Skill> getUnlockableSkills() {
        return unlockableSkills;
    }

    public void setUnlockableSkills(List<Skill> unlockableSkills) {
        this.unlockableSkills = unlockableSkills;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXpCap() {
        return xpCap;
    }

    public void setXpCap(int xpCap) {
        this.xpCap = xpCap;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
