package justsurviverpg;

import java.util.Random;
import java.util.Scanner;

public class JustSurviveRpg {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        Map default_map = new Map();
        
        System.out.println("Welcome to my game!");
        System.out.println("The goal of this game is to kill as many slimes as possible.");
        default_map.help();
        System.out.println("This game was made by Davide Queruli.");
        System.out.println("GOOD LUCK!!!");
        System.out.println("Send any message to continue...");
        sc.nextLine();
        
        default_map.uploadMap(1);
        default_map.spawnPlayer();
        
        default_map.spawnSlime();
        
        while (default_map.isPlayerAlive()) {
            default_map.playerTurn();
            default_map.slimeTurn();
            
            if (random.nextInt(100) <= 35) {
                default_map.spawnSlime();
            }
            default_map.end();
        }
    }
}