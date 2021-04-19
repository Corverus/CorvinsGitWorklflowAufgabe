package fight.enemy.skillEnemy;

import fight.Meeple;
import fight.Skill;

public class Pistol extends Skill {

    public Pistol(){
        super("Pistole",1,false,1);
    }
    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user,target);
        int damage = (user.getAtk() * 3) - target.getDef();
        if(damage > 0) {
            System.out.println(user.getName() + " benutzt " + getName() + ".\n");
            System.out.println("Und fügt " + target.getName() + " " + damage + " Schaden zu.\n");
            System.out.println("Du blutest.\n");
            target.setCurrentLP(target.getCurrentLP() - damage);
            target.applyCondition(Meeple.Condition.BLEEDING,3);
        }else{
            System.out.println(getName() + " zeigt keine Wirkung.\n");
        }
    }
}
