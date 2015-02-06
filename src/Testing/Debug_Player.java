
package Testing;

import ListElements.Player;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Debug the player's methods here
 * @author jhn73_000
 */
public class Debug_Player {
        public static ArrayList<Player> pList = new ArrayList<>();
    public static void main(String[] args){
        /*//set up list of players
        for(int i = 0; i < 10; i++){
            System.out.println(i);
            Player p = new Player(10-i, "Player#"+Integer.toString(10-i));
            p.setRank((double)(i));
            pList.add(p);
        } */
        
        /*test the sorting of players by rank
        Debug_Player.printPlayerList(pList);

        pList.sort(new Player.ById());
        System.out.println();
        Debug_Player.printPlayerList(pList);//*/
        
        /*test out the fromJSONObject
        for(int i = 0; i < 10; i++){
            JSONObject nPlayer = new JSONObject();
            nPlayer.put("sid", i);
            nPlayer.put("id", i);
            nPlayer.put("name", "Player#"+Integer.toString(i));
            nPlayer.put("rank", (double)(10-i));
            Player p = new Player();
            p.fromJSONObject(nPlayer);
            pList.add(p);
        }
        /*test out the toJSONObject
        for(Player p: pList)
            System.out.println((p.toJSONObject()).toJSONString());
        //*/
        
    }   
    
    public static void printPlayerList(List l){
        for(Object current:l){
            Player p = (Player)current;
        System.out.println(p.name()+":"+p.rank());
        }
    }
}
