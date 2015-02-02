package ListElements;


import java.util.ArrayList;
import java.util.Comparator;
import org.json.simple.JSONObject;

/**
 * The player object, holds data on the player and has sorting, saving loading 
 * functionality.
 * Get names and student id- the only things saved to the roster beforehand
 * @author jhn73_000
 */
public class Player implements JsonList.ListElement{
    private int id;
    private int student_id;
    private String name;
    private double rank;
        
    public Player(){}
    public Player(int id, String name){
        //this.student_id = student_id;
        this.id = id;
        this.name = name;
    }
    public String getName(){return name;}
    public int getSid(){return this.student_id;}
    
    public int getId(){ return id;}
    public void setId(int id){ this.id = id;}
    
    public double getRank(){ return rank;}
    public void setRank(double rank){this.rank = rank;}
    public void addToRank(double points){ this.rank += points;}
    
    //sorts id. Low to High.
    public static class ById implements Comparator<Player>{
        @Override
        public int compare(Player o1, Player o2) {
            double p1Id = o1.getId();
            double p2Id = o2.getId();
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
            double p1Rank = o1.getRank();
            double p2Rank = o2.getRank();
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
        //if object is compatible
            //set the value of each of the corresponding data entries
        this.student_id = (int)obj.get("sid");    
        this.id = (int)obj.get("id");
        this.name = (String)obj.get("name");
        this.rank = (double)obj.get("rank");

    }

    @Override
    public JSONObject toJSONObject() {
       JSONObject retVal = new JSONObject();
       retVal.put("sid", student_id);
       retVal.put("id", id);
       retVal.put("name", name);
       retVal.put("rank", rank);
       return retVal;
    }
}
