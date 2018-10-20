package kelvin.tablayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MonitoringToolPagerAdapter extends FragmentPagerAdapter {

    public MonitoringToolPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                BmiCalculation bmiCalculation=new BmiCalculation();
                return bmiCalculation;

            case 1:
                Calorie calorie=new Calorie();
                return  calorie;

            case 2:
                ExercisePlanning exercisePlanning = new ExercisePlanning();
                return exercisePlanning;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "BMI計算";

            case 1:
                return "攝取卡路里計算";

            case 2:
                return "運動方案";



            default:
                return null;
        }

    }
}
