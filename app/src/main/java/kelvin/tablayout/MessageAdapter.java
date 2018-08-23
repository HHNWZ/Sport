package kelvin.tablayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    public static String new_date2;
    public static String new_date3;

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
        public TextView mymessageText;
        public CircleImageView profileImage;
        public CircleImageView myprofileImage;
        public TextView displayName;
        public TextView mydisplayName;
        public ImageView messageImage;
        public TextView time_text_layout;
        public TextView mytime_text_layout;
        public TextView date_text_layout;
        public ImageView mymessageImage;
        public RelativeLayout leftLayout;
        public RelativeLayout rightLayout;


        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            time_text_layout=(TextView)view.findViewById(R.id.time_text_layout);
            mymessageText = (TextView) view.findViewById(R.id.my_message_text_layout);
            myprofileImage = (CircleImageView) view.findViewById(R.id.my_message_profile_layout);
            mydisplayName = (TextView) view.findViewById(R.id.my_name_text_layout);
            mytime_text_layout=(TextView)view.findViewById(R.id.my_time_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            date_text_layout=(TextView)view.findViewById(R.id.date_text_layout);
            mymessageImage = (ImageView) view.findViewById(R.id.my_message_image_layout);
            leftLayout=(RelativeLayout)view.findViewById(R.id.left_layout);
            rightLayout=(RelativeLayout)view.findViewById(R.id.right_layout);//
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);
        if(i!=0){
            d =mMessageList.get(i-1);
            message_one_i =d.getMessage();
            long date2;
            date2=d.getTime();
            new_date2=Time.get_Chat_date(date2);
        }

        long date1;
        date1=c.getTime();
        String new_date=Time.get_Chat_date(date1);
        Log.i("i值="+i+"的時間",Time.get_Chat_date(c.getTime()));
        //Log.i("前一個i值="+(i-1)+"的時間",Time.get_Chat_date(d.getTime()));




        if(i==0){
            viewHolder.date_text_layout.setText(new_date);
        }else {
            if(new_date2.equals(new_date)&&i!=0){
                viewHolder.date_text_layout.setVisibility(View.INVISIBLE);
            }else {
                viewHolder.date_text_layout.setText(new_date);//
            }

        }

        if(c.getSendType().equals("received")){
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);

            String from_user = c.getFrom();
            String message_type = c.getType();

            long time;
            time=c.getTime();
            Date date = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            viewHolder.time_text_layout.setText(""+format.format(date));




            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    viewHolder.displayName.setText(name);
                    Picasso.with(viewHolder.profileImage.getContext()).load(image).placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            if(message_type.equals("text")) {

                viewHolder.messageText.setText(c.getMessage());
                viewHolder.messageImage.setVisibility(View.INVISIBLE);
            } else {

                viewHolder.messageText.setVisibility(View.INVISIBLE);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
            }

        }else if(c.getSendType().equals("sent")){
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);

            String from_user = c.getFrom();
            String message_type = c.getType();



            long time;
            time=c.getTime();
            Date date = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            viewHolder.mytime_text_layout.setText(""+format.format(date));



            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    viewHolder.mydisplayName.setText(name);
                    Picasso.with(viewHolder.myprofileImage.getContext()).load(image).placeholder(R.drawable.default_avatar).into(viewHolder.myprofileImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            if(message_type.equals("text")) {

                viewHolder.mymessageText.setText(c.getMessage());
                viewHolder.mymessageImage.setVisibility(View.INVISIBLE);
            } else {

                viewHolder.mymessageText.setVisibility(View.INVISIBLE);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
            }
        }




















    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}