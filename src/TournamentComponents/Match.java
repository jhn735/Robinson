package TournamentComponents;

import BaseComponents.Player;
import JSONIO.JSONCompatible;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Match implements JSONCompatible<Match>{

    public static enum Winner {
        WHITE("White"), BLACK("Black"), TIE("Tie"), NONE("Ongoing");
        public final String string;
        private Winner(String string){
            this.string = string;
        }
    }

    private Player White;
    public Player White(){return White;}
    
    private Player Black;
    public Player Black(){return Black;}
    
    private Winner winner;
    public Winner winner(){return winner;}
    
    private static int runningMatches = 0;
    public static int getRunningMatches(){ return runningMatches;}
    
    public Match(){
        this.White = new Player();
        this.Black = new Player();
    }
 
    public Match(Player White, Player Black) {
        //first try to set up match
        this.White = White;
        this.Black = Black;
        runningMatches++;
    }

    public Match(JSONObject obj){
        this.fromJSONObject(obj);
    }
    
    public void declareWinner(Winner winner) {
        if(winner!= Winner.NONE) return;
        switch (winner) {
            case WHITE:
                White.addToRank(1);
                break;
            case BLACK:
                Black.addToRank(1);
                break;
            case TIE:
                White.addToRank(0.5);
                Black.addToRank(0.5);
                break;
            case NONE:
                //nothing to declare
                break;
        }
        runningMatches--;
    }
    
    public boolean matchSet(){ return winner != Winner.NONE;}
    
    @Override
    public void fromJSONObject(JSONObject obj) {
        this.winner = (Winner)obj.get("Winner");
        Player White = new Player((JSONObject)obj.get("White"));
        Player Black = new Player((JSONObject)obj.get("Black"));
    }
    
    @Override
    public JSONObject toJSONObject() {
    JSONObject retVal = new JSONObject();
            retVal.put("Winner", winner.string);             
            retVal.put("White", White.toJSONObject());
            retVal.put("Black", Black.toJSONObject());
            
    return retVal;
    }

    @Override
    public ArrayList<Match> fromJSONArray(JSONArray arr) {
    ArrayList<Match> retVal = new ArrayList<>();
        for(Object o:arr)
           retVal.add(new Match((JSONObject)o));
        
    return retVal;
    }

}
