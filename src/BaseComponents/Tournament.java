
package BaseComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import TournamentComponents.*;
import org.json.simple.JSONArray;

/**
 *  Handles the loading and organizing of players and games.
 * @author jhn73_000
 */
public class Tournament{
    private ArrayList<Player> playerList;
    public ArrayList<Player> playerList(){ return playerList;}
    public void newPlayer(String name, int id){playerList.add(new Player(id, name));}
    //the rounds of the tournament, round 0 is the playerList
    private ArrayList<Round> roundList = new ArrayList<>();
    public Round currentRound(){ return roundList.get(currentRoundNumber);}
    
    private int currentRoundNumber = 0;
    private int numRounds;
    
    private boolean ongoing = false;
    
    public Tournament(ArrayList<Player> playerList, int numRounds){
        this.playerList = playerList;
        this.numRounds = numRounds++;
        //start the zeroth round and make matches.
        roundList.add(new Round(currentRoundNumber, playerList, false));
        ongoing = true;
    }
    
    public Tournament(int numRounds){
       this.numRounds = numRounds++;
    }
    
    public void startTournament(){
       roundList.add(new Round(currentRoundNumber, playerList, false));
       ongoing = true;
    }
    
    public boolean StartNextRound(){
        if(!ongoing) return false;
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
    public JSONObject matchList(){
    JSONObject retVal = new JSONObject();
        if(!ongoing) return retVal;
        
        ArrayList<Match> matchList = currentRound().getMatchList();
        JSONArray list = new JSONArray();
        
        for(Match m:matchList)
            list.add(m.toJSONObject());
        
        retVal.put("MatchList", list);
    return retVal;
    }
    
    //return a list of player ordered based on their rankings
        //all ties are broken
    public JSONObject rankedPlayerList(){
        JSONObject retVal = new JSONObject();
        ArrayList<ArrayList<TPlayer>> bins = currentRound().playerBins();
        
        JSONArray list = new JSONArray();
        //for each bin sort it by opponents scores and add each player to the list
        for(ArrayList<TPlayer> bin:bins){
            bin.sort(new TPlayer.byOpponentScores());
            for(TPlayer curPlayer:bin)
                list.add(curPlayer.toJSONObject());
        }
        retVal.put("RankedPlayerList", list);
        
    return retVal;
    }
}
