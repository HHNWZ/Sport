package kelvin.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ExercisePagerAdapter extends FragmentPagerAdapter {

    public ExercisePagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                ExerciseWalking exerciseWalking =new ExerciseWalking();
                return exerciseWalking;

            case 1:
                ExerciseRunning exerciseRunning=new ExerciseRunning();
                return  exerciseRunning;

            case 2:
                ExerciseYoga exerciseYoga = new ExerciseYoga();
                return exerciseYoga;

            case 3:
                ExerciseSquats exerciseSquats= new ExerciseSquats();
                return exerciseSquats;

            case 4:
                ExerciseCrunches exerciseCrunches=new ExerciseCrunches();
                return exerciseCrunches;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "步行";

            case 1:
                return "跑步";

            case 2:
                return "瑜伽";

            case 3:
                return "深蹲";

            case 4:
                return "仰臥起坐";

            default:
                return null;
        }

    }

}
