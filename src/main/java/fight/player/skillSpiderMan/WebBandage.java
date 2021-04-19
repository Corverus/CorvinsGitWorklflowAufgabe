package fight.player.skillSpiderMan;

import fight.AttackSkill;
import fight.Meeple;

public class WebBandage extends AttackSkill {

    public WebBandage() {
        super("Netzverband", 3, true, 2);
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
    }

    @Override
    public void applyEffect(Meeple user, Meeple target) {
        int healing = user.getAtk() / 2;
        target.setCurrentLP(target.getCurrentLP() + healing);
        target.checkLP();
        String message = "Du hast " + target.getName() + " um " + healing + "LP geheilt";
        if (target.getMyConditions().contains(Meeple.Condition.BLEEDING)) {
            target.getMyConditions().remove(Meeple.Condition.BLEEDING);
            message += " und den Blutungseffekt entfernt.";
        }
        System.out.println(message);
    }
}
