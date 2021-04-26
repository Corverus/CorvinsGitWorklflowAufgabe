import fight.player.meeple.Player;
import level.Level1;
import org.junit.jupiter.api.Test;
import world.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalkingTests {
    private Player myPlayer;
    private Account myAccount;
    private Level1 myLevel;

    public WalkingTests(){
        myPlayer = new Player(1);
        myAccount = new Account(myPlayer, "SomeDude");
        myLevel = new Level1(myAccount);
    }
    @Test
    void goHome(){
        myLevel.move("s");
        myLevel.move("o");
        myLevel.move("s");
        assertEquals("Joe's Arena",myLevel.getCurrentRoom().getDescription());
        myLevel.examine();
        myLevel.move("n");
        myLevel.move("w");
        myLevel.move("w");
        myLevel.move("s");
        myLevel.move("o");
        myLevel.move("s");
        assertEquals("Zuhause",myLevel.getCurrentRoom().getDescription());
    }
}
