package CLI;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Each statement is of a simple "CMD PARAM_1...PARAM_N" structure.
 * @author jhn73_000
 */
public class Statement {
        private statementType type;
        public statementType type(){return type;}
        
        private ArrayList<String> params = new ArrayList<>();
        
        public Statement(String statement_text){
            //init a new tokenizer
            StringTokenizer st = new StringTokenizer(statement_text);
            
            //get the first token as the command
            type = statementType.getStatement(st.nextToken());
            
            //if the statement is invalid just return, no need to do anything else
            if(type == statementType.INVALID) return;
            
            //grab all the potential parameters
            while(st.hasMoreTokens()){
                params.add(st.nextToken());
            }
            
            //if the parameter amount dosn't match the statement is invalid
            if(params.size() != type.numArgs && type.numArgs >= 0) type = statementType.INVALID;
            
        }
        
    public static enum statementType{
        ADD_PLAYER("addPlayer", 2), DISP_PLAYERS("dispPlayers", 0), 
        SET_MATCH("setMatch", 1), DISP_MATCHES("dispMatches", 0), 
        START_TOURNAMENT("startTournament", 0), QUIT("quit", -1), INVALID("", -1);
        
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

