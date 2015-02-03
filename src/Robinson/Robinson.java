/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Robinson;

import ListElements.*;
import java.util.Date;

/**
 *
 * @author jhn73_000
 */
public class Robinson {
    private static String Location = "UCMerced";
    private static Date date = new Date();
    private static int tournamentId = 0; 
    
    private JsonList<Player> playerList = 
            new JsonList<>("./playerList.json");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
}
