import fight.Fight;
import fight.enemy.meeple.Enemy;
import fight.enemy.skillEnemy.Crowbar;
import fight.player.meeple.Player;
import fight.player.skillSpiderMan.WebShot;
import org.junit.Test;
import world.Account;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class FightingTests {
    private Fight myFight;
    private Player myPlayer;
    private Enemy myEnemy;
    private List<Enemy> enemyTeam;
    private Account myAcc;
    private List<Player> team;

    public FightingTests(){
        myPlayer = new Player("Dude",10,100,11,1,new WebShot());
        myAcc = new Account(myPlayer,"Dudus");
        myEnemy = new Enemy("???",10,5,1,6,200,new Crowbar());
        enemyTeam = new ArrayList();
        enemyTeam.add(myEnemy);
        team = myAcc.getParty();
    }
    @Test
    public void fightQuestionmark(){
        
    }
}
