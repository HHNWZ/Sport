package kelvin.tablayout;

public class FoodData {
    public String item_food;

    public FoodData(){

    }

    public FoodData(String item_food) {
        this.item_food = item_food;
    }

    public String getItem() {
        return item_food;
    }

    public void setItem(String item_food) {
        this.item_food = item_food;
    }
}
