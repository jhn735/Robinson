/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLI;

import static CLI.Statement.statementType;
import java.util.Scanner;

/**
 *
 * @author jhn73_000
 */
public final class CLI {
    private CLI(){}
    
    public static int REPL(){
       Scanner s = new Scanner(System.in);

        do{
            //get the next statement
            Statement curStatement = new Statement(s.nextLine());
            
            //if the statement is invalid or is quit break out of the loop
            if(curStatement.type() == statementType.QUIT) break;
            
            switch(curStatement.type()){
                case ADD_PLAYER: {break;}
                case DISP_PLAYERS: { break;}
                case SET_MATCH: {break;}
                case DISP_MATCHES: {break;}
                case START_TOURNAMENT: {break;}
                case INVALID: {break;}
            }
            
        }while(true);
        
    return 0; 
    }
    

}
