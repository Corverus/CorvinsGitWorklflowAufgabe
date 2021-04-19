package fight.player.meeple;

import fight.player.skillSpiderMan.NotSoFast;
import fight.player.skillSpiderMan.WebBandage;
import fight.player.skillSpiderMan.WebShot;

public class SpiderMan extends Player{
    public SpiderMan() {
        super("Spider-Man", 15, 15, 2, 6, new WebShot());
        getUnlockableSkills().add(new WebBandage());
        getUnlockableSkills().add(new NotSoFast());
        setID(1);
    }
}
