package JSONIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author jhn73_000
 */
public class JSONFile {
    //the path name of the file
    public final String filename;
    
    //the name of the main JSON object
    public final String name;
    
    //the JSONObject holding the contents of the file
    private JSONObject contents;
    public JSONObject contents(){ return contents;}
    
    //open the file and load it to the JSONObject.
    public JSONFile(String filename, String name){
        this.filename = filename;
        this.name = name;
    }
    
    //open the file and save the contents of the JSONObject to it.
    public JSONFile(JSONObject contents, String filename, String name){
        this.filename = filename;
        this.name = name;
        this.contents = contents;
        
    }
    
    private void load(){
        String fileContents = new String();
        try{ fileContents = JSONFile.readFile(this.filename);}
        catch(IOException e){}
        
        JSONParser parser = new JSONParser();
        try{ this.contents = (JSONObject)parser.parse(fileContents);}
        catch(ParseException e){}
    }
    
    private void save(){
        try{ JSONFile.writeFile(this.filename, this.contents.toJSONString());}
        catch(IOException e){}
    }
       
    static public String readFile(String filename) throws IOException{
        byte[] contents = Files.readAllBytes(Paths.get(filename));
        //utf-8 to be on the safe side
    return new String(contents, StandardCharsets.UTF_8);
    } 
    
    static public void writeFile(String filename, String contents) throws IOException{
        Files.write(Paths.get(filename), contents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
    }
}
