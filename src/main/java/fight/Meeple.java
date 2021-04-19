package fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Meeple {
    public enum Condition {
        BLEEDING, STUNNED, COCOONED;


        private int duration;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    private List<Condition> myConditions = new ArrayList();
    private boolean alive;
    private String name;
    private int maxLP;
    private int atk;
    private int def;
    private int currentLP;
    private int speed;
    private int level;
    private List<Skill> mySkills = new ArrayList();
    private int dodgeChance;

    public Meeple(){

    }


    public Meeple(String name, int maxLP, int atk, int def, int speed, Skill mySkill) {
        this.name = name;
        this.maxLP = maxLP;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.currentLP = getMaxLP();
        this.alive = true;
        this.dodgeChance = 0;
        if (mySkill != null) {
            getMySkills().add(mySkill);
        }
    }

    public void checkLP() {
        if (getCurrentLP() > getMaxLP()) {
            setCurrentLP(getMaxLP());
        }
    }

    public void setLP(int maxLP) {
        setMaxLP(maxLP);
        setCurrentLP(maxLP);
    }

    public boolean checkDodge() {
        Random number = new Random();
        int min = 1;
        int max = 10;
        int chance = number.nextInt((max - min) + 1) + min;
        return dodgeChance >= chance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLP() {
        return maxLP;
    }

    public void setMaxLP(int maxLP) {
        this.maxLP = maxLP;
    }

    public int getCurrentLP() {
        return currentLP;
    }

    public void setCurrentLP(int currentLP) {
        this.currentLP = currentLP;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Skill> getMySkills() {
        return mySkills;
    }

    public void setMySkills(List<Skill> mySkills) {
        this.mySkills = mySkills;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean checkAlive(boolean message) {
        if (currentLP <= 0 || !isAlive()) {
            setCurrentLP(0);
            setAlive(false);
            if (message) {
                System.out.println(getName() + " wurde besiegt.\n");
            }
            return false;
        }
        return true;
    }

    public void applyCondition(Condition conditionName, int duration) {
        conditionName.setDuration(duration);
        myConditions.add(conditionName);
    }

    public void increaseDurationOfCondition(Condition conditionName, int increase){
        if(myConditions.contains(conditionName)){
            myConditions.get(myConditions.indexOf(conditionName)).setDuration(myConditions.get(myConditions.indexOf(conditionName)).getDuration() + increase);
        }
    }

    public List<Condition> getMyConditions() {
        return myConditions;
    }

    public boolean isPlayer(){
        return false;
    }
}
