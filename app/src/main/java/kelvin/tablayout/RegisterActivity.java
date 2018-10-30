package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {




    private Toolbar register_toolbar;
    private ActionBar register_action_bar;
    private EditText register_email_edit_text;
    private EditText register_password_edit_text;
    private EditText register_name_edit_text;

    private Button register_button;

    private DatabaseReference UserDataBase;
    private DatabaseReference UserDataBase_Int;
    private DatabaseReference crunches_dare_database;
    private DatabaseReference exercise_count_database;
    private DatabaseReference exercise_count_crunches_database;
    private DatabaseReference exercise_count_running_database;
    private DatabaseReference exercise_count_squats_database;
    private DatabaseReference exercise_count_walking_database;
    private DatabaseReference exercise_count_yoga_database;
    private DatabaseReference exercise_plan_database;
    private DatabaseReference running_dare_database;
    private DatabaseReference squats_dare_database;
    private DatabaseReference walking_dare_database;
    private DatabaseReference yoga_dare_database;


    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar Set
        register_toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(register_toolbar);
        register_action_bar=getSupportActionBar();
        register_action_bar.setTitle("請輸入註冊資料");
        register_action_bar.setDisplayHomeAsUpEnabled(true);


        mRegProgress = new ProgressDialog(this);



        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields



        register_email_edit_text=(EditText)findViewById(R.id.register_email_edit_text);
        register_password_edit_text=(EditText)findViewById(R.id.register_password_edit_text);
        register_name_edit_text=(EditText)findViewById(R.id.register_name_edit_text); 
        
        register_button=(Button)findViewById(R.id.register_button);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  email= register_email_edit_text.getText().toString();//使用者名稱
                String password = register_password_edit_text.getText().toString();//使用者電郵
                String display_name = register_name_edit_text.getText().toString();//使用者密碼
                Log.i("名字1",display_name);
                Log.i("電郵1",email);
                Log.i("密碼1",password);
                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    Log.i("名字",display_name);
                    Log.i("電郵",email);
                    Log.i("密碼",password);

                    mRegProgress.setTitle("註冊用戶");
                    mRegProgress.setMessage("我們正在創建您的帳戶，請稍候");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name, email, password);
                }else {
                    Toast.makeText(RegisterActivity.this,"請重新輸入",Toast.LENGTH_LONG).show();
                    register_email_edit_text.setText("");
                    register_password_edit_text.setText("");
                    register_name_edit_text.setText("");
                }



            }
        });


    }

    private void register_user(final String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.i("註冊成功",current_user.getUid());
                    String uid = current_user.getUid();

                    UserDataBase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("status", "不在邊緣運動");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);
                    userMap.put("exercise","default");
                    userMap.put("exercise_count","default");
                    userMap.put("DateChatCheck",Time.getToDate(System.currentTimeMillis()));
                    userMap.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                    userMap.put("crunches_all_count",""+0);
                    userMap.put("crunches_all_count_sort",""+0);
                    userMap.put("crunches_dare","default");
                    userMap.put("exercise_plan","default");
                    userMap.put("friend_count",""+0);
                    userMap.put("friend_point",""+0);
                    userMap.put("online",""+System.currentTimeMillis());
                    userMap.put("running_all_count",""+0);
                    userMap.put("running_all_count_sort",""+0);
                    userMap.put("running_dare","default");
                    userMap.put("running_to_day_count",""+0);
                    userMap.put("squats_all_count",""+0);
                    userMap.put("squats_all_count_sort",""+0);
                    userMap.put("squats_dare","default");
                    userMap.put("username","Hi "+display_name);
                    userMap.put("walking_all_count",""+0);
                    userMap.put("walking_all_count_sort",""+0);
                    userMap.put("walking_dare","default");
                    userMap.put("walking_to_day_count",""+0);
                    userMap.put("yoga_all_count",""+0);
                    userMap.put("yoga_all_count_sort",""+0);
                    userMap.put("yoga_dare","default");
                    UserDataBase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                HashMap<String,Integer>userMapInt=new HashMap<>();
                                crunches_dare_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("crunches_dare");
                                HashMap<String,String> crunches_dare_map=new HashMap<>();
                                crunches_dare_map.put("count",""+0);
                                crunches_dare_map.put("time",""+0);
                                crunches_dare_database.setValue(crunches_dare_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        exercise_count_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count");
                                        HashMap<String,String> exercise_count_map=new HashMap<>();
                                        exercise_count_map.put("crunches","default");
                                        exercise_count_map.put("running","default");
                                        exercise_count_map.put("squats","default");
                                        exercise_count_map.put("walking","default");
                                        exercise_count_map.put("yoga","default");
                                        exercise_count_database.setValue(exercise_count_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                exercise_count_crunches_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("crunches");
                                                HashMap<String,String> exercise_count_crunches_map=new HashMap<>();
                                                exercise_count_crunches_map.put("DataIdcheck","default");
                                                exercise_count_crunches_map.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                                                exercise_count_crunches_map.put("all_count",""+0);
                                                exercise_count_crunches_map.put("big_count",""+0);
                                                exercise_count_crunches_map.put("count",""+0);
                                                exercise_count_crunches_map.put("small_count",""+0);
                                                exercise_count_crunches_map.put("task_record",""+0);
                                                exercise_count_crunches_map.put("today_count",""+0);
                                                exercise_count_crunches_map.put("week_calorie",""+0);
                                                exercise_count_crunches_map.put("week_record",""+0);
                                                exercise_count_crunches_database.setValue(exercise_count_crunches_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        exercise_count_running_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("running");
                                                        HashMap<String,String> exercise_count_running_map=new HashMap<>();
                                                        exercise_count_running_map.put("DataIdcheck","default");
                                                        exercise_count_running_map.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                                                        exercise_count_running_map.put("all_record",""+0);
                                                        exercise_count_running_map.put("distance",""+0);
                                                        exercise_count_running_map.put("long_distance",""+0);
                                                        exercise_count_running_map.put("short_distance",""+0);
                                                        exercise_count_running_map.put("task_record",""+0);
                                                        exercise_count_running_map.put("today_record",""+0);
                                                        exercise_count_running_map.put("week_calorie",""+0);
                                                        exercise_count_running_map.put("week_record",""+0);
                                                        exercise_count_running_database.setValue(exercise_count_running_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                exercise_count_squats_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("squats");
                                                                HashMap<String,String> exercise_count_squats_map=new HashMap<>();
                                                                exercise_count_squats_map.put("DataIdcheck","default");
                                                                exercise_count_squats_map.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                                                                exercise_count_squats_map.put("all_count",""+0);
                                                                exercise_count_squats_map.put("big_count",""+0);
                                                                exercise_count_squats_map.put("count",""+0);
                                                                exercise_count_squats_map.put("small_count",""+0);
                                                                exercise_count_squats_map.put("task_record",""+0);
                                                                exercise_count_squats_map.put("today_count",""+0);
                                                                exercise_count_squats_map.put("week_calorie",""+0);
                                                                exercise_count_squats_map.put("week_record",""+0);
                                                                exercise_count_squats_database.setValue(exercise_count_squats_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        exercise_count_walking_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("walking");
                                                                        HashMap<String,String> exercise_count_walking_map=new HashMap<>();
                                                                        exercise_count_walking_map.put("DataIdcheck","default");
                                                                        exercise_count_walking_map.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                                                                        exercise_count_walking_map.put("all_record",""+0);
                                                                        exercise_count_walking_map.put("distance",""+0);
                                                                        exercise_count_walking_map.put("long_distance",""+0);
                                                                        exercise_count_walking_map.put("short_distance",""+0);
                                                                        exercise_count_walking_map.put("task_record",""+0);
                                                                        exercise_count_walking_map.put("today_record",""+0);
                                                                        exercise_count_walking_map.put("week_calorie",""+0);
                                                                        exercise_count_walking_map.put("week_record",""+0);
                                                                        exercise_count_walking_database.setValue(exercise_count_walking_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                exercise_count_yoga_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("yoga");
                                                                                HashMap<String,String> exercise_count_yoga_map=new HashMap<>();
                                                                                exercise_count_yoga_map.put("DataIdcheck","default");
                                                                                exercise_count_yoga_map.put("DateCheck",Time.getToDate(System.currentTimeMillis()));
                                                                                exercise_count_yoga_map.put("all_time",""+0);
                                                                                exercise_count_yoga_map.put("long_time",""+0);
                                                                                exercise_count_yoga_map.put("short_time",""+0);
                                                                                exercise_count_yoga_map.put("task_record",""+0);
                                                                                exercise_count_yoga_map.put("task_time",""+0);
                                                                                exercise_count_yoga_map.put("time",""+0);
                                                                                exercise_count_yoga_map.put("today_count",""+0);
                                                                                exercise_count_yoga_map.put("today_time",""+0);
                                                                                exercise_count_yoga_map.put("week_calorie",""+0);
                                                                                exercise_count_yoga_map.put("week_record",""+0);
                                                                                exercise_count_yoga_database.setValue(exercise_count_yoga_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        exercise_plan_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_plan");
                                                                                        HashMap<String,String>exercise_plan_map=new HashMap<>();
                                                                                        exercise_plan_map.put("crunches",""+0);
                                                                                        exercise_plan_map.put("running",""+0);
                                                                                        exercise_plan_map.put("squats",""+0);
                                                                                        exercise_plan_map.put("walking",""+0);
                                                                                        exercise_plan_map.put("yoga",""+0);
                                                                                        exercise_plan_database.setValue(exercise_plan_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                running_dare_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("running_dare");
                                                                                                HashMap<String,String> running_dare_map=new HashMap<>();
                                                                                                running_dare_map.put("distance",""+0);
                                                                                                running_dare_map.put("time",""+0);
                                                                                                running_dare_database.setValue(running_dare_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        squats_dare_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("squats_dare");
                                                                                                        HashMap<String,String> squats_dare_map=new HashMap<>();
                                                                                                        squats_dare_map.put("count",""+0);
                                                                                                        squats_dare_map.put("time",""+0);
                                                                                                        squats_dare_database.setValue(squats_dare_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                walking_dare_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("walking_dare");
                                                                                                                HashMap<String,String> walking_dare_map=new HashMap<>();
                                                                                                                walking_dare_map.put("distance",""+0);
                                                                                                                walking_dare_map.put("time",""+0);
                                                                                                                walking_dare_database.setValue(walking_dare_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                        yoga_dare_database=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("yoga_dare");
                                                                                                                        HashMap<String,String> yoga_dare_map=new HashMap<>();
                                                                                                                        yoga_dare_map.put("calorie",""+0);
                                                                                                                        yoga_dare_map.put("time",""+0);
                                                                                                                        yoga_dare_database.setValue(yoga_dare_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                UserDataBase_Int=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                                                                                                                UserDataBase_Int.child("crunches_all_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("crunches_all_count_sort").setValue(0);
                                                                                                                                UserDataBase_Int.child("friend_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("friend_point").setValue(0);
                                                                                                                                UserDataBase_Int.child("running_all_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("running_all_count_sort").setValue(0);
                                                                                                                                UserDataBase_Int.child("walking_all_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("walking_all_count_sort").setValue(0);
                                                                                                                                UserDataBase_Int.child("squats_all_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("squats_all_count_sort").setValue(0);
                                                                                                                                UserDataBase_Int.child("walking_to_day_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("running_to_day_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("yoga_all_count").setValue(0);
                                                                                                                                UserDataBase_Int.child("yoga_all_count_sort").setValue(0);
                                                                                                                                mRegProgress.dismiss();
                                                                                                                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                                                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                                                startActivity(mainIntent);
                                                                                                                                finish();
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });


                                            }
                                        });

                                    }
                                });


                            }

                        }

                    });




                } else {

                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "無法註冊。請檢查表單然後重試。", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
