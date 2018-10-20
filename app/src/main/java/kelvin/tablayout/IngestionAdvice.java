package kelvin.tablayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a888888888.sport.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

public class IngestionAdvice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingestion_advice);
        Toolbar ingestion_advice_toolbar=findViewById(R.id.ingestion_advice_toolbar);
        setSupportActionBar(ingestion_advice_toolbar);
        ActionBar ingestion_advice_actionbar=getSupportActionBar();
        if(ingestion_advice_actionbar!=null){
            ingestion_advice_actionbar.setTitle("攝取建議");
            ingestion_advice_actionbar.setDisplayHomeAsUpEnabled(true);
        }
        ExpandableTextView expTv1 = findViewById(R.id.expand_text_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(getString(R.string.texta));
        TextView textView=findViewById(R.id.textView14);
        textView.setText("維他命A");
        textView.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewa=findViewById(R.id.textView16);
        textViewa.setText(getString(R.string.textA));


        ExpandableTextView expTv2 = findViewById(R.id.expand_text2_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv2.setText(getString(R.string.textb1));
        TextView textViewb1=findViewById(R.id.textViewb1);
        textViewb1.setText("維他命B1");
        textViewb1.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb1a2=findViewById(R.id.textViewb1a2);
        textViewb1a2.setText(getString(R.string.textB1));

        ExpandableTextView expTv3 = findViewById(R.id.expand_text3_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv3.setText(getString(R.string.textb2));
        TextView textViewb2=findViewById(R.id.textViewb2);
        textViewb2.setText("維他命B2");
        textViewb2.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb2a2=findViewById(R.id.textViewb2a2);
        textViewb2a2.setText(getString(R.string.textB2));

        ExpandableTextView expTv4 = findViewById(R.id.expand_text4_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv4.setText(getString(R.string.textb6));
        TextView textViewb6=findViewById(R.id.textViewb6);
        textViewb6.setText("維他命B6");
        textViewb6.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb6a2=findViewById(R.id.textViewb6a2);
        textViewb6a2.setText(getString(R.string.textB6));

        ExpandableTextView expTv5 = findViewById(R.id.expand_text5_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv5.setText(getString(R.string.textb12));
        TextView textViewb12=findViewById(R.id.textViewb12);
        textViewb12.setText("維他命B12");
        textViewb12.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb12a2=findViewById(R.id.textViewb12a2);
        textViewb12a2.setText(getString(R.string.textB12));

        ExpandableTextView expTv6 = findViewById(R.id.expand_text6_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv6.setText(getString(R.string.textc));
        TextView textViewc=findViewById(R.id.textViewc);
        textViewc.setText("維他命C");
        textViewc.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewca2=findViewById(R.id.textViewca2);
        textViewca2.setText(getString(R.string.textC));

        ExpandableTextView expTv7 = findViewById(R.id.expand_text7_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv7.setText(getString(R.string.textd));
        TextView textViewd=findViewById(R.id.textViewd);
        textViewd.setText("維他命D");
        textViewd.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewda2=findViewById(R.id.textViewda2);
        textViewda2.setText(getString(R.string.textD));

        ExpandableTextView expTv8 = findViewById(R.id.expand_text8_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv8.setText(getString(R.string.texte));
        TextView textViewe=findViewById(R.id.textViewe);
        textViewe.setText("維他命E");
        textViewe.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewea2=findViewById(R.id.textViewea2);
        textViewea2.setText(getString(R.string.textE));

        ExpandableTextView expTv9 = findViewById(R.id.expand_text9_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv9.setText(getString(R.string.textk));
        TextView textViewk=findViewById(R.id.textViewk);
        textViewk.setText("維他命K");
        textViewk.setTypeface(Typeface.createFromAsset(getApplication().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewka2=findViewById(R.id.textViewka2);
        textViewka2.setText(getString(R.string.textK));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(IngestionAdvice.this,ControlDie.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
