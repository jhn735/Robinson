package TournamentComponents;

import ListElements.Player;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author jhn73_000
 */
public class TPlayer extends Player{
    private ArrayList<TPlayer> opponentList = new ArrayList<>();
    public ArrayList<TPlayer> getOpponentList(){return opponentList;}
    
    //tally all the opponents ranks and return it
    public double talliedOpponentsRanks(){
        double retVal = 0.0;
        for(TPlayer player:opponentList)
            retVal+= player.rank();
    return retVal;
    }
    //checking whether the player has played too many games as one
    //side in a row is handled by this fsm
    public static enum Side{
        WHITE, BLACK, NEITHER;
    }
    
    public static enum SideState{
        WHITE_ONCE(Side.WHITE,1), WHITE_TWICE(Side.WHITE,2),  
        BLACK_ONCE(Side.BLACK,1), BLACK_TWICE(Side.BLACK,2),
        NONE(Side.NEITHER,0);
            
        public final int value;
        public final Side side;
            
        private SideState(Side sValue, int value){
            this.side = sValue;
            this.value = value;
        }
    }
    
    private SideState CurrentSideState;
    public SideState getCurrentSideState(){return CurrentSideState;}
        
        //updates the CurrentSideState 
        //state change table
        /*  SIDE          CURRENTSIDESTATE    RESULT
            ****          ****************    ******
            WHITE/BLACK   WHITE/BLACK_ONCE    WHITE/BLACK_TWICE
            BLACK         WHITE_ONCE/TWICE    BLACK_ONCE
            WHITE         BLACK_ONCE/TWICE    WHITE_ONCE
        */
    private void updateCurrentSideState(Side side){
        switch(side){
           case WHITE:
                if(CurrentSideState.side == Side.WHITE)
                    CurrentSideState = SideState.WHITE_TWICE;
                else
                    CurrentSideState = SideState.WHITE_ONCE;
                break;
            case BLACK:
                if(CurrentSideState.side == Side.BLACK)
                    CurrentSideState = SideState.BLACK_TWICE;
                else
                    CurrentSideState = SideState.BLACK_ONCE;
                break;
                default:
                break;
        }  
    }

    //returns true if match was set, false if not.
    private boolean MakeMatch(Side side, TPlayer Opponent){
        updateCurrentSideState(side);
        //add the opponent to the list of opponents
        opponentList.add(Opponent);        
    return true;
    }  
        
    //sets up a match between two players
    public static boolean MakeMatch(TPlayer player1, TPlayer player2){
        //a player can't play against itself
        if(player1 == player2) return false;
        //if these players have played together return false
        if(player1.getOpponentList().contains(player2)) return false;
             
        //check the side state of each player
        SideState p1SideState = player1.getCurrentSideState();
        SideState p2SideState = player2.getCurrentSideState();
             
        //if the states are of opposite sides setup a game with eachothers
        //previous side
        if(p1SideState.side != p2SideState.side){
            player1.MakeMatch(p2SideState.side, player2);
            player2.MakeMatch(p1SideState.side, player1);
            //the match making was successful
            return true;
        }
   
         //if the sides are the same set the one who has played the 
            //most games on that side to the opposite side.
        if(p1SideState.side == p2SideState.side){  
            int p1Value = p1SideState.value;
            int p2Value = p2SideState.value; 
                 
            Side p1newSide = p1SideState.side;
            Side p2newSide = p2SideState.side;
             //if the values are the same, give up you're screwed
            if(p1Value == p2Value) return false;
            //if p1 played more games as one side, set it the opposite
                //otherwise do the same to p2
            if(p1Value > p2Value){
                p1newSide = (p2newSide != Side.WHITE)?Side.WHITE:Side.BLACK;
                //p2newSide does not change
            }else{      
                p2newSide = (p1newSide != Side.WHITE)?Side.WHITE:Side.BLACK;
                //p1newSide does not change
            }
            player1.MakeMatch(p1newSide, player2);
            player2.MakeMatch(p2newSide, player1);
            //matchmaking was a success
            return true;
        }
                 
    return true;
    }
      
    //Sorts players by the scores of their opponents combined. High to Low.
    public static class byOpponentScores implements Comparator<TPlayer>{
        @Override
        public int compare(TPlayer o1, TPlayer o2) {
            double p1tORank = o1.talliedOpponentsRanks();
            double p2tORank = o2.talliedOpponentsRanks();
            if(p1tORank > p2tORank)
                return 1;
            else 
                if(p1tORank < p2tORank)
                    return -1;
            
        return 0;
        }
        
    }
}
