package fight.player.skillSpiderMan;

import fight.AttackSkill;
import fight.Meeple;

public class WebShot extends AttackSkill {

    public WebShot() {
        super("Netzschuss", 3, false, 1);
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
    }

    @Override
    public void applyEffect(Meeple user, Meeple target) {
        if (target.getCurrentLP() <= (target.getMaxLP() / 5)) {
            target.setCurrentLP(0);
            System.out.println("Du hast" + target.getName() + "an die Wand gesponnen und damit Kampfunfähig gemacht.\n");
        } else {
            target.applyCondition(Meeple.Condition.COCOONED, 2);
            System.out.println("Du hast " + target.getName() + " für 2 Runden eingesponnen.\n");
        }
    }
}
