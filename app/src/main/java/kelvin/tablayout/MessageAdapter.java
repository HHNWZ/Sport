package kelvin.tablayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    private static String last_date=" ";
    public String DateChatCheck;
    private String message_one_i;
   private static Messages d;
    private FirebaseAuth mAuth;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;
        public TextView time_text_layout;
        public TextView date_text_layout;


        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            time_text_layout=(TextView)view.findViewById(R.id.time_text_layout);
            date_text_layout=(TextView)view.findViewById(R.id.date_text_layout);


        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);


        if(i!=0){
            d =mMessageList.get(i-1);
            message_one_i =d.getMessage();
        }





        String from_user = c.getFrom();
        String message_type = c.getType();

        long time;
        time=c.getTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        viewHolder.time_text_layout.setText(""+format.format(date));

        long date1;
        date1=c.getTime();
        String new_date=Time.get_Chat_date(date1);

        long date2;
        date2=d.getTime();
        String new_date2=Time.get_Chat_date(date2);




        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();




                viewHolder.displayName.setText(name);

                Picasso.with(viewHolder.profileImage.getContext()).load(image).placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);
                //Picasso.with(ChatActivity).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("i值",""+i);
        Log.i(""+i+"值現在的日期",""+new_date);
        Log.i(""+i+"值前一個i的日期",""+new_date2);
        //last_date=Time.getToDate(System.currentTimeMillis());
        if(i==0){
            viewHolder.date_text_layout.setText(new_date);
        }else {
           if(new_date2.equals(new_date)&&i!=0){
               viewHolder.date_text_layout.setVisibility(View.INVISIBLE);
           }else {
               viewHolder.date_text_layout.setText(new_date);
           }

        }





        //viewHolder.date_text_layout.setText(new_date);
        //Log.i("新資料日期:",new_date);



        //Log.i("數據資料",""+i);














        if(message_type.equals("text")) {

            viewHolder.messageText.setText(c.getMessage());
            viewHolder.messageImage.setVisibility(View.INVISIBLE);


        } else {

            viewHolder.messageText.setVisibility(View.INVISIBLE);
            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);

        }

    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}