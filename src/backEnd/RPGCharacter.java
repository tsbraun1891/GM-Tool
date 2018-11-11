package backEnd;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class RPGCharacter {
    /* Basic character stats */
    private String playerName;
    private String name;
    private String characterClass;
    private String race;
    private int level;
    private String alignment;
    private int xp;
    private boolean inspiration;
    private int ac;
    private int speed;
    private int maxHP;
    private int currentHP;
    private int hitDiceSides;
    private int hitDiceAmount;
    private int currentHitDiceAmount;

    public boolean[][] skills = {new boolean[2], new boolean[4], new boolean[1], new boolean[6], new boolean[6], new boolean[5]};

    /* Skill stats */
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    /* Items list */
    public LinkedList<Item> items = new LinkedList<>() {
        public boolean add(Item i) {
            super.add(i);
            Collections.sort(items);
            return true;
        }
    };

    public RPGCharacter(String filePath) {
        ArrayList<String> rawStats = new ArrayList<String>();

        /* Read from file into array of stats */
        try {
            File statsFile = new File(filePath + System.getProperty("file.separator") + "Characters/stats.txt");
            BufferedReader br = new BufferedReader(new FileReader(statsFile));
            String stat;
            while ((stat = br.readLine()) != null)
                rawStats.add(stat);

            File inventoryFile = new File(filePath + System.getProperty("file.separator") + "Characters/inventory.txt");
            br = new BufferedReader(new FileReader(inventoryFile));
            String item;
            while ((item = br.readLine()) != null)
                addItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int counter = 0; //to avoid hardcoding the indexes for reading from file
        this.playerName = rawStats.get(counter); counter ++;
        this.name = rawStats.get(counter); counter ++;
        this.characterClass = rawStats.get(counter); counter ++;
        this.race = rawStats.get(counter); counter ++;
        this.level = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.alignment = rawStats.get(counter); counter ++;
        this.xp = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.inspiration = Boolean.parseBoolean(rawStats.get(counter)); counter ++;
        this.ac = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.speed = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.maxHP = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.currentHP = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.hitDiceSides = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.hitDiceAmount = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.currentHitDiceAmount = Integer.parseInt(rawStats.get(counter)); counter ++;

        this.strength = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.dexterity = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.constitution = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.intelligence = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.wisdom = Integer.parseInt(rawStats.get(counter)); counter ++;
        this.charisma = Integer.parseInt(rawStats.get(counter)); counter ++;


        for (int i = 0; i < this.skills.length; i++) {
            for (int j = 0; j < this.skills[i].length; j++) {
                skills[i][j] = Boolean.parseBoolean(rawStats.get(counter));
                counter ++;
            }
        }

        /* (hopefully) Garbage collect that shit */
        rawStats = null;

        calculateRealStats();
        // TODO: calculate relevant stats
    }

    /**
     * Get's the REAL stats
     */
    private void calculateRealStats() {
        this.strength = (this.strength - 10)/2;
        this.dexterity = (this.dexterity - 10)/2;
        this.constitution = (this.constitution - 10)/2;
        this.intelligence = (this.intelligence - 10)/2;
        this.wisdom = (this.wisdom - 10)/2;
        this.charisma = (this.charisma - 10)/2;
    }

    private void addItem(String infoLine) {
        String[] stringData = infoLine.split(" ");
        switch (stringData[0]) {
            case "WEAPON":
                items.add(new Weapon(stringData[1], Integer.parseInt(stringData[2]), stringData[3], stringData[4]));
                break;
            case "ARMOR":
                items.add(new Armor(stringData[1], Integer.parseInt(stringData[2]), stringData[3]));
                break;
            case "CURRENCY":
                items.add(new Currency(stringData[1], Integer.parseInt(stringData[2])));
                break;
            case "MISC":
                items.add(new Item(stringData[1]));
        }
    }

    public static void createNewCharFiles(ArrayList<String> data) {
        String charName = data.get(1);
        File dir = new File("Characters/" + charName);
        dir.mkdir();
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Characters/" + charName + "/stats.txt"), "utf-8"));
            for(int i = 0; i < data.size();i++){
                writer.write(data.get(i));
                writer.write("\n");
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong when writing to character file.");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {/*ignore*/}
        }
    }

    // TODO: Use ENUMS and arrays cause its more sugar33
    /* Getters */
    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public boolean isInspiration() {
        return inspiration;
    }

    public void setInspiration(boolean inspiration) {
        this.inspiration = inspiration;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getHitDiceSides() {
        return hitDiceSides;
    }

    public void setHitDiceSides(int hitDiceSides) {
        this.hitDiceSides = hitDiceSides;
    }

    public int getHitDiceAmount() {
        return hitDiceAmount;
    }

    public void setHitDiceAmount(int hitDiceAmount) {
        this.hitDiceAmount = hitDiceAmount;
    }

    public int getCurrentHitDiceAmount() {
        return currentHitDiceAmount;
    }

    public void setCurrentHitDiceAmount(int currentHitDiceAmount) {
        this.currentHitDiceAmount = currentHitDiceAmount;
    }
}
