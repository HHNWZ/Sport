package kelvin.tablayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;

import java.util.List;

/**
 * Created by CCDBA on 2017/12/2.
 */

public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {
    private List<Student> stList;
    //private ItemClickListener clickListener;

    public CardViewDataAdapter(List<Student> studentList, List<Student> students) {
        this.stList = students;
    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getName());
        viewHolder.tvEmailId.setText(stList.get(position).getEmailId());
        viewHolder.imageView.setImageResource(stList.get(position).getPhoto());
       /*viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (clickListener != null) clickListener.itemClick(v, position);
           }
       });*/
        viewHolder.imageView.setTag(viewHolder);
        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());
        viewHolder.chkSelected.setTag(stList.get(position));

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Student contact = (Student) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Return the size Array List
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public CheckBox chkSelected;
        public ImageView imageView;

        public Student singleStudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);

            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chkSelected);
            imageView = (ImageView) itemLayoutView.findViewById(R.id.list_avatar);

        }
    }

    /*public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public interface ItemClickListener {
        public void itemClick(View view, int position);
    }*/
    // method to access in activity after updating selection
    public List<Student> getStudentList() {
        return stList;
    }
}
