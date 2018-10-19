package kelvin.tablayout;

public class ExerciseData3 {
    public String start_time;
    public String end_time;
    public String duration;
    public int mean_heart_rate;
    public int calorie;
    public int max_heart_rate;
    public int count2;

    public ExerciseData3(){

    }

    public  ExerciseData3(String start_time,String end_time,String duration,int mean_heart_rate,int calorie,int max_heart_rate,int count2){
        this.start_time =start_time;
        this.end_time = end_time;
        this.duration=duration;
        this.mean_heart_rate=mean_heart_rate;
        this.calorie=calorie;
        this.max_heart_rate=max_heart_rate;
        this.count2=count2;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getMean_heart_rate() {
        return mean_heart_rate;
    }

    public void setMean_heart_rate(int mean_heart_rate) {
        this.mean_heart_rate = mean_heart_rate;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getMax_heart_rate() {
        return max_heart_rate;
    }

    public void setMax_heart_rate(int max_heart_rate) {
        this.max_heart_rate = max_heart_rate;
    }

    public int getCount() {
        return count2;
    }

    public void setCount(int count2) {
        this.count2 = count2;
    }
}
