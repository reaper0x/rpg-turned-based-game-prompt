package justsurviverpg;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Map {
    Random random=new Random();
    Scanner scanner = new Scanner(System.in);
    private char[][] map;
    private final int maxX;
    private final int maxY;
    private Player player;
    private ArrayList<Slime> slime=new ArrayList<>();
    private String key;
    private boolean isPlayerAlive;
    private String cancel;
    private String direction;
    public static int killCount;
    
    public Map() {
        maxX=12;
        maxY=12;
        map=new char[maxX][maxY];
        player = new Player(map, maxX, maxY);
        key="";
        isPlayerAlive=false;
        cancel=" ";
        killCount=0;
    }
    
//  If necessary, modify the visibility of the method to reset the map.
    private void resetMap() {
        for (int i=0; i<maxY; i++){
            for (int j=0; j<maxX; j++) {
                map[j][i]=' ';
            }
        }
    }
    
    public void showMap() {
        System.out.print("|");
        for (int i=0; i<maxY; i++) {
            System.out.print("- ");
        }
        System.out.println("|");
        
        for (int i=0; i<maxY; i++){
            System.out.print("|");
            for (int j=0; j<maxX; j++) {
                System.out.print(map[j][i]+" ");
            }
            System.out.println("|");
        }
        
        System.out.print("|");
        for (int i=0; i<maxY; i++) {
            System.out.print("- ");
        }
        System.out.println("|");
        
        System.out.println("Kills count: " + killCount);
        System.out.println("ENERGY: " + player.getEnergy());
        System.out.println("HP: " + player.getHp());
        System.out.println("ATK: " + player.getAtk());
    }
    
    public void uploadMap(int mapID) {
        resetMap();
        addWalls(mapID);
    }
    
    private void addWalls(int mapID) {
//      Map id = 1 means randomly place single walls
        if(mapID==1) {
            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    // Ensure boundaries are respected before checking neighbors
                    boolean leftEmpty = (j > 0) ? (map[j - 1][i] == ' ') : true;
                    boolean rightEmpty = (j < maxX - 1) ? (map[j + 1][i] == ' ') : true;
                    boolean upEmpty = (i > 0) ? (map[j][i - 1] == ' ') : true;
                    boolean downEmpty = (i < maxY - 1) ? (map[j][i + 1] == ' ') : true;

                    // Diagonal checks
                    boolean topLeftEmpty = (j > 0 && i > 0) ? (map[j - 1][i - 1] == ' ') : true;
                    boolean topRightEmpty = (j < maxX - 1 && i > 0) ? (map[j + 1][i - 1] == ' ') : true;
                    boolean bottomLeftEmpty = (j > 0 && i < maxY - 1) ? (map[j - 1][i + 1] == ' ') : true;
                    boolean bottomRightEmpty = (j < maxX - 1 && i < maxY - 1) ? (map[j + 1][i + 1] == ' ') : true;

                    // If surrounded by empty spaces (including diagonals), randomly place a wall
                    if (leftEmpty && rightEmpty && upEmpty && downEmpty &&
                        topLeftEmpty && topRightEmpty && bottomLeftEmpty && bottomRightEmpty) {
                        if (random.nextInt(100) <= 10) {
                            map[j][i] = '/';
                        }
                    }
                }
            }
        }
    }
    
    public void spawnPlayer() {
        map[player.getX()][player.getY()] = player.getCh();
        isPlayerAlive = true;
    }
    
    public void spawnSlime() {
        slime.add(new Slime(map, maxX, maxY));
        Slime lastSlime = slime.get(slime.size() - 1);
        lastSlime.spawn(map, maxX, maxY);
        map[lastSlime.getX()][lastSlime.getY()] = lastSlime.getCh();
    }
    
    public void slimeTurn() {
        for(Slime s: slime){
            s.turn(map, 1, player.x, player.y, player);
            showMap();
        }
    }
    
    public void playerTurn() {
        player.setEnergy(player.getStarterEnergy());
        cancel = " ";
        while (player.getEnergy() > 0 && !cancel.isEmpty()) {
            showMap();
            
            key = scanner.nextLine().toLowerCase().strip();
            
            if (key.startsWith("attack")) {
                direction = key.substring(6).strip();
                player.attackSlime(map, direction, slime);
                continue;
            }
            
            switch (key){
                case "w":
                    player.moveUp(map, maxX, maxY);
                    break;
                case "a":
                    player.moveLeft(map, maxX, maxY);
                    break;
                case "s":
                    player.moveDown(map, maxX, maxY);
                    break;
                case "d":
                    player.moveRight(map, maxX, maxY);
                    break;
                case "help":
                    help();
                default:
                    if (key.isEmpty()) {
                        System.out.println("Are you sure to pass the turn? Write anything to cancel.");
                        cancel = scanner.nextLine();
                        if (!cancel.isEmpty()) {
                           playerTurn(); 
                        }
                    } else {
                        System.out.println("Invalid command.");
                    }
            }
        }
    }
    
    public void end() {
        if (player.getHp()<=0) {
            player.setHp(0);
            isPlayerAlive=false;
            map[player.getX()][player.getY()] = ' ';
            showMap();
            System.out.println("You're dead!\nKills count: " + killCount);
            System.out.println("This game was made by Davide Queruli.");
            System.out.println("Thanks for playing, bye!");
        }
    }
    
//  Get methods:
    public boolean isPlayerAlive() {
        return isPlayerAlive;
    }
    
    public void help() {
        System.out.println("Here there is the list of the commands:");
        System.out.println("1)w  -->  move up");
        System.out.println("2)a  -->  move left");
        System.out.println("3)s  -->  move down");
        System.out.println("4)d  -->  move right");
        System.out.println("5)attack <direction>  -->  attack in a specified direction (up, left, down, right).");
        System.out.println("Example: attack up");
        System.out.println("6)help  -->  to see all the commands");
    }
}