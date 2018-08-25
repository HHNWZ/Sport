package kelvin.tablayout;

public class Exercise_History {
    public String date;
    public String name;



    public String walking;

    public Exercise_History() {
    }

    public Exercise_History(String date,String name,String walking){
        this.date=date;
        this.name=name;
        this.walking=walking;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getWalking() {
        return walking;
    }

    public void setWalking(String walking) {
        this.walking = walking;
    }
}
