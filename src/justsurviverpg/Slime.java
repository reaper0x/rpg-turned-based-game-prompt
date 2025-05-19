package justsurviverpg;

import java.util.Random;

public class Slime extends Entity {
    Random random=new Random();
    private static int ID=0;
    private int slimeID;
    private boolean canAttack;
    private boolean canMove;
    private int dx;
    private int dy;
    private int distX;
    private int distY;
    private boolean moveX;
    private boolean alreadyOnDestination;
    
    public Slime(char map[][], int maxX, int maxY) {
        ID++;
        this.slimeID=ID;
        this.starterEnergy=2;
        this.energy=this.starterEnergy;
        this.canAttack=true;
        this.attackEnergyCost=(float) 2;
        this.canMove=true;
        this.moveEnergyCost=(float) 1.2;
        this.atk=1;
        this.hp=3;
        this.alreadyOnDestination=false;
        setCh('S');
        spawn(map, maxX, maxY);
    }
    
    public void spawn(char map[][], int maxX, int maxY) {
        do{
            this.x = random.nextInt(maxX);
            this.y = random.nextInt(maxY);
        } while (map[this.x][this.y] != ' ');
    }
    
    private void move(char map[][], int patternID, int x, int y, Player player) {
        boolean moveX = this.distX >= this.distY;  // Priorità sull'asse X
        boolean canMoveX = dx != 0 && this.energy > this.moveEnergyCost && map[this.x + dx][this.y] == ' ';
        boolean isWallX = dx != 0 && map[this.x + dx][this.y] == '/';

        boolean canMoveY = dy != 0 && this.energy > this.moveEnergyCost && map[this.x][this.y + dy] == ' ';
        boolean isWallY = dy != 0 && map[this.x][this.y + dy] == '/';

        if (patternID == 1) {
            // If we prioritize axis X and there is not a wall, we move on X
            if (moveX && canMoveX && !isWallX) {
                map[this.x][this.y] = ' ';
                this.x += dx;
                map[this.x][this.y] = this.getCh();
                this.energy -= 1.2;
            }

            // If there is a wall on axis X and we can move on Y, we move on Y
            else if (moveX && isWallX && canMoveY && !isWallY) {
                map[this.x][this.y] = ' ';
                this.y += dy;
                map[this.x][this.y] = this.getCh();
                this.energy -= 1.2;
            }

            // If we prioritize axis Y and we can move on Y, we move on Y
            else if (!moveX && canMoveY && !isWallY) {
                map[this.x][this.y] = ' ';
                this.y += dy;
                map[this.x][this.y] = this.getCh();
                this.energy -= this.moveEnergyCost;
            }

            // If the axis Y is blocked by a wall and we can move on X, we move on X
            else if (!moveX && isWallY && canMoveX && !isWallX) {
                map[this.x][this.y] = ' ';
                this.x += dx;
                map[this.x][this.y] = this.getCh();
                this.energy -= this.moveEnergyCost;
            } else {
                this.canMove = false;
            }
        }
    }
    
    private void attack(Player player) {
        if (this.energy >= this.attackEnergyCost) {
            player.hit(this.atk);
            this.energy -= this.attackEnergyCost;
        } else{
            this.canAttack = false;
        }
    }
    
    public void turn(char map[][], int patternID, int x, int y, Player player) {
        while (this.energy > 0 && (this.canAttack || this.canMove)) {
            // Calculate the distance from the player
            distX = Math.abs(this.x - x);  // Distance in X
            distY = Math.abs(this.y - y);  // Distance in Y
            
            // Verify if it's not already at destination
            if ((distX == 1 && distY == 0) || (distX == 0 && distY == 1)) {
                this.alreadyOnDestination = true;
                this.canAttack = true;
                this.canMove = false;
            } else {
                this.alreadyOnDestination = false;
                this.canAttack = false;
                this.canMove = true;
            }
            
            dx = Integer.compare(x, this.x); // -1 if we have to go left, 1 if right, 0 if it's aligned
            dy = Integer.compare(y, this.y); // -1 if we have to go up, 1 if down, 0 if it's aligned
            
            if (this.alreadyOnDestination && this.canAttack) {
                attack(player);
            } else if (!this.alreadyOnDestination && this.canMove) {
                move(map, patternID, x, y, player);
            }
        }
        
        this.canAttack = true;
        this.canMove = true;
        this.energy = this.starterEnergy;
    }
}