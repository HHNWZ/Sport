package qwer;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.a888888888.sport.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment3 extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar need_food_toolbar;

    private OnFragmentInteractionListener mListener;

    public BlankFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blank_fragment4, null);
        view.setOnTouchListener(this);

        need_food_toolbar=(Toolbar)view.findViewById(R.id.need_food_toolbar);
        need_food_toolbar.setTitle("攝取建議");
        need_food_toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_48);
        need_food_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,new Dietcontrol(),null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        ExpandableTextView expTv1 = (ExpandableTextView)view.findViewById(R.id.expand_text_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(getString(R.string.texta));
        TextView textView=(TextView)view.findViewById(R.id.textView14);
        textView.setText("維他命A");
        textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewa=(TextView)view.findViewById(R.id.textView16);
        textViewa.setText(getString(R.string.textA));


        ExpandableTextView expTv2 = (ExpandableTextView)view.findViewById(R.id.expand_text2_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv2.setText(getString(R.string.textb1));
        TextView textViewb1=(TextView)view.findViewById(R.id.textViewb1);
        textViewb1.setText("維他命B1");
        textViewb1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb1a2=(TextView)view.findViewById(R.id.textViewb1a2);
        textViewb1a2.setText(getString(R.string.textB1));

        ExpandableTextView expTv3 = (ExpandableTextView)view.findViewById(R.id.expand_text3_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv3.setText(getString(R.string.textb2));
        TextView textViewb2=(TextView)view.findViewById(R.id.textViewb2);
        textViewb2.setText("維他命B2");
        textViewb2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb2a2=(TextView)view.findViewById(R.id.textViewb2a2);
        textViewb2a2.setText(getString(R.string.textB2));

        ExpandableTextView expTv4 = (ExpandableTextView)view.findViewById(R.id.expand_text4_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv4.setText(getString(R.string.textb6));
        TextView textViewb6=(TextView)view.findViewById(R.id.textViewb6);
        textViewb6.setText("維他命B6");
        textViewb6.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb6a2=(TextView)view.findViewById(R.id.textViewb6a2);
        textViewb6a2.setText(getString(R.string.textB6));

        ExpandableTextView expTv5 = (ExpandableTextView)view.findViewById(R.id.expand_text5_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv5.setText(getString(R.string.textb12));
        TextView textViewb12=(TextView)view.findViewById(R.id.textViewb12);
        textViewb12.setText("維他命B12");
        textViewb12.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewb12a2=(TextView)view.findViewById(R.id.textViewb12a2);
        textViewb12a2.setText(getString(R.string.textB12));

        ExpandableTextView expTv6 = (ExpandableTextView)view.findViewById(R.id.expand_text6_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv6.setText(getString(R.string.textc));
        TextView textViewc=(TextView)view.findViewById(R.id.textViewc);
        textViewc.setText("維他命C");
        textViewc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewca2=(TextView)view.findViewById(R.id.textViewca2);
        textViewca2.setText(getString(R.string.textC));

        ExpandableTextView expTv7 = (ExpandableTextView)view.findViewById(R.id.expand_text7_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv7.setText(getString(R.string.textd));
        TextView textViewd=(TextView)view.findViewById(R.id.textViewd);
        textViewd.setText("維他命D");
        textViewd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewda2=(TextView)view.findViewById(R.id.textViewda2);
        textViewda2.setText(getString(R.string.textD));

        ExpandableTextView expTv8 = (ExpandableTextView)view.findViewById(R.id.expand_text8_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv8.setText(getString(R.string.texte));
        TextView textViewe=(TextView)view.findViewById(R.id.textViewe);
        textViewe.setText("維他命E");
        textViewe.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewea2=(TextView)view.findViewById(R.id.textViewea2);
        textViewea2.setText(getString(R.string.textE));

        ExpandableTextView expTv9 = (ExpandableTextView)view.findViewById(R.id.expand_text9_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv9.setText(getString(R.string.textk));
        TextView textViewk=(TextView)view.findViewById(R.id.textViewk);
        textViewk.setText("維他命K");
        textViewk.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/asd.TTF"));// 更改字型
        TextView textViewka2=(TextView)view.findViewById(R.id.textViewka2);
        textViewka2.setText(getString(R.string.textK));
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag, String number) {
        if (mListener != null) {
            mListener.onFragmentInteraction(Tag,number);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Tag, String number);
    }
}
