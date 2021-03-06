package BaseComponents;


import java.util.ArrayList;
import java.util.Comparator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The player object, holds data on the player and has sorting, saving loading 
 * functionality.
 * Get names and student id- the only things saved to the roster beforehand
 * @author jhn73_000
 */
public class Player implements JSONIO.JSONCompatible<Player>{
    protected int id;
    public int id(){ return id;}
    
    protected String name;
    public String name(){return name;}
    
    protected double rank;
    public double rank(){ return rank;}    
        public void setRank(double rank){this.rank = rank;}
        public void addToRank(double points){ this.rank += points;}
    
    public Player(){}
    public Player(int id, String name, double rank){
        //this.student_id = student_id;
        this.id = id;
        this.name = name;
        this.rank = rank;
    }

    public Player(JSONObject obj){
        this.fromJSONObject(obj);
    }
    //sorts id. Low to High.
    public static class ById implements Comparator<Player>{
        @Override
        public int compare(Player o1, Player o2) {
            double p1Id = o1.id();
            double p2Id = o2.id();
            if(p1Id > p2Id)
                return 1;
            else
                if(p1Id < p2Id)
                    return -1;
        return 0;
        }
    }
    
    //sorts rank. High to Low
    public static class ByRank implements Comparator<Player>{
        @Override
        public int compare(Player o1, Player o2) {
            double p1Rank = o1.rank();
            double p2Rank = o2.rank();
            if(p1Rank < p2Rank)
                return 1;
            else 
                if(p1Rank > p2Rank)
                    return -1;
            
        return 0;
        }
    }
    
    @Override
    public void fromJSONObject(JSONObject obj) {
        this.id = (int)obj.get("id");
        this.name = (String)obj.get("name");
        this.rank = (double)obj.get("rank");
    }

    @Override
    public JSONObject toJSONObject() {
       JSONObject retVal = new JSONObject();
       //retVal.put("sid", student_id);
       retVal.put("id", id);
       retVal.put("name", name);
       retVal.put("rank", rank);
       return retVal;
    }
    
    @Override
    public ArrayList<Player> fromJSONArray(JSONArray arr) {
        ArrayList<Player> retVal = new ArrayList<>();
        //for each element make a new player, extract the info from the obj and add it
        for(Object o:arr){
            Player p = new Player();
            p.fromJSONObject((JSONObject)o);
            retVal.add(p);
        }
    return retVal;
    }
}
