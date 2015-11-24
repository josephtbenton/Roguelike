package actors;

import game.Position;
import javafx.scene.image.Image;
import util.Dice;

/**
 * Created by josephbenton on 9/13/15.
 */
public class Hero extends Actor {
    private int maxHealth, currentHealth, attack, luck, level;
    int experience, expToNextLevel;
    private Actor attacker;
    private String name;

    public Hero(Profession prof, int x, int y) {
        this.setPosition(x, y);
        this.maxHealth = prof.getHealth();
        this.currentHealth = maxHealth;
        this.attack = prof.getAttack();
        this.luck = prof.getLuck();
        this.name = prof.name().toLowerCase();
        this.sprite = prof.getAvatar();
        this.alive = true;
        this.experience = 0;
        this.level = 1;
        this.expToNextLevel = 100;
    }

    @Override
    public boolean attack(Actor actor) {
    	actor.setAttacker(this);
        Dice dice = new Dice(20);
        int roll = dice.roll() + luck;
        if (roll < 10) {
            actor.takeDamage(0);
            return false;
        } else if (roll < 20) {
            actor.takeDamage(attack);
            return true;
        } else {
            actor.takeDamage(attack * 2);
            return true;
        }
    }

    @Override
    public void takeDamage(int damage) {
        System.out.println(this.name + " took " + damage + " damage!");
        currentHealth -= damage;
        if (currentHealth <= 0) {
            this.die();
        }

    }
    public double getHealthPercent() {
        return (double)currentHealth / (double)maxHealth;
    }
    
    public void addExperience(int monsterExp){
    	experience += monsterExp;
    	System.out.println("Gained exp: " + monsterExp);
    	this.levelIf();
    }
    
    private void levelIf(){
    	if (expToNextLevel - experience <= 0){
    		level += 1;
    		experience = expToNextLevel - experience;
    		expToNextLevel += 25;
    		System.out.println("next level: " + this.expToNextLevel);
    		System.out.println(String.valueOf(experience));
    		System.out.println(String.valueOf(experience));
    		System.out.println("hero level: " + this.level);
    	}
    }
    
    public int getLevel(){
    	return level;
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void die() {
        this.sprite = new Image("assets/skull.png");
        alive = false;
    }

	@Override
	public void setAttacker(Actor actor) {
		this.attacker = actor;
		
	}


}
