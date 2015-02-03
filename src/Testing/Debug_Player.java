
package Testing;

import ListElements.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Debug the player's methods here
 * @author jhn73_000
 */
public class Debug_Player {
        public static ArrayList<Player> pList = new ArrayList<>();
    public static void main(String[] args){
    
        //set up list of players
        for(int i = 0; i < 10; i++){
            System.out.println(i);
            Player p = new Player(10-i, "Player#"+Integer.toString(10-i));
            p.setRank((double)(i));
            pList.add(p);
        } 
        /*test the sorting of players by rank
        Debug_Player.printPlayerList(pList);

        pList.sort(new Player.ById());
        System.out.println();
        Debug_Player.printPlayerList(pList);//*/
        
        /*test out the toJSONObject*/
        for(Player p: pList)
            System.out.println(p.toJSONObject());
        //*/
    }
    
    public static void printPlayerList(ArrayList<Player> l){
        for(Player p:l){
        System.out.println(p.name()+":"+p.rank());
        }
    }
}
