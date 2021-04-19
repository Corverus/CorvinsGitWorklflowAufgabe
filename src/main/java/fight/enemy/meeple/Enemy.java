package fight.enemy.meeple;

import fight.Meeple;
import fight.Skill;

public class Enemy extends Meeple {
    private int xpReward;

    public Enemy(){

    }
    public Enemy(String name, int maxLP, int atk, int def, int speed, int xpReward, Skill mySkill) {
        super(name,maxLP,atk,def,speed,mySkill);
        setXpReward(xpReward);
    }

    public void setValues(Enemy myEnemy) {
        setName(myEnemy.getName());
        setAlive(myEnemy.isAlive());
        setAtk(myEnemy.getAtk());
        setDef(myEnemy.getDef());
        setSpeed(myEnemy.getSpeed());
        setXpReward(myEnemy.getXpReward());
        setLP(myEnemy.getMaxLP());
        if (myEnemy.getMySkills() != null) {
            setMySkills(myEnemy.getMySkills());
        }
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }
}
