package TournamentComponents;

import BaseComponents.Player;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author jhn73_000
 */
public class TPlayer extends Player{
    public TPlayer(){}
    public TPlayer(Player p){
        super(p.id(), p.name(), p.rank());
    }
    public TPlayer(int id, String name, double rank){
        super(id, name, rank);
    }
    
    private ArrayList<TPlayer> opponentList = new ArrayList<>();
    public void addOpponent(TPlayer p){ opponentList.add(p);}
    public ArrayList<TPlayer> opponentList(){return opponentList;}
    
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
    //get rid of this setter later
    public void setSideState(SideState s){ CurrentSideState = s;}
    private SideState CurrentSideState = SideState.NONE;
    public SideState sideState(){return CurrentSideState;}
        
        //updates the CurrentSideState 
        //state change table
         /*  SIDE          CURRENTSIDESTATE    RESULT
            ****          ****************    ******
            WHITE/BLACK   WHITE/BLACK_ONCE    WHITE/BLACK_TWICE
            BLACK         WHITE_ONCE/TWICE    BLACK_ONCE
            WHITE         BLACK_ONCE/TWICE    WHITE_ONCE
            NEITHER             ANY           NONE
        */
    public void updateSideState(Side side){
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
            case NEITHER:
                CurrentSideState = SideState.NONE;
                default:
                break;
        }  
    }

    //returns true if match was set, false if not.
    private boolean MakeMatch(Side side, TPlayer Opponent){
        updateSideState(side);
        //add the opponent to the list of opponents
        opponentList.add(Opponent);        
    return true;
    }  
        
    //sets up a match between two players
    public static boolean MakeMatch(TPlayer player1, TPlayer player2){
        //a player can't play against itself
        if(player1 == player2) return false;
        //if these players have played together return false
        if(player1.opponentList().contains(player2)) return false;
             
        //check the side state of each player
        SideState p1SideState = player1.sideState();
        SideState p2SideState = player2.sideState();
        
        //if the that states are none then set p1 to black and p2 to white
        if(p1SideState == SideState.NONE) 
            p1SideState = SideState.BLACK_ONCE;
        
        if(p2SideState == SideState.NONE)
            p2SideState = SideState.WHITE_ONCE;
        
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
             //if the values are the same and at 2, give up you're screwed
        if(p1Value == p2Value){ 
            if(p1Value == 2) return false;
            else{
                p1newSide = Side.WHITE; 
                p2newSide = Side.BLACK;
            }
        }else{ if(p1Value > p2Value){//if p1 played more games as one side, 
            //set it the opposite
            p1newSide = 
                (p2newSide != Side.WHITE)?Side.WHITE:Side.BLACK;
            }else{//otherwise do the same to p2   
            p2newSide = 
                (p1newSide != Side.WHITE)?Side.WHITE:Side.BLACK;
            }
        }    
            player1.MakeMatch(p1newSide, player2);
            player2.MakeMatch(p2newSide, player1);
            //matchmaking was a success
            return true;
        }
                 
    return false;
    }
    
//tally all the opponents ranks and return it
    public double talliedOpponentsRanks(){
        double retVal = 0.0;
        for(TPlayer player:opponentList)
            retVal+= player.rank();
    return retVal;
    }

    //Sorts players by the scores of their opponents combined. High to Low.
    public static class byOpponentScores implements Comparator<TPlayer>{
        @Override
        public int compare(TPlayer o1, TPlayer o2) {
            double p1tORank = o1.talliedOpponentsRanks();
            double p2tORank = o2.talliedOpponentsRanks();
            if(p1tORank < p2tORank)
                return 1;
            else 
                if(p1tORank > p2tORank)
                    return -1;
            
        return 0;
        }
        
    }
}
