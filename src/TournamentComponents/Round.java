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
    private ArrayList<ArrayList<TPlayer>> playerBins = new ArrayList<>();
    public ArrayList<ArrayList<TPlayer>> playerBins(){ return playerBins;}
    
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
            playerBins.add(new ArrayList<>());
        
        //put the players in their appropriate bin
        for (Player p : playerList) 
            (playerBins.get((int)p.rank()*2)).add(new TPlayer(p));
        
        //sort the bins by id
        for (ArrayList<TPlayer> pl : playerBins) 
            Collections.sort(pl, new Player.ById());
    }

    private void makeMatches(){
        //make a copy of playerBins so that it doesn't fuck with it 
        ArrayList<ArrayList<TPlayer>> bins = new ArrayList<>();
        for(ArrayList<TPlayer> curBin:playerBins)
            bins.add(new ArrayList<>(curBin));

        for(int i = 0; i < bins.size(); i++){
            ArrayList<TPlayer> curBin = bins.get(i);
        //for each player in the bin
            for( int j = 0; j < curBin.size(); j++){
                TPlayer curPlayer = curBin.get(j);
                
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
                        bins.get(i+1).add(curPlayer);
                }
            }
        }
    }
    public ArrayList<TPlayer> getBin(double binNum) {
        return playerBins.get((int)(binNum*2));
    }
    
    public JSONObject binInfo(){
        JSONObject retVal = new JSONObject();
        JSONArray bins = new JSONArray();
        double i = 0.0;
        for(ArrayList<TPlayer> curBin:playerBins){

            //foreach bin list the players by name and rank
            JSONArray names = new JSONArray(); 
            
            //create the bin that will hold the array of names
            JSONObject bin = new JSONObject();
            
            for(TPlayer curPlayer:curBin){
                //create an object that holds the name and rank of a player
                JSONObject nameRank = new JSONObject();
                    nameRank.put(curPlayer.name(), curPlayer.rank());
                names.add(nameRank);
            }
            //give the current bin a name and add it to the array
            if(!names.isEmpty()){
                bin.put("Bin_"+Double.toString(i), names);
                bins.add(bin);    
            }
        i+=0.5;
        }
        retVal.put("bins", bins);
    return retVal;
    }
    
    public ArrayList<Match> getMatchList(){ return this.matchList;}

}
