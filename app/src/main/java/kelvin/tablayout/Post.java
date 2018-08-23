package kelvin.tablayout;

import java.util.HashMap;
import java.util.Map;

public class Post {


    public String name;
    public float crunches_all_count;

    public Post() {

    }
    public Post(String name, float crunches_all_count) {
        this.name = name;
        this.crunches_all_count = crunches_all_count;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCrunches_all_count() {
        return crunches_all_count;
    }

    public void setCrunches_all_count(int crunches_all_count) {
        this.crunches_all_count = crunches_all_count;
    }


}
