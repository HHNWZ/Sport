package kelvin.tablayout;

public class Food_note {
    int food_icon=0;
    String food_name=null;
    int food_calorie=0;
    int number_food_component=0;
    String number_food_component_unit=null;
    boolean selected = false;

    public Food_note(int food_icon, String food_name, int food_calorie, int number_food_component, String number_food_component_unit, boolean selected) {
        this.food_icon = food_icon;
        this.food_name = food_name;
        this.food_calorie = food_calorie;
        this.number_food_component = number_food_component;
        this.number_food_component_unit = number_food_component_unit;
        this.selected = selected;
    }

    public int getFood_icon() {
        return food_icon;
    }

    public void setFood_icon(int food_icon) {
        this.food_icon = food_icon;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_calorie() {
        return food_calorie;
    }

    public void setFood_calorie(int food_calorie) {
        this.food_calorie = food_calorie;
    }

    public int getNumber_food_component() {
        return number_food_component;
    }

    public void setNumber_food_component(int number_food_component) {
        this.number_food_component = number_food_component;
    }

    public String getNumber_food_component_unit() {
        return number_food_component_unit;
    }

    public void setNumber_food_component_unit(String number_food_component_unit) {
        this.number_food_component_unit = number_food_component_unit;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
