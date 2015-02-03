package Testing;

import TournamentComponents.TPlayer;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jhn73_000
 */
public class Debug_TPlayer extends Debug_Player{
    private static ArrayList<TPlayer> tPList = new ArrayList<>();
    private static int limit = 10;
     public static void main(String[] args){
         testSideStateMethods();
     System.out.println("All tests successful");
     }
     
     private static void testSideStateMethods(){
         
         //create a new player
         TPlayer p = new TPlayer(123, "TestPlayer");
         //make sure that the side state is as it should
         assert(p.sideState() == TPlayer.SideState.NONE);
         
         p.updateSideState(TPlayer.Side.BLACK);
         assert(p.sideState() == TPlayer.SideState.BLACK_ONCE);
         
         p.updateSideState(TPlayer.Side.BLACK);
         assert(p.sideState() == TPlayer.SideState.BLACK_TWICE);
         
         p.updateSideState(TPlayer.Side.BLACK);
         //it's supposed to do this.
         assert(p.sideState() == TPlayer.SideState.BLACK_TWICE);
         
         p.updateSideState(TPlayer.Side.WHITE);
         assert(p.sideState() == TPlayer.SideState.WHITE_ONCE);
         
         p.updateSideState(TPlayer.Side.WHITE);
         assert(p.sideState()==TPlayer.SideState.WHITE_TWICE);
         
         p.updateSideState(TPlayer.Side.WHITE);
         assert(p.sideState() == TPlayer.SideState.WHITE_TWICE);
         System.out.println("SideState tests successful");
     }
     
     private static void testMatchMaking(){
         TPlayer p = new TPlayer(123, )
     System.out.println("Match making tests successful");
     }
}
