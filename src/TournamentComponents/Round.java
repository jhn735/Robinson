package TournamentComponents;

import ListElements.Player;
import ListElements.Tournament;
import java.util.ArrayList;
import java.util.Collections;
import TournamentComponents.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

    //The state of the tournament at the start of the round.
    //All the players sorted into bins based on their scores at the beginning
    //  of the round. Matches are then made accordingly.
public class Round {

    //an arrayList of arrayLists of players each bins
    private ArrayList<ArrayList<TPlayer>> playerBins;
    public ArrayList<ArrayList<TPlayer>> getPlayerBins(){ return playerBins;}
    
    private ArrayList<Match> matchList = new ArrayList<>();
    
    public Round(int roundNum, ArrayList<Player> playerList, boolean lastRound) {
        initPlayerBins(roundNum, playerList);
        if(!lastRound) makeMatches();
    }
    
    private void initPlayerBins(int roundNum, ArrayList<Player> playerList){
        //sort the playerList by rank
        Collections.sort(playerList, new Player.ByRank());
        //the scores that are possible includes lose all games
        for (double i = 0; i < roundNum; i += 0.5) 
            playerBins.add((int)i*2, new ArrayList<TPlayer>());
        
        //put the players in their appropriate bin
        for (Player p : playerList) 
            (playerBins.get((int)p.getRank()*2)).add((TPlayer) p);
        
        //sort the bins by id
        for (ArrayList<TPlayer> pl : playerBins) 
            Collections.sort(pl, new Player.ById());
    }

    private void makeMatches(){
    //for each playerbin
        for(int i = 0; i < playerBins.size(); i++){
            ArrayList<TPlayer> curBin = playerBins.get(i);
        //for each player in the bin
            for(TPlayer curPlayer:curBin){
                //attempt to make a match with the player halfway through the list
                int curBinHalfway = curBin.size()/2;
                boolean matchMade = 
                    TPlayer.MakeMatch(curPlayer, curBin.get(curBinHalfway));
                //if the attempt was a success
                    //remove both players from the list
                if(matchMade){
                    curBin.remove(curBinHalfway);
                }else{
                    //if there is only one player in the bin
                        //add that player to the next bin
                    if(curBin.size() == 1)
                        playerBins.get(i+1).add(curPlayer);
                    
                }
            }
        }
    }
    public ArrayList<TPlayer> getBin(double binNum) {
        return playerBins.get((int)(binNum*2));
    }
    
    public JSONObject getBinInfo(){
        JSONObject retVal = new JSONObject();
        double i = 0.0;
        for(ArrayList<TPlayer> curBin:playerBins){
            //foreach bin list the players by name and rank
            JSONArray names = new JSONArray(); 
            for(TPlayer curPlayer:curBin){
                JSONObject nameRank = new JSONObject();
                    nameRank.put(curPlayer.getName(), curPlayer.getRank());
                names.add(nameRank);
            }
            retVal.put("bin_"+Double.toString(i), names);
        i+=0.5;
        }
    return retVal;
    }
    
    public ArrayList<Match> getMatchList(){ return this.matchList;}

}
