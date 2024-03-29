package champion;

import util.Constants;

import java.util.ArrayList;

public abstract class Champion {
    private char preferredTerrain;
    private float terrainModifier;
    private int hp;
    private int hpStart;
    private int hpGrowth;
    private int xp;
    private int level;
    private boolean applyTerrainModifier;
    private ArrayList<Integer> damageOverTime = new ArrayList<Integer>();
    private int posX;
    private int posY;
    private int id;
    private int incapacitated;
    private boolean foughtThisRound;
    private float firstAbilityBase;
    private float firstAbilityGrowth;
    private float secondAbilityBase;
    private float secondAbilityGrowth;
    private int roundCounter;
    private int hpBeforeRound;

    /**
     *  Method is used to move the champion.
     * @param newMove represents the direction of the move
     */
    public void makeMove(final char newMove) {
        switch (newMove) {
            case 'U':
                --posX;
                break;
            case 'D':
                ++posX;
                break;
            case 'R':
                ++posY;
                break;
            case 'L':
                --posY;
                break;
            case '_':
                break;
            default:
                break;
        }
    }

    /**
     *  Method is used to display the final status of a champion.
     * @return champion stats if he is alive, or it will display "dead"
     */
    public String printFinalStats() {
        if (isAlive()) {
            return (getName() + " " + level + " " + xp + " " + hp + " "
                    + posX + " " + posY);
        } else {
            return (getName() + " dead");
        }
    }

    /**
     *  Method is used to determine if champion is a valid opponent.
     * @param champion opponent that "this" will fight
     * @return true if the fight can take place, false otherwise
     */
    public boolean verifyOpponent(final Champion champion) {
        return this.posX == champion.getPosX() && this.posY == champion.getPosY()
                && this.id != champion.getId() && !this.foughtThisRound
                && !champion.getFoughtThisRound();
    }

    /**
     * Method is used to award XP to the winner after a fight and level him up, if
     * necessary.
     * @param levelLoser level of the champion that died after the fight
     * @return true if the winner accumulated enough XP to level up, false if otherwise
     */
    public boolean awardXp(final int levelLoser) {
        int levelDiff = getLevel() - levelLoser;
        int xpWinner = Math.max(0, Constants.XP_INDICATOR - levelDiff * Constants.XP_MULTIPLIER);
        xp += xpWinner;

        boolean leveledUp = false;
        while (xp >= Constants.LEVEL_UP_XP + Constants.LEVEL_UP_XP_GROWTH * level) {
            ++level;
            leveledUp = true;
        }
        return leveledUp;
    }

    /**
     *  Method is used to test if the champion is alive or dead.
     * @return true if the champion is alive, false if he is dead
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     *  Method is used to determine if the champion is incapacitated.
     *  The field "incapacitated" stores the number of rounds he will be under the effect.
     * @return true if the champion is under an incapacitation effect, false otherwise
     */
    public boolean isIncapacitated() {
        if (incapacitated > 0) {
            --incapacitated;
            return true;
        }
        return false;
    }

    /**
     *  Method is used to apply the effect of the abilities that have a damage over time
     *  (DOT - how is called in MOBAs) effect.
     */
    public void applyDamageOverTime() {
        if (damageOverTime.size() > 0) {
            reduceHP(damageOverTime.get(0));
            damageOverTime.remove(0);
        }
    }

    /**
     *  Method is used to store the damage over time from the specific abilities.
     * @param damage damage to be taken over time
     * @param rounds the number of rounds the DOT effect will be active
     */
    final void addDamageOverTime(final int damage, final int rounds) {
        for (int i = 0; i < rounds; ++i) {
            damageOverTime.add(damage);
        }
    }

    /**
     *  Method is used to overwrite the current damage over time effects when the champion is hit
     *  with a new ability that has a damage over time effect.
     */
    final void resetDamageOverTime() {
        damageOverTime.clear();
    }

    /**
     *  Method is used to determine if the terrain the champion is sitting on will give him
     *  a bonus.
     * @param terrainType the type of the current location of the champion
     */
    public void hasTerrainModifier(final char terrainType) {
        applyTerrainModifier = preferredTerrain == terrainType;
    }

