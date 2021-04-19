package world;


import fight.player.meeple.Player;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import javax.json.*;


public class Account {
    private static final String CORVINSADVENTURE_SAVEFILE_LOCATION = "C:\\Users\\Corvin Frei\\Documents\\CorvinsAdventure\\CA Maven\\SaveFiles\\SaveFile.txt";
    private String name;
    private List<Player> party = new ArrayList();
    private List<Level> unlockedLevel = new ArrayList();
    private List<Player> unlockedCharacters = new ArrayList();
    private static Gson gson = new Gson();

    public Account(Player myPlayer, String name) {
        this.name = name;
        getUnlockedCharacters().add(myPlayer);
        party.add(myPlayer);
    }

    public void saveData() {
        String myData = "";
        for (Player partyMember : getParty()) {
            myData += partyMember.toString();
        }
        for (Level unlockedLevel : getUnlockedLevel()) {
            myData += unlockedLevel.toString();
        }
        File saveFile = new File(CORVINSADVENTURE_SAVEFILE_LOCATION);
        if (!saveFile.exists()) {
            try {
                File directory = new File(saveFile.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                saveFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Excepton Occured: " + e.toString());
            }
        }
    }

    public void loadData() {
        File saveFile = new File(CORVINSADVENTURE_SAVEFILE_LOCATION);
        if (!saveFile.exists())
            System.out.println("File doesn't exist");

        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(saveFile), "UTF-8");

            JsonReader myReader = new JsonReader(isReader);


        } catch (Exception e) {
            System.out.println("error load cache from file " + e.toString());
        }

        System.out.println("\n Data loaded successfully from file " + saveFile);

    }

    public List<Player> getParty() {
        return party;
    }

    public List<Level> getUnlockedLevel() {
        return unlockedLevel;
    }

    public List<Player> getUnlockedCharacters() {
        return unlockedCharacters;
    }

    public void setParty(List<Player> party) {
        this.party = party;
    }

    public String getSaveLocation() {
        return CORVINSADVENTURE_SAVEFILE_LOCATION;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
