package kelvin.tablayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a888888888.sport.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final  String TAG="RecyclerViewAdapter";

    private ArrayList<Integer> mFoodImage=new ArrayList<>();
    private ArrayList<String> mFoodName=new ArrayList<>();
    private ArrayList<String> mFoodUnit=new ArrayList<>();
    private ArrayList<String> mFoodCalories=new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<Integer> mFoodImage, ArrayList<String> mFoodName, ArrayList<String> mFoodUnit, ArrayList<String> mFoodCalories, Context mContext) {
        this.mFoodImage = mFoodImage;
        this.mFoodName = mFoodName;
        this.mFoodUnit = mFoodUnit;
        this.mFoodCalories = mFoodCalories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.common_food_calories_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called.");
        holder.common_food_calories_recycler_view_adapter_image.setImageResource(mFoodImage.get(position));
        holder.common_food_calories_recycler_view_adapter_name.setText(mFoodName.get(position));
        holder.common_food_calories_recycler_view_adapter_name_unit.setText(mFoodUnit.get(position));
        holder.common_food_calories_recycler_view_adapter_data.setText(mFoodCalories.get(position));

        holder.common_food_calories_recycler_view_adapter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("點擊了",""+mFoodName.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFoodName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView common_food_calories_recycler_view_adapter_image;
        TextView common_food_calories_recycler_view_adapter_name;
        TextView common_food_calories_recycler_view_adapter_name_unit;
        TextView common_food_calories_recycler_view_adapter_data;
        LinearLayout common_food_calories_recycler_view_adapter_layout;
        public ViewHolder(View itemView){
            super(itemView);
            common_food_calories_recycler_view_adapter_image=itemView.findViewById(R.id.common_food_calories_layout_image);
            common_food_calories_recycler_view_adapter_name=itemView.findViewById(R.id.common_food_calories_layout_name);
            common_food_calories_recycler_view_adapter_name_unit=itemView.findViewById(R.id.common_food_calories_layout_name_unit);
            common_food_calories_recycler_view_adapter_data=itemView.findViewById(R.id.common_food_calories_layout_data);
            common_food_calories_recycler_view_adapter_layout=itemView.findViewById(R.id.common_food_calories_layout);
        }
    }


}
