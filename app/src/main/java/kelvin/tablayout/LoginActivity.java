package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import okhttp3.internal.Internal;

public class LoginActivity extends AppCompatActivity {

    private Toolbar login_toolbar;

    private EditText login_email_edit_text;
    private EditText login_password_edit_text;

    private Button login_button;

    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;

    private ActionBar login_action_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        login_toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(login_toolbar);
        login_action_bar=getSupportActionBar();
        login_action_bar.setTitle("請輸入登錄資料");
        login_action_bar.setDisplayHomeAsUpEnabled(true);

        mLoginProgress = new ProgressDialog(this);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        login_email_edit_text=(EditText)findViewById(R.id.login_email_edit_text);
        login_password_edit_text=(EditText)findViewById(R.id.login_password_edit_text);
        login_button=(Button)findViewById(R.id.login_button);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = login_email_edit_text.getText().toString();
                String password = login_password_edit_text.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mLoginProgress.setTitle("登錄中");
                    mLoginProgress.setMessage("請等我們檢查您的憑據.");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(email, password);
                }
            }
        });


    }



    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mLoginProgress.dismiss();

                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            OneSignal.sendTag("device_token",deviceToken);
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                    });
                } else {

                    mLoginProgress.hide();

                    String task_result = task.getException().getMessage().toString();

                    Toast.makeText(LoginActivity.this, "錯誤: " + task_result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
