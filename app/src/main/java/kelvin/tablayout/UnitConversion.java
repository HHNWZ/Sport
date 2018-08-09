package kelvin.tablayout;

import android.app.Application;

import java.text.DecimalFormat;

public class UnitConversion extends Application {
    public static  double get_kilometer(double meter){
        double kilometer;
        kilometer=((meter/100d)/10d);
        DecimalFormat df = new DecimalFormat("0.00");

        return Double.parseDouble(df.format(kilometer));
    }

    public static double get_kilometer_per_hour(double meter_per_second){
        double kilometer_per_hour;
        kilometer_per_hour=meter_per_second*3.6;
        DecimalFormat df = new DecimalFormat("0.00");

        return  Double.parseDouble(df.format(kilometer_per_hour));
    }
}
