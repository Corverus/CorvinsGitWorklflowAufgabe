package fight;

public abstract class AttackSkill extends Skill {
    public AttackSkill(String name, int cooldown, boolean friendlyFire, int levelRequirement) {
        super(name, cooldown, friendlyFire, levelRequirement);
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
        applyEffect(user, target);
    }

    public abstract void applyEffect(Meeple user, Meeple target);
}

