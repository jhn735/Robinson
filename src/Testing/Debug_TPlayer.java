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
         //testSideStateMethods();
         //testMatchMaking();
         testOpponentMethods();
     System.out.println("All tests successful");
     }
     
     private static void testSideStateMethods(){
         
         //create a new player
         TPlayer p = new TPlayer(123, "TestPlayer", 0);
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
         //add more tests here.
         System.out.println("SideState tests successful");
     }
     
     private static void testMatchMaking(){
         TPlayer p = new TPlayer(123, "TestPlayer", 0.0);
         TPlayer o = new TPlayer(124, "TestPlayer2", 0.0);
         //"make match is trivial so no need to test" says this idiot
         
         //test opposites
         p.setSideState(TPlayer.SideState.BLACK_ONCE);
         o.setSideState(TPlayer.SideState.WHITE_ONCE);
         assert(TPlayer.MakeMatch(p,o));
         
         //test the completion when both are of value 1
         p.setSideState(TPlayer.SideState.BLACK_ONCE);
         o.setSideState(TPlayer.SideState.BLACK_ONCE);
         assert(TPlayer.MakeMatch(p, o));
         
         //test to see incompletion when both are of value 2
         p.setSideState(TPlayer.SideState.BLACK_TWICE);
         o.setSideState(TPlayer.SideState.BLACK_TWICE);
         assert(!TPlayer.MakeMatch(p, o));
         
         //test completion when only the values differ
         p.setSideState(TPlayer.SideState.BLACK_ONCE);
         o.setSideState(TPlayer.SideState.BLACK_TWICE);
         assert(TPlayer.MakeMatch(p, o) 
                 && o.sideState() == TPlayer.SideState.WHITE_ONCE);
         //test completion of when both are NONE
         p.setSideState(TPlayer.SideState.NONE);
         o.setSideState(TPlayer.SideState.NONE);
         assert(TPlayer.MakeMatch(p,o));
         
     System.out.println("Match making tests successful");
     }
     
     private static void testOpponentMethods(){
         //start by testing talliedOpponentsRanks
         TPlayer testPlayer = new TPlayer(123, "Test_Player", 10.0);
         testPlayer.setRank(10.0);
         //init tPList
         for(int i = 0; i < 10; i++){
             TPlayer p = new TPlayer(i, "Player#"+Integer.toString(i), 5.5+i);
             p.setRank(5.5+i);
             tPList.add(p);
             testPlayer.addOpponent(p);
         }                 

         double p = testPlayer.talliedOpponentsRanks();
         //should come out to 100
         assert(p == 100);
         
         //now test the byOpponentsScores comparator
         int count = 0;

         //for each player in PList add in a variable amount of opponents
         for(TPlayer currentPlayer:tPList){
             for(int i = 0; i < count; i++){
                 currentPlayer.addOpponent(tPList.get(i));
             }
             count++;
         }
         //by this, Player#1 should be at the bottom of the list
         tPList.sort(new TPlayer.byOpponentScores());
         printPlayerList(tPList);
         System.out.println("Opponents methods tests successful");
     }
}
