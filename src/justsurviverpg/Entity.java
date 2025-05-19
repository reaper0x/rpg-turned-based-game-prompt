package justsurviverpg;


public class Entity {
    protected float hp;
    protected float atk;
    protected int x;
    protected int y;
    protected char ch;
    protected float energy;
    protected float starterEnergy;
    protected float moveEnergyCost;
    protected float attackEnergyCost;
    
    public Entity() {
        this.hp=0;
        this.atk=0;
        this.x=0;
        this.y=0;
        this.ch=' ';
        this.energy=0;
        this.starterEnergy=0;
    }
    
//  PUT HERE FULL CONSTRUCTOR ONLY IF NECESSARY
    
    
//  Set methods:
    public void setHp(float hp) {
        this.hp=hp;
    }
    
    public void setAtk(float atk) {
        this.atk=atk;
    }
    
    public void setX(int x) {
        this.x=x;
    }
    
    public void setY(int y) {
        this.y=y;
    }

    public void setCh(char ch) {
        this.ch=ch;
    }
    
    public void setEnergy(float energy) {
        this.energy=energy;
    }
    
    public void setStarterEnergy(float starterEnergy) {
        this.starterEnergy=starterEnergy;
    }
    
    public void setMoveEnergyCost(float moveEnergyCost) {
        this.moveEnergyCost=moveEnergyCost;
    }
    
    public void setAttackEnergyCost(float attackEnergyCost) {
        this.attackEnergyCost=attackEnergyCost;
    }

//  Get methods:    
    public float getHp() {
        return this.hp;
    }
    
    public float getAtk() {
        return this.atk;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public char getCh() {
        return this.ch;
    }
    
    public float getEnergy() {
        return this.energy;
    }
    
    public float getStarterEnergy() {
        return this.starterEnergy;
    }
    
    public float getMoveEnergyCost() {
        return this.moveEnergyCost;
    }
    
    public float getAttackEnergyCost() {
        return this.attackEnergyCost;
    }
    
//  General Methods:
    public void hit(float atk) {
        this.hp -= atk;
    }
}