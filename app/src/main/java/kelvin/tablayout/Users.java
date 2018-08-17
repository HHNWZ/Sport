package kelvin.tablayout;

public class Users {
    public String name;
    public String image;
    public String status;
    public String thumb_image;
    public int crunches_all_count;
    public int squats_all_count;
    public double running_all_count;
    public double walking_all_count;
    public long yoga_all_count;




    public Users(){

    }

    public Users(String name, String image, String status, String thumb_image,int crunches_all_count,int squats_all_count,int running_all_count,int walking_all_count,long yoga_all_count) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.crunches_all_count=crunches_all_count;
        this.squats_all_count=squats_all_count;
        this.running_all_count=running_all_count;
        this.walking_all_count=walking_all_count;
        this.yoga_all_count=yoga_all_count;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public int getCrunches_all_count(){
        return crunches_all_count;
    }

    public void setCrunches_all_count(int crunches_all_count){
        this.crunches_all_count=crunches_all_count;
    }
    public int getSquats_all_count() {
        return squats_all_count;
    }

    public void setSquats_all_count(int squats_all_count) {
        this.squats_all_count = squats_all_count;
    }

    public double getRunning_all_count() {
        return running_all_count;
    }

    public void setRunning_all_count(double running_all_count) {
        this.running_all_count = running_all_count;
    }

    public double getWalking_all_count() {
        return walking_all_count;
    }

    public void setWalking_all_count(double walking_all_count) {
        this.walking_all_count = walking_all_count;
    }

    public long getYoga_all_count() {
        return yoga_all_count;
    }

    public void setYoga_all_count(long yoga_all_count) {
        this.yoga_all_count = yoga_all_count;
    }
}
