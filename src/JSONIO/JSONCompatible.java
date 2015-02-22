package JSONIO;

import java.util.ArrayList;
import org.json.simple.*;

public interface JSONCompatible <E> {
        void fromJSONObject(JSONObject obj);
        ArrayList<E> fromJSONArray(JSONArray arr);
        JSONObject toJSONObject();
        

}
