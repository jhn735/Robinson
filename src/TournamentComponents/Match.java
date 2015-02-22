package TournamentComponents;

import BaseComponents.Player;
import org.json.simple.JSONObject;

public class Match {
    public static enum Winner {
        WHITE("White"), BLACK("Black"), TIE("Tie"), NONE("Ongoing");
        public final String string;
        private Winner(String string){
            this.string = string;
        }
    }

    public final Player White;
    public final Player Black;
    private Winner winner;
    public Winner winner(){return winner;}
    
    private static int runningMatches = 0;
    public static int getRunningMatches(){ return runningMatches;}
    
    public Match(Player White, Player Black) {
        //first try to set up match
        this.White = White;
        this.Black = Black;
        runningMatches++;
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
    
    public JSONObject matchInfo(){
        JSONObject retVal = new JSONObject();
            retVal.put("Winner", winner.string);  
        JSONObject WPlayer = new JSONObject();
            WPlayer.put(White.name(), White.rank());            
            retVal.put("White", White.name());
        JSONObject BPlayer = new JSONObject();
            BPlayer.put(Black.name(), Black.rank());
            retVal.put("Black", Black.name());
            
    return retVal;
    }
}
