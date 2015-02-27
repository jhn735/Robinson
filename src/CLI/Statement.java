package CLI;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author jhn73_000
 */
public class Statement {
        final statementType type;
        
        private ArrayList<String> params = new ArrayList<>();
        
        public Statement(String statement_text){
            //init a new tokenizer
            StringTokenizer st = new StringTokenizer(statement_text);
            
            //get the first token as the command
            type = statementType.getStatement(st.nextToken());
            
            //if the statement is invalid
            if(type == statementType.INVALID) return;
            
            while(st.hasMoreTokens()){
                params.add(st.nextToken());
            }
            
        }
        
        private int evalStatement(String statement_text){

            return 0;
        }
    public static enum statementType{
        ADD_PLAYER("addPlayer", 2), DISP_PLAYERS("dispPlayers", 0), 
        SET_MATCH("setMatch", 1), DISP_MATCHES("dispMatches", 0), 
        START_TOURNAMENT("startTournament", 0), INVALID("", 0);
        
        public final String cmd;
        public final int numArgs;
        
        private statementType(String cmd, int numArgs){
            this.cmd = cmd;
            this.numArgs = numArgs;
        }
        public static statementType getStatement(String cmd){
            switch(cmd){
                case "addPlayer": return ADD_PLAYER;
                case "dispPlayers": return DISP_PLAYERS;
                case "setMatch": return SET_MATCH;
                case "dispMatches": return DISP_MATCHES;
                case "start_tournament": return START_TOURNAMENT;
                default: return INVALID;
            }
        }
    }
        
}

