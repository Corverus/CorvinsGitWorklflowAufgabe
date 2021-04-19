package fight;

public abstract class BuffSkill extends Skill {
    private int buffTime;

    public BuffSkill(String name, int cooldown, boolean friendlyFire, int levelRequirement, int buffTime) {
        super(name, cooldown, friendlyFire, levelRequirement);
        this.buffTime = buffTime;
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
        applyEffect(user, target);
    }

    public abstract void applyEffect(Meeple user, Meeple target);

    public boolean isBuffTime() {
        return buffTime > 0;
    }
}
