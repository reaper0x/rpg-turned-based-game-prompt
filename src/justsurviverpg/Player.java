package justsurviverpg;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity{
    Random random=new Random();
    
    public Player(char map[][], int maxX, int maxY) {
        this.atk=2;
        this.hp=8;
        this.starterEnergy=(float) 4;
        this.energy=this.starterEnergy;
        this.moveEnergyCost=(float) 1.2;
        this.attackEnergyCost=(float) 2;
        setCh('P');
        spawn(map, maxX, maxY);
    }
    
    private void spawn(char map[][], int maxX, int maxY) {
        do{
            this.x = random.nextInt(maxX);
            this.y = random.nextInt(maxY);
        } while (map[this.y][this.x] == ' ');
    }
    
    public void moveUp(char map[][], int maxX, int maxY) {
        if (this.energy >= this.moveEnergyCost) {
            if (this.y>0 && map[this.x][this.y-1] == ' ') {
                map[this.x][this.y] = ' ';
                this.y--;
                map[this.x][this.y] = this.getCh();
            }

            this.energy -= this.moveEnergyCost;
        } else {
            System.out.println("Not enough energy.");
        }
    }
    
    public void moveLeft(char map[][], int maxX, int maxY) {
        if (this.energy >= this.moveEnergyCost) {
            if (this.x>0 && map[this.x-1][this.y] == ' ') {
                map[this.x][this.y] = ' ';
                this.x--;
                map[this.x][this.y] = this.getCh();
            }

            this.energy -= this.moveEnergyCost;
        } else {
            System.out.println("Not enough energy.");
        }
    }
    
    public void moveDown(char map[][], int maxX, int maxY) {
        if (this.energy >= this.moveEnergyCost) {
            if (this.y<maxY-1 && map[this.x][this.y+1] == ' ') { 
                map[this.x][this.y] = ' ';
                this.y++;
                map[this.x][this.y] = this.getCh();
            }

            this.energy -= this.moveEnergyCost;
        } else {
            System.out.println("Not enough energy.");
        }
    }
    
    public void moveRight(char map[][], int maxX, int maxY){
        if (this.energy >= this.moveEnergyCost) {
            if (this.x<maxX-1 && map[this.x+1][this.y] == ' ') {
                map[this.x][this.y] = ' ';
                this.x++;
                map[this.x][this.y] = this.getCh();
            }

            this.energy -= this.moveEnergyCost;
        } else {
            System.out.println("Not enough energy.");
        }
    }
    
    public Slime attack(char[][] map, int enemyX, int enemyY, ArrayList<Slime> slime) {
        for (Slime slimeEntity : slime) {
            try {
                if (map[this.x + enemyX][this.y + enemyY] == slimeEntity.getCh()
                    && slimeEntity.getX() == this.x + enemyX
                    && slimeEntity.getY() == this.y + enemyY) {

                    slimeEntity.hit(this.atk);
                    this.energy -= this.attackEnergyCost;
                    System.out.println("You hit it!");
                    return slimeEntity; // ritorna proprio lo slime colpito
                }
            } catch (Exception e) {
                return null;
            }
        }

        this.energy -= this.attackEnergyCost;
        System.out.println("Missed!");
        return null;
    }
    
    public void attackSlime(char[][] map, String direction, ArrayList<Slime> slime) {
        if (this.energy >= this.attackEnergyCost) {
            int dx = 0, dy = 0;
            switch (direction) {
                case "up": dy = -1; break;
                case "down": dy = 1; break;
                case "left": dx = -1; break;
                case "right": dx = 1; break;
                default:
                    System.out.println("Coords are not valids.");
                    return;
            }

            Slime hitSlime = attack(map, dx, dy, slime);

            if (hitSlime != null && hitSlime.getHp() <= 0) {
                map[hitSlime.getX()][hitSlime.getY()] = ' ';
                slime.remove(hitSlime); // rimuove esattamente quello colpito
                this.hp += 1.5f;
                Map.killCount++;
                System.out.println("Slime killed!");
            }

        } else {
            System.out.println("Not enough energy.");
        }
    }
}