/**
 * This is a program for the game Nim.
 * 
 * @Freya Ou and Roanna Zou
 * @October 28, 2018
 * 
 */

import java.util.Scanner;
import java.util.Random;

public class Nim{
    static Scanner in = new Scanner (System.in);
    static Random random = new Random ();
    static int rstick; //for computer/random generator to generate sticks
    
    //Displays main selection
    public static void main(String[] args){
        System.out.println("Welcome to Nim!");
        System.out.println("Two opponents pick either ONE or TWO sticks in turn from a set number of sticks.");
        System.out.println("Whoever gets the last stick loses.");
        chooseMode();
        int selection = in.nextInt();
        //As long as the user doesn't press 3 and quit, the program stays in the loop 
        while (selection != 3){
            if (selection == 1){ //Sinple-player mode
                singlePlayer();
            } else if (selection == 2){ //Multi-player mode
                multiPlayer();
            } else { 
                System.out.println("Invalid answer!");
            }
            selection = in.nextInt();
        }
    }
   
    //Displays multi-player instructions
    public static void multiPlayer(){
        System.out.print("How many sticks do you want to start with? ");
        //Asks user for the initial number of sticks
        int sticksInitial = in.nextInt();
        System.out.println("Players can remove one or two sticks.");
        printStick(sticksInitial); //calls the printStick method to print the number of sticks
        calcSticks(sticksInitial); //calls the calcSticks method
        chooseMode(); //re-prints the instructions for clarity 
    }
    
    //Displays single-player selections
    public static void singlePlayer(){
        chooseLevel(); //Calls instructions
        //Asks user for input of the level of difficulty
        int selection = in.nextInt();
        //As long as the user doesn't press 3 and go back to main method, the program stays in this method.
        while (selection != 3){
            if (selection == 1){ //goes to easy mode
                instructionsComp(1); 
            } else if (selection == 2){ //goes to casino mode
                instructionsComp(2);
            } else {
                System.out.println("Invalid answer!");
            }
            selection = in.nextInt();
        }
        chooseMode(); //escape to main
    }
    
    //Displys the instructions for single-player mode
    public static void instructionsComp(int n){
        in.nextLine(); //Allows the computer to read the string input
        System.out.print("What is your name? ");
        String name = in.nextLine(); //Inputs name
        
        //Gives player a description of how the computer plays and scares them. 
        System.out.printf("Hello %s, you are playing against the Computer.\nThe Computer will only remove one or two sticks. Good luck.\n", name);
        System.out.print("How many sticks do you want to start with?\nGive a number greater than 2 please.");
        int sticksInitial = in.nextInt();
        System.out.println("Players can remove one or two sticks.");
        printStick(sticksInitial);
        //choose easy mode or casino mode
        if(n==1){ //brings player to easy mode
            calcSticksCompE(sticksInitial);
        } else if(n==2){ //brings player to casino mode
            calcSticksCompC(sticksInitial);
        }
        chooseLevel(); //re-prints level instructions for clarity
    }
    
    //Calculates the sticks for easy mode
    public static void calcSticksCompE(int sticksLeft){
        //while sticksLeft is greater than 3, repeats the code. sticksLeft > 3 to ensurea nonnegative integer output
        while (sticksLeft > 3){
            System.out.print("Player remove how many sticks?");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
            
            compRemove(); //calls the method compRemove
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
        }
        //When sticksLeft is equal to 3, displays the sequence of possible events along with the corresponding winner. 
        if (sticksLeft == 3){
            System.out.print("Player remove how many sticks?");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
            
            //if player removes 1 stick, could result in sticksLeft of 2, 1, or 0
            if (decrease == 1){ 
                if (sticksLeft == 2){
                    compRemove();
                    if (decrease == 1){ //Computer wins
                        sticksLeft = sticksLeft(decrease, sticksLeft);
                        printStick(sticksLeft);
                        System.out.println("Computer wins!");
                    } else if (decrease == 2){ //Player wins
                        System.out.println("Player wins!");
                    } 
                } else if (sticksLeft == 1){ //Player wins
                    System.out.println("Player wins!");
                } else if (sticksLeft == 0){ //Computer wins
                    System.out.println("Computer wins!");
                }
            } else if (decrease == 2){ //if player remove 2 sticks, player wins
                System.out.println("Player wins!");
            }
        } //When sticksLeft is equal to 2, displays the sequence of possible events along with the corresponding winner.
        else if (sticksLeft == 2){
            System.out.print("Player remove how many sticks? ");
            int decrease = in.nextInt();
            if (decrease == 1){ //Player wins
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                System.out.println("Player wins!");
            } else if (decrease == 2){ //Computer wins
                System.out.println("Computer wins!");
            } 
        } //Computer wins
        else if (sticksLeft == 1){
            System.out.println("Computer wins!");
        } //Player wins
        else if (sticksLeft == 0){
            System.out.println("Player wins!");
        }
    }
    
    //Casino mode. Computer uses strategies and has a very big chance of winning
    public static void calcSticksCompC(int sticksLeft){ 
        /*When the intial amount of sticks is 1 mod 3, 
        Computer always leaves the player with sticks with a number 1 mod 3 so that eventually there is 1 left for the player*/
        if (sticksLeft % 3 == 1){
            loopCasino(1, sticksLeft);
        } //Initial amount of sticks is 2 mod 3.
        else if (sticksLeft % 3 == 2){ 
            System.out.print("Player remove how many sticks? ");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
            
            /* Computer can only win for sure from the beginning the player choose to remove 2 sticks as their starting move.
            To win, Computer always leaves the player with sticks with a number 1 mod 3 so that eventually there is 1 left for the player */
            if (decrease == 2) { 
                System.out.println("Computer remove 2 sticks");
                decrease = 2;
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                loopCasino(1, sticksLeft); //calls for the first part of loopCasino
            } /*When the player chooses to remove 1 stick as their starting move, 
                Computer cannot follow a pattern to win so it generates sticks randomly */
            else if (decrease == 1) { 
                loopCasino(2, sticksLeft); //calls for the second part of loopCasino
            }
        } //Initial amount of sticks is a multiple of 3
        else if (sticksLeft % 3 == 0){
            System.out.print("Player remove how many sticks? ");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
            
            /* Computer can only win for sure from the beginning the player choose to remove 1 sticks as their starting move.
            To win, Computer always leaves the player with sticks with a number 1 mod 3 so that eventually there is 1 left for the player */
            if (decrease == 1){
                System.out.println("Computer remove 1 stick");
                decrease = 1;
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                loopCasino(1, sticksLeft);
            } /*When the player chooses to remove 2 stick as their starting move, 
                Computer cannot follow a pattern to win so it generates sticks randomly */
            else if (decrease == 2){ 
                loopCasino(2, sticksLeft);
            }
        }
    }
    
    //A method to make the code more concise and less repetitive
    public static void loopCasino(int n, int sticksLeft){
        int decrease;
        //repetitive code in the Casino level that allows Computer to win for sure
        if (n == 1){ 
            while (sticksLeft > 1){
                System.out.print("Player remove how many sticks? ");
                decrease = in.nextInt();
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                    
                //creating a sum of 3 in both scenerios
                if (decrease == 1) {
                    System.out.println("Computer remove 2 sticks");
                    decrease = 2;
                } else if (decrease == 2) {
                    System.out.println("Computer remove 1 stick");
                    decrease = 1;
                }
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                }
            if (sticksLeft == 1){
                System.out.println("Computer wins!");
            }
        }//repetitive code in the Casino level that allows Computer to generate randomly until it is sure it can win
        else if (n == 2){
             while (sticksLeft > 3){
                rstick = random.nextInt(2) + 1;
                if (rstick == 1){
                    System.out.println("Computer removes 1 stick");
                } else if (rstick == 2){
                    System.out.println("Computer removes 2 sticks");
                }
                decrease = rstick;
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                 
                System.out.print("Player remove how many sticks? ");
                decrease = in.nextInt();
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
            }
             //At the end, if sticksLeft == 3, Computer can win and leaves 1 stick for the player
            if (sticksLeft == 3){ 
                System.out.println("Computer remove 2 sticks");
                decrease = 2;
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                System.out.println("Computer wins!");
            } //At the end, if sticksLeft == 2, Computer can win and leaves 1 stick for the player
            else if (sticksLeft == 2){ 
                System.out.println("Computer remove 1 stick");
                decrease = 1;
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                System.out.println("Computer wins!"); 
            } //Computer loses
            else if (sticksLeft == 1){ 
                System.out.println("Player wins!");
            } //Computer wins
            else if (sticksLeft == 0){ 
                System.out.println("Computer wins!");
            }
        }
    }
    