    /**
     *  Method is used to determine the maximum possible HP at a given point.
     * @return maximum possible HP
     */
    final int calculateTeoreticalHp() {
        return hpStart + hpGrowth * level;
    }
    /**
     *  Method is used to determine the name of a champion by its class.
     * @return  char representing champion type
     */
    private char getName() {
        return getClass().getName().charAt(Constants.NAME_INDEX);
    }

    final ArrayList<Integer> getDamageOverTime() {
        return damageOverTime;
    }

    /**
     *  Method is used to access the field that determines if the terrain modifier
     *  should be active in a particular location.
     * @return  true if the terrain modifier should be applied, false if otherwise
     */
    boolean getApplyTerrainModifier() {
        return applyTerrainModifier;
    }

    private boolean getFoughtThisRound() {
        return foughtThisRound;
    }

    /**
     *  Method is used to set the status of the champion involvement in a round.
     * @param fightStatus true if the champion fought, false if otherwise
     */
    public void setFoughtThisRound(final boolean fightStatus) {
        foughtThisRound = fightStatus;
    }

    /**
     *  Method is a setter for incapacitated field that stores the number of rounds
     *  the champion is under the incapacitation effect.
     * @param incapacitated number of incapacitation rounds
     */
    void setIncapacitated(final int incapacitated) {
        this.incapacitated = incapacitated;
    }

    final int getRoundCounter() {
        return roundCounter;
    }

    final void setRoundCounter() {
        roundCounter = 0;
    }

    public final void increaseRoundCounter() {
        ++roundCounter;
    }

    final void setXp() {
        xp = 0;
    }

    public final int getHp() {
        return hp;
    }

    final void setHp(final int hp) {
        this.hp = hp;
    }

    final void setHpGrowth(final int newHpGrowth) {
        this.hpGrowth = newHpGrowth;
    }

    final void setHpStart(final int newHpStart) {
        this.hpStart = newHpStart;
    }

    final int getHpBeforeRound() {
        return hpBeforeRound;
    }

    public final void setHpBeforeRound(final int newHp) {
        hpBeforeRound = newHp;
    }

    public final int getLevel() {
        return level;
    }

    final void setLevel() {
        this.level = 0;
    }

    public final int getPosX() {
        return posX;
    }

    final void setPosX(final int posX) {
        this.posX = posX;
    }

    public final int getPosY() {
        return posY;
    }

    final void setPosY(final int posY) {
        this.posY = posY;
    }

    private int getId() {
        return id;
    }

    public final void setId(final int newId) {
        id = newId;
    }

    final void setPreferredTerrain(final char preferredTerrain) {
        this.preferredTerrain = preferredTerrain;
    }

    final float getTerrainModifier() {
        return terrainModifier;
    }

    final void setTerrainModifier(final float terrainModifier) {
        this.terrainModifier = terrainModifier;
    }

    final void setFirstAbilityBase(final float firstAbilityBase) {
        this.firstAbilityBase = firstAbilityBase;
    }

    final void setFirstAbilityGrowth(final float firstAbilityGrowth) {
        this.firstAbilityGrowth = firstAbilityGrowth;
    }

    final void setSecondAbilityBase(final float secondAbilityBase) {
        this.secondAbilityBase = secondAbilityBase;
    }

    final void setSecondAbilityGrowth(final float secondAbilityGrowth) {
        this.secondAbilityGrowth = secondAbilityGrowth;
    }

    final float firstAbility() {
        return firstAbilityBase + firstAbilityGrowth * level;
    }

    final float secondAbility() {
        return secondAbilityBase + secondAbilityGrowth * level;
    }

    final void reduceHP(final int damage) {
        hp -= damage;
    }

    /**
     *  Method is used to restore HP to 100% after level up.
     *  Method is abstract because every champion has different initial HP values
     *  and different HP per level growth.
     */
    public void restoreHP() {
        hp = hpStart + hpGrowth * level;
    }

    public abstract void isAttackedBy(Champion champion);

    public abstract void attack(Knight knight);

    public abstract void attack(Pyromancer pyromancer);

    public abstract void attack(Rogue rogue);

    public abstract void attack(Wizard wizard);
}
