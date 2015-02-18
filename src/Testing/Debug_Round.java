/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import ListElements.Player;
import TournamentComponents.Round;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author jhn73_000
 */
public class Debug_Round {
        private static ArrayList<Player> pList = new ArrayList<>();
        
        public static void main(String[] args){
            testBinInfo();
            System.out.println("All tests successful");
        }
        
        private static void testBinInfo(){
            //init the pList
            int bSize = 10;
            for(int i = 0; i < 10; i++){
                //to allow for bins
                for(int j = 0; j < bSize; j++){
                    Player p = new Player(i*10+j, "Player#"+Integer.toString(i*10+j));
                    p.setRank(10-i);
                    pList.add(p);
                    
                }
            bSize--;
            }
            
            //create a round and print bin info
            Round r = new Round(11, pList, false);
            JSONObject bininf = r.binInfo();
            /*for(double i = 0.0; i < 10; i+=0.5)
                System.out.println(bininf.get("Bin_"+Double.toString(i)));
            */
            System.out.println(bininf.toJSONString());
            System.out.println("BinInfo tested successfully");
        }

}
