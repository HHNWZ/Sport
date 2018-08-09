package kelvin.tablayout;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ExercisePost {
    public String start_time;
    public String end_time;

    public ExercisePost(){

    }

    public ExercisePost(String start_time,String end_time){
        this.start_time =start_time;
        this.end_time = end_time;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("start_time",start_time);
        result.put("end_time",end_time);

        return result;
    }
}
