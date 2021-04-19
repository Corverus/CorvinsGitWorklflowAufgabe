package fight.player.skillSpiderMan;

import fight.AttackSkill;
import fight.Meeple;

public class NotSoFast extends AttackSkill {

    public NotSoFast() {
        super("Nicht so schnell!", 2, false, 3);
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
    }

    @Override
    public void applyEffect(Meeple user, Meeple target) { //An Zukunfts-Corvin: Fähigkeit beendet "Fliegen"
        if(target.getMyConditions().contains(Meeple.Condition.COCOONED)) {
            target.increaseDurationOfCondition(Meeple.Condition.COCOONED,2);
            int damage = user.getAtk() * 2;
            target.setCurrentLP(target.getCurrentLP() - damage);
            System.out.println("Du hast " + target.getName() + " für 2 weitere Runden eingesponen und ihm " + damage + " Schaden zugefügt.\n");
        }
        else{
            target.applyCondition(Meeple.Condition.COCOONED,1);
            int damage = user.getAtk();
            target.setCurrentLP(target.getCurrentLP() - damage);
            System.out.println("Du hast " + target.getName() + " für 1 Runden eingesponnen und ihm " + damage + " Schaden zugefügt.\n");
        }
    }
}
