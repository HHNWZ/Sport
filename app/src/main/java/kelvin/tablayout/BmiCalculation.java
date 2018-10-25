package kelvin.tablayout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BmiCalculation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BmiCalculation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private DatabaseReference exercise_plan_database;
    private View view;

    public BmiCalculation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BmiCalculation.
     */
    // TODO: Rename and change types and number of parameters
    public static BmiCalculation newInstance(String param1, String param2) {
        BmiCalculation fragment = new BmiCalculation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        exercise_plan_database=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("exercise_plan");
        Log.i("我的id",""+mAuth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bmi_calculation, null);
        Button qa=(Button)view.findViewById(R.id.button18);
        final EditText edi1=(EditText)view.findViewById(R.id.editText);
        final EditText edi2=(EditText)view.findViewById(R.id.editText2);
        //體重等級
        final TextView tviw30=(TextView)view.findViewById((R.id.textView30));
        tviw30.setText("<18.5");
        final TextView tviw31=(TextView)view.findViewById((R.id.textView31));
        tviw31.setText("18.5 - 24");
        final TextView tviw32=(TextView)view.findViewById((R.id.textView32));
        tviw32.setText("24 - 27");
        final TextView tviw36=(TextView)view.findViewById((R.id.textView36));
        tviw36.setText("27 - 30");
        final TextView tviw37=(TextView)view.findViewById((R.id.textView37));
        tviw37.setText("30 - 35");
        final TextView tviw38=(TextView)view.findViewById((R.id.textView38));
        tviw38.setText(">35");
        //體重重量
        final TextView tviw29=(TextView)view.findViewById((R.id.textView29));
        final TextView tviw28=(TextView)view.findViewById((R.id.textView28));
        final TextView tviw27=(TextView)view.findViewById((R.id.textView27));
        final TextView tviw33=(TextView)view.findViewById((R.id.textView33));
        final TextView tviw34=(TextView)view.findViewById((R.id.textView34));
        final TextView tviw35=(TextView)view.findViewById((R.id.textView35));
        final TextView tviw39=(TextView)view.findViewById((R.id.textView39));
        final TextView tviw12=(TextView)view.findViewById((R.id.textView12));
        //TextView中插入圖片
        ImageSpan mImageSpan1 = new ImageSpan(getActivity(), R.drawable.bmic1);
        SpannableString spannableString1 = new SpannableString("1體重過輕");
        spannableString1.setSpan(mImageSpan1, 0, 1, 0);
        tviw29.setText(spannableString1);
        //TextView中插入圖片
        ImageSpan mImageSpan2 = new ImageSpan(getActivity(), R.drawable.bmic2);
        SpannableString spannableString2 = new SpannableString("1體重正常");
        spannableString2.setSpan(mImageSpan2, 0, 1, 0);
        tviw28.setText(spannableString2);
        //TextView中插入圖片
        ImageSpan mImageSpan3 = new ImageSpan(getActivity(), R.drawable.bmic3);
        SpannableString spannableString3 = new SpannableString("1體重過重");
        spannableString3.setSpan(mImageSpan3, 0, 1, 0);
        tviw27.setText(spannableString3);
        //TextView中插入圖片
        ImageSpan mImageSpan4 = new ImageSpan(getActivity(), R.drawable.bmic4);
        SpannableString spannableString4 = new SpannableString("1輕度肥胖");
        spannableString4.setSpan(mImageSpan4, 0, 1, 0);
        tviw33.setText(spannableString4);
        //TextView中插入圖片
        ImageSpan mImageSpan5 = new ImageSpan(getActivity(), R.drawable.bmic5);
        SpannableString spannableString5 = new SpannableString("1中度肥胖");
        spannableString5.setSpan(mImageSpan5, 0, 1, 0);
        tviw34.setText(spannableString5);
        //TextView中插入圖片
        ImageSpan mImageSpan6 = new ImageSpan(getActivity(), R.drawable.bmic6);
        SpannableString spannableString6 = new SpannableString("1重度肥胖");
        spannableString6.setSpan(mImageSpan6, 0, 1, 0);
        tviw35.setText(spannableString6);

        qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !("".equals(edi1.getText().toString())//避免沒有輸入參數
                        || "".equals(edi2.getText().toString())) )//避免沒有輸入參數
                {
                    ImageView qwerbb=(ImageView)view.findViewById((R.id.imageView4));
                    qwerbb.setVisibility(View.GONE);
                    ImageView qwerb1 =(ImageView)view.findViewById((R.id.imageView15));
                    ImageView qwerb2 =(ImageView)view.findViewById((R.id.imageView16));
                    ImageView qwerb3 =(ImageView)view.findViewById((R.id.imageView17));
                    ImageView qwerb4 =(ImageView)view.findViewById((R.id.imageView18));
                    ImageView qwerb5 =(ImageView)view.findViewById((R.id.imageView19));
                    ImageView qwerb6 =(ImageView)view.findViewById((R.id.imageView20));
                    double h = Integer.parseInt(edi1.getText().toString());//string->int
                    double w = Integer.parseInt(edi2.getText().toString());//string->int
                    double a2;
                    String a3;
                    NumberFormat nf = NumberFormat.getInstance();   // 數字格式
                    nf.setMaximumFractionDigits(1);                 // 限制小數第一位
                    h=h/100;h=h*h;a2=w/h;a3=nf.format(w/h);//int->string
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("檢測報告");
                    tviw12.setVisibility(View.VISIBLE);
                    tviw39.setVisibility(View.VISIBLE);
                    tviw39.setText(a3);
                    tviw39.setTextColor(0xFF00FF00);
                    if(a2<18.5)
                    {
                        dialog.setMessage(getString(R.string.bmi1));
                        tviw29.setTextColor(0xFF00FF00);
                        tviw30.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF000000);
                        tviw32.setTextColor(0xFF000000);
                        tviw36.setTextColor(0xFF000000);
                        tviw37.setTextColor(0xFF000000);
                        tviw38.setTextColor(0xFF000000);
                        tviw28.setTextColor(0xFF000000);
                        tviw27.setTextColor(0xFF000000);
                        tviw33.setTextColor(0xFF000000);
                        tviw34.setTextColor(0xFF000000);
                        tviw35.setTextColor(0xFF000000);
                        qwerb1.setVisibility(View.VISIBLE);
                        qwerb2.setVisibility(View.GONE);
                        qwerb3.setVisibility(View.GONE);
                        qwerb4.setVisibility(View.GONE);
                        qwerb5.setVisibility(View.GONE);
                        qwerb6.setVisibility(View.GONE);


                        exercise_plan_database.child("crunches").setValue(10);
                        exercise_plan_database.child("running").setValue(1);
                        exercise_plan_database.child("squats").setValue(20);
                        exercise_plan_database.child("walking").setValue(2);
                        exercise_plan_database.child("yoga").setValue(3);


                    }
                    else if (18.5 <= a2 && a2< 24)
                    {
                        dialog.setMessage(getString(R.string.bmi3));
                        tviw28.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF00FF00);
                        tviw30.setTextColor(0xFF000000);
                        tviw32.setTextColor(0xFF000000);
                        tviw36.setTextColor(0xFF000000);
                        tviw37.setTextColor(0xFF000000);
                        tviw38.setTextColor(0xFF000000);
                        tviw29.setTextColor(0xFF000000);
                        tviw27.setTextColor(0xFF000000);
                        tviw33.setTextColor(0xFF000000);
                        tviw34.setTextColor(0xFF000000);
                        tviw35.setTextColor(0xFF000000);
                        qwerb2.setVisibility(View.VISIBLE);
                        qwerb1.setVisibility(View.GONE);
                        qwerb3.setVisibility(View.GONE);
                        qwerb4.setVisibility(View.GONE);
                        qwerb5.setVisibility(View.GONE);
                        qwerb6.setVisibility(View.GONE);
                        HashMap<String,Integer> exercise_plan_data =new HashMap<>();

                        exercise_plan_database.child("crunches").setValue(20);
                        exercise_plan_database.child("running").setValue(2);
                        exercise_plan_database.child("squats").setValue(40);
                        exercise_plan_database.child("walking").setValue(4);
                        exercise_plan_database.child("yoga").setValue(6);

                    }
                    else if (24 <=a2 && a2<27)
                    {

                        exercise_plan_database.child("crunches").setValue(30);
                        exercise_plan_database.child("running").setValue(3);
                        exercise_plan_database.child("squats").setValue(60);
                        exercise_plan_database.child("walking").setValue(6);
                        exercise_plan_database.child("yoga").setValue(9);
                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("檢測報告")
                                .setMessage(getString(R.string.bmi1))
                                .setPositiveButton("來去運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                    }
                                })
                                .setNeutralButton("我還不想運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        tviw27.setTextColor(0xFF00FF00);
                        tviw32.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF000000);
                        tviw29.setTextColor(0xFF000000);
                        tviw36.setTextColor(0xFF000000);
                        tviw37.setTextColor(0xFF000000);
                        tviw38.setTextColor(0xFF000000);
                        tviw28.setTextColor(0xFF000000);
                        tviw30.setTextColor(0xFF000000);
                        tviw33.setTextColor(0xFF000000);
                        tviw34.setTextColor(0xFF000000);
                        tviw35.setTextColor(0xFF000000);
                        qwerb3.setVisibility(View.VISIBLE);
                        qwerb1.setVisibility(View.GONE);
                        qwerb2.setVisibility(View.GONE);
                        qwerb4.setVisibility(View.GONE);
                        qwerb5.setVisibility(View.GONE);
                        qwerb6.setVisibility(View.GONE);

                    }
                    else if (27 <=a2 && a2 < 30)
                    {

                        exercise_plan_database.child("crunches").setValue(40);
                        exercise_plan_database.child("running").setValue(4);
                        exercise_plan_database.child("squats").setValue(80);
                        exercise_plan_database.child("walking").setValue(8);
                        exercise_plan_database.child("yoga").setValue(12);
                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("檢測報告")
                                .setMessage(getString(R.string.bmi1))
                                .setPositiveButton("來去運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                      
                                    }
                                })
                                .setNeutralButton("我還不想運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();                    tviw33.setTextColor(0xFF00FF00);
                        tviw36.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF000000);
                        tviw32.setTextColor(0xFF000000);
                        tviw29.setTextColor(0xFF000000);
                        tviw37.setTextColor(0xFF000000);
                        tviw38.setTextColor(0xFF000000);
                        tviw28.setTextColor(0xFF000000);
                        tviw27.setTextColor(0xFF000000);
                        tviw30.setTextColor(0xFF000000);
                        tviw34.setTextColor(0xFF000000);
                        tviw35.setTextColor(0xFF000000);
                        qwerb4.setVisibility(View.VISIBLE);
                        qwerb1.setVisibility(View.GONE);
                        qwerb2.setVisibility(View.GONE);
                        qwerb3.setVisibility(View.GONE);
                        qwerb5.setVisibility(View.GONE);
                        qwerb6.setVisibility(View.GONE);

                    }
                    else if (30 <= a2 && a2 < 35)
                    {

                        exercise_plan_database.child("crunches").setValue(50);
                        exercise_plan_database.child("running").setValue(5);
                        exercise_plan_database.child("squats").setValue(100);
                        exercise_plan_database.child("walking").setValue(10);
                        exercise_plan_database.child("yoga").setValue(15);
                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("檢測報告")
                                .setMessage(getString(R.string.bmi1))
                                .setPositiveButton("來去運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                    }
                                })
                                .setNeutralButton("我還不想運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                    }
                                })
                                .show();
                        tviw34.setTextColor(0xFF00FF00);
                        tviw37.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF000000);
                        tviw32.setTextColor(0xFF000000);
                        tviw36.setTextColor(0xFF000000);
                        tviw30.setTextColor(0xFF000000);
                        tviw38.setTextColor(0xFF000000);
                        tviw28.setTextColor(0xFF000000);
                        tviw27.setTextColor(0xFF000000);
                        tviw33.setTextColor(0xFF000000);
                        tviw29.setTextColor(0xFF000000);
                        tviw35.setTextColor(0xFF000000);
                        qwerb5.setVisibility(View.VISIBLE);
                        qwerb1.setVisibility(View.GONE);
                        qwerb2.setVisibility(View.GONE);
                        qwerb3.setVisibility(View.GONE);
                        qwerb4.setVisibility(View.GONE);
                        qwerb6.setVisibility(View.GONE);

                    }
                    else if (a2 >= 35)
                    {

                        exercise_plan_database.child("crunches").setValue(60);
                        exercise_plan_database.child("running").setValue(6);
                        exercise_plan_database.child("squats").setValue(120);
                        exercise_plan_database.child("walking").setValue(12);
                        exercise_plan_database.child("yoga").setValue(18);

                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("檢測報告")
                                .setMessage(getString(R.string.bmi1))
                                .setPositiveButton("來去運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       
                                    }
                                })
                                .setNeutralButton("我還不想運動", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        tviw35.setTextColor(0xFF00FF00);
                        tviw38.setTextColor(0xFF00FF00);
                        tviw31.setTextColor(0xFF000000);
                        tviw32.setTextColor(0xFF000000);
                        tviw36.setTextColor(0xFF000000);
                        tviw37.setTextColor(0xFF000000);
                        tviw30.setTextColor(0xFF000000);
                        tviw28.setTextColor(0xFF000000);
                        tviw27.setTextColor(0xFF000000);
                        tviw33.setTextColor(0xFF000000);
                        tviw34.setTextColor(0xFF000000);
                        tviw29.setTextColor(0xFF000000);
                        qwerb6.setVisibility(View.VISIBLE);
                        qwerb1.setVisibility(View.GONE);
                        qwerb2.setVisibility(View.GONE);
                        qwerb3.setVisibility(View.GONE);
                        qwerb4.setVisibility(View.GONE);
                        qwerb5.setVisibility(View.GONE);

                    }


                    dialog.setNeutralButton("關閉",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "介面有顯示您的BMI值",Toast.LENGTH_SHORT).show();
                        }

                    });
                    dialog.show();
                }}
        });
        return view;
    }

}
