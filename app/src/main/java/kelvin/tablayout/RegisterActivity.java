package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateBtn;


    private Toolbar mToolbar;

    private DatabaseReference mDatabase,mDatabase1,mDatabase2,mDatabase3,mDatabase4,mDatabase5,mDatabase6;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        mToolbar.setTitle("註冊帳號");
        mToolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        mRegProgress = new ProgressDialog(this);



        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields

        mDisplayName = (TextInputLayout) findViewById(R.id.register_display_name);
        mEmail = (TextInputLayout) findViewById(R.id.register_email);
        mPassword = (TextInputLayout) findViewById(R.id.reg_password);
        mCreateBtn = (Button) findViewById(R.id.reg_create_btn);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String display_name = mDisplayName.getEditText().getText().toString();//使用者名稱
                String email = mEmail.getEditText().getText().toString();//使用者電郵
                String password = mPassword.getEditText().getText().toString();//使用者密碼

                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("註冊用戶");
                    mRegProgress.setMessage("我們正在創建您的帳戶，請稍候");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name, email, password);

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
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("status", "不在邊緣運動");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("device_token", device_token);
                    userMap.put("exercise","default");
                    userMap.put("exercise_count","default");


                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mDatabase1= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise");
                                HashMap<String,String> exercise_Map =new HashMap<>();
                                exercise_Map.put("running","default");
                                exercise_Map.put("walking","default");

                                mDatabase1.setValue(exercise_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mDatabase2= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise").child("running");
                                            HashMap<String,String>running_Map=new HashMap<>();
                                            running_Map.put("DataIdcheck","0");
                                            running_Map.put("dataId"," ");

                                            mDatabase2.setValue(running_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        mDatabase3= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise").child("walking");
                                                        HashMap<String,String>Walking_Map=new HashMap<>();
                                                        Walking_Map.put("DataIdcheck"," ");
                                                        Walking_Map.put("dataId","0");
                                                        mDatabase3.setValue(Walking_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    mDatabase4= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count");
                                                                    HashMap<String,String> exercise_count_Map =new HashMap<>();
                                                                    exercise_count_Map.put("running","default");
                                                                    exercise_count_Map.put("walking","default");
                                                                    mDatabase4.setValue(exercise_count_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()){
                                                                                mDatabase5=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("running");
                                                                                HashMap<String,String> running_count_Map =new HashMap<>();
                                                                                running_count_Map.put("all_record","0");
                                                                                running_count_Map.put("distance","0");
                                                                                running_count_Map.put("long_distance","0");
                                                                                running_count_Map.put("short_distance","0");
                                                                                running_count_Map.put("today_record","0");
                                                                                mDatabase5.setValue(running_count_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if(task.isSuccessful()){
                                                                                            mDatabase6=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("exercise_count").child("walking");
                                                                                            HashMap<String,String> walking_count_Map =new HashMap<>();
                                                                                            walking_count_Map.put("all_record","0");
                                                                                            walking_count_Map.put("distance","0");
                                                                                            walking_count_Map.put("long_distance","0");
                                                                                            walking_count_Map.put("short_distance","0");
                                                                                            walking_count_Map.put("today_record","0");
                                                                                            mDatabase6.setValue(walking_count_Map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if(task.isSuccessful()){
                                                                                                        mRegProgress.dismiss();
                                                                                                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                                                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                        startActivity(mainIntent);
                                                                                                        finish();
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    }
                                                                                });

                                                                            }

                                                                        }
                                                                    });
                                                                }

                                                            }
                                                        });
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });

                                /*mRegProgress.dismiss();

                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);*/
                                //finish();

                            }

                        }
                    });


                } else {

                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "無法登錄。請檢查表單然後重試。", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
