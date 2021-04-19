package fight;

public abstract class ChannellingSkill extends Skill {
    private int channellingTime;

    public ChannellingSkill(String name, int cooldown, boolean friendlyFire, int levelRequirement, int channellingTime) {
        super(name, cooldown, friendlyFire, levelRequirement);
        this.channellingTime = channellingTime;
    }

    @Override
    public void activate(Meeple user, Meeple target) {
        super.activate(user, target);
        applyEffect(user,target);
    }

    public boolean isChannelingTime() {
        return channellingTime > 0;
    }

    public abstract void applyEffect(Meeple user, Meeple target);
}
