package ListElements;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.lang.*;
import java.util.logging.*;

/**
 *  Dynamically load, build and modify a list from a json file.
 * @author jhn73_000
 */
public class JsonList<E extends JsonList.ListElement> {
    private String filename;
    private String rawJSON;
    private JSONArray mainList;
    private Class<E> listType;
    private ArrayList<E> contents = new ArrayList<>();
    
    public JsonList(String filename){
        this.filename= filename;
        try{ rawJSON = readFile(filename);}catch(IOException e){ }
        load(rawJSON);
        build();
    }
    
    static public String readFile(String filename) throws IOException{
        byte[] contents = Files.readAllBytes(Paths.get(filename));
        //utf-8 to be on the safe side
     return new String(contents, StandardCharsets.UTF_8);
    } 
    
    static public void writeFile(String filename, String contents) throws IOException{
        Files.write(Paths.get(filename), contents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
    }
    
    public void load(String contents){
        JSONParser parser = new JSONParser();
        try{ mainList = (JSONArray)parser.parse(contents);}
        catch(ParseException e){}
    }
    
    public void build(){
         E element;
        //foreach of the elements of the mainList
        for(JSONObject obj:(JSONObject[])mainList.toArray()){
            try {
                element = listType.newInstance();
                element.fromJSONObject(obj);
                contents.add(element);
            } catch (InstantiationException ex) {
                Logger.getLogger(JsonList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JsonList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public E get(int index){
        return contents.get(index);
    }
    
    public ArrayList<E> getContents(){ return contents;}
    
    public int size(){
        return contents.size();
    }
    
    public void add(E newElement){
        contents.add(newElement);
        mainList.add(newElement.toJSONObject());
        save();
    }
    
    public void save(){
        try{writeFile(this.filename, mainList.toString());} catch (IOException ex) {
            Logger.getLogger(JsonList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public interface ListElement{
        void fromJSONObject(JSONObject obj);
        JSONObject toJSONObject();
    }
}
