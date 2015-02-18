
package ListElements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import TournamentComponents.*;

/**
 *  Handles the loading and organizing of players and games.
 * @author jhn73_000
 */
public class Tournament{
    private ArrayList<Player> playerList;
    //the rounds of the tournament, round 0 is the playerList
    private ArrayList<Round> roundList = new ArrayList<>();
    public Round currentRound(){ return roundList.get(currentRoundNumber);}
    
    private int currentRoundNumber = 0;
    private int numRounds;
    
    public Tournament(ArrayList<Player> playerList, int numRounds){
        this.playerList = playerList;
        this.numRounds = numRounds++;
        //start the zeroth round and make matches.
        roundList.add(new Round(currentRoundNumber, playerList, false));
    }
    
    public boolean StartNextRound(){
        currentRoundNumber++;
        if(currentRoundNumber >= numRounds) return false;
        Round currentRound = 
                new Round(currentRoundNumber, playerList, 
                        currentRoundNumber == numRounds);
        roundList.add(currentRound);
    return true;
    }
    
    //get the match list of current round
        //JSONObject is returned because only tournament should mess directly with the list.
    public JSONObject getMatchList(){return new JSONObject();}
    
    //return a list of player ordered based on their rankings
        //all ties are broken
    public ArrayList<Player> rankedPlayerList(){
        ArrayList<Player> retVal;
        //foreach bin in the round
        for(ArrayList<TPlayer> bin:this.currentRound().playerBins()){
            //sort that bin by their opponents scores
            bin.sort(new TPlayer.byOpponentScores());
            //and add the players to the list in order.
        }
        return this.playerList;
    }
    
    public ArrayList<Player> getPlayerList(){ 
        return this.playerList;
    }
}
