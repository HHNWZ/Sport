package kelvin.tablayout;

public class ExerciseData {
    public String start_time;
    public String end_time;
    public double distance;
    public String duration;
    public int mean_heart_rate;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public double getIncline_distance() {
        return incline_distance;
    }

    public void setIncline_distance(double incline_distance) {
        this.incline_distance = incline_distance;
    }

    public double getDecline_distance() {
        return decline_distance;
    }

    public void setDecline_distance(double decline_distance) {
        this.decline_distance = decline_distance;
    }

    public int getMax_heart_rate() {
        return max_heart_rate;
    }

    public void setMax_heart_rate(int max_heart_rate) {
        this.max_heart_rate = max_heart_rate;
    }

    public int getMax_altitude() {
        return max_altitude;
    }

    public void setMax_altitude(int max_altitude) {
        this.max_altitude = max_altitude;
    }

    public int getMin_altitude() {
        return min_altitude;
    }

    public void setMin_altitude(int min_altitude) {
        this.min_altitude = min_altitude;
    }

    public double getMean_speed() {
        return mean_speed;
    }

    public void setMean_speed(double mean_speed) {
        this.mean_speed = mean_speed;
    }

    public double getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(double max_speed) {
        this.max_speed = max_speed;
    }

    public int calorie;
    public double incline_distance;
    public double decline_distance;
    public int max_heart_rate;
    public int max_altitude;
    public int min_altitude;
    public double mean_speed;
    public double max_speed;

    public ExerciseData(){

    }

    public  ExerciseData(String start_time,String end_time,double distance,String duration,int mean_heart_rate,int calorie,double incline_distance,double decline_distance,int max_heart_rate,int max_altitude,int min_altitude,double mean_speed,double max_speed){
        this.start_time =start_time;
        this.end_time = end_time;
        this.distance=distance;
        this.duration=duration;
        this.mean_heart_rate=mean_heart_rate;
        this.calorie=calorie;
        this.incline_distance=incline_distance;
        this.decline_distance=decline_distance;
        this.max_heart_rate=max_heart_rate;
        this.max_altitude=max_altitude;
        this.min_altitude=min_altitude;
        this.mean_speed=mean_speed;
        this.max_speed=max_speed;


    }
}
