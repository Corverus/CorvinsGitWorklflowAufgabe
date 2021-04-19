package fight;


public abstract class Skill {
    private String name;
    private int cooldown;
    private boolean friendlyFire;
    private int currentCooldown;
    private int levelRequirement;

    public Skill(String name, int cooldown, boolean friendlyFire, int levelRequirement) {
        this.name = name;
        this.cooldown = cooldown;
        this.friendlyFire = friendlyFire;
        this.currentCooldown = getCooldown();
        this.levelRequirement = levelRequirement;
    }

    public void activate(Meeple user, Meeple target) {
        setCurrentCooldown(0);
    }

    public boolean isReady() {
        return currentCooldown >= cooldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }
}