    //Calculates the amount of sticks in multiplayer mode
    public static void calcSticks(int sticksLeft){
        //while sticksLeft is greater than 3, repeats the code. sticksLeft > 3 to ensurea nonnegative integer output
        while (sticksLeft > 3){
            System.out.print("Player 1 remove how many sticks? ");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
          
            System.out.print("Player 2 remove how many sticks? ");
            decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
        }
        //When sticksLeft is equal to 3, displays the sequence of possible events along with the corresponding winner. 
        if (sticksLeft == 3){
            System.out.print("Player 1 remove how many sticks? ");
            int decrease = in.nextInt();
            sticksLeft = sticksLeft(decrease, sticksLeft);
            printStick(sticksLeft);
            
            //if player removes 1 stick, could result in sticksLeft of 2, 1, or 0
            if (decrease == 1){ 
                if (sticksLeft == 2){ 
                    System.out.print("Player 2 remove how many sticks? ");
                    decrease = in.nextInt();
                    if (decrease == 1){ //Player 2 wins
                        sticksLeft = sticksLeft(decrease, sticksLeft);
                        printStick(sticksLeft);
                        System.out.println("Player 2 wins!");
                    } else if (decrease == 2){ //Player 1 wins
                        System.out.println("Player 1 wins!");
                    } 
                } else if (sticksLeft == 1){ //Player 1 wins
                    System.out.println("Player 1 wins!");
                } else if (sticksLeft == 0){ //Player 2 wins
                    System.out.println("Player 2 wins!");
                }
            } else if (decrease == 2){ //if player 1 removes 2 sticks, player 1 wins
                System.out.println("Player 1 wins!");
            }
        } //When sticksLeft is equal to 2, displays the sequence of possible events along with the corresponding winner.
        else if (sticksLeft == 2){
            System.out.print("Player 1 remove how many sticks? ");
            int decrease = in.nextInt();
            if (decrease == 1){ //Player 1 wins
                sticksLeft = sticksLeft(decrease, sticksLeft);
                printStick(sticksLeft);
                System.out.println("Player 1 wins!");
            } else if (decrease == 2){ //Player 2 wins
                System.out.println("Player 2 wins!");
            } 
        } else if (sticksLeft == 1){ //Player 2 wins
            System.out.println("Player 2 wins!");
        } else if (sticksLeft == 0){ //Player 1 wins
            System.out.println("Player 1 wins!");
        }
    }
    
    //Asks user to choose mode 
    public static void chooseMode(){ 
        System.out.println("For Single-player, press 1");
        System.out.println("For Multiplayer, press 2");
        System.out.println("To Exit, press 3");
    }
    
    //Ask user to choose difficulty
    public static void chooseLevel(){ 
        System.out.println("For Easy mode, press 1");
        System.out.println("For Casino mode, press 2");
        System.out.println("To Escape and go back to main screen, press 3");
    }
    
    //Print sticks on screen
    public static void printStick(int n){ 
        for (int i = 0; i < n; i++){
            System.out.print("|");
        }
        System.out.println("");
    }
    
    //Calculate number of sticks after each removal
    public static int sticksLeft(int decrease, int sticksLeft){ 
        sticksLeft = sticksLeft - decrease;
        return sticksLeft;
    }
    
    //Process of computer removing sticks randomly (is grammatically correct as well!)
    public static void compRemove(){
        rstick = random.nextInt(2) + 1;
        if (rstick == 1){
            System.out.println("Computer removes 1 stick");
        } else if (rstick == 2){
            System.out.println("Computer removes 2 sticks");
        }
        int decrease = rstick;
    }
}