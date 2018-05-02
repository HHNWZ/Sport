package necowneco.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link theArt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link theArt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class theArt extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String THEART_ID = "param1";
    private static final String THEART_TITLE = "param2";
    private static final String THEART_AUT = "param3";
    private static final String THEART_TYPE = "param4";
    private static final String THEART_CON="param5";
    private static final String THEART_GOOD="param8";
    private static final String THEART_RES="param6";
    private static final String NOWUSER="param7";

    // TODO: Rename and change types of parameters
    private int mTarID;
    private String mTitle;
    private String mAut;
    private String mType;
    private String mCon;
    private int mGood;
    private ArrayList<String> mRes;
    private String mNowUser;

    private OnFragmentInteractionListener mListener;

    public theArt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @param param4 Parameter 4.
     * @param param5 Parameter 5.
     * @param param6 Parameter 6.
     * @param param7 Parameter 7.
     * @param param8 Parameter 8.
     * @return A new instance of fragment theArt.
     */
    // TODO: Rename and change types and number of parameters
    public static theArt newInstance(int param1,
                                     String param2,
                                     String param3,
                                     String param4,
                                     String param5,
                                     int param8,
                                     ArrayList param6,
                                     String param7) {
        theArt fragment = new theArt();
        Bundle args = new Bundle();
        args.putInt(THEART_ID, param1);
        args.putString(THEART_TITLE, param2);
        args.putString(THEART_AUT, param3);
        args.putString(THEART_TYPE,param4);
        args.putString(THEART_CON,param5);
        args.putInt(THEART_GOOD,param8);
        args.putStringArrayList(THEART_RES,param6);
        args.putString(NOWUSER,param7);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTarID = getArguments().getInt(THEART_ID);
            mTitle = getArguments().getString(THEART_TITLE);
            mAut = getArguments().getString(THEART_AUT);
            mType = getArguments().getString(THEART_TYPE);
            mCon = getArguments().getString(THEART_CON);
            mGood=getArguments().getInt(THEART_GOOD);
            mRes=getArguments().getStringArrayList(THEART_RES);
            mNowUser = getArguments().getString(NOWUSER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_the_art, container, false);
        view.setOnTouchListener(this);
        final Button backtolist=(Button)view.findViewById(R.id.BackTolist);
        final TextView theartTitle=(TextView)view.findViewById(R.id.theTitle);
        final TextView theartAut=(TextView)view.findViewById(R.id.theAut);
        final TextView theartClass=(TextView)view.findViewById(R.id.theClass);
        final TextView theartCon=(TextView)view.findViewById(R.id.theCon);
        final Button koreiine=(Button)view.findViewById(R.id.koreIINe);
        final Button tores=(Button)view.findViewById(R.id.toRes);
        final Button thedeletBtn=(Button)view.findViewById(R.id.DelebBtn);
        final Button readdBtn=(Button)view.findViewById(R.id.ReAddBtn);
        //final ListView theartReslist=(ListView)view.findViewById(R.id.theReslist);
        //final EditText theartNewres=(EditText)view.findViewById(R.id.theNewres);
        if(mNowUser==mAut) {
            thedeletBtn.setVisibility(View.VISIBLE);
            readdBtn.setVisibility(View.VISIBLE);
        }
        theartTitle.setText(mTitle);
        theartAut.setText("貼文作者："+mAut);
        theartClass.setText("貼文類別："+mType);
        theartCon.setText(mCon);
        koreiine.setText(mGood+"個讚");
        if(mRes.size()>1) {
            tores.setText("最新留言："+mRes.get(mRes.size()-1));
        }else{
            tores.setText("目前尚無留言");
        }
        koreiine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((habaActivity)getActivity()).IINe(mTarID);
                ((habaActivity)getActivity()).toArtcon(mTarID);
            }
        });

        /*ArrayAdapter<String> theartreslist=new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                mParam3
                );
        theartReslist.setAdapter(theartreslist);*/
        tores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getActivity(), "留言："+theartNewres.getText(), Toast.LENGTH_SHORT).show();
                ((habaActivity) getActivity()).addRes(mParam4,theartNewres.getText().toString());
                ((habaActivity) getActivity()).toArtcon(mParam4);*/
                ((habaActivity) getActivity()).toResList(mTarID);
            }
        });

        backtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((habaActivity) getActivity()).BackArtList();
            }
        });
        thedeletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((habaActivity) getActivity()).deletartDATA(mTarID);
            }
        });
        readdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((habaActivity)getActivity()).reAddArtDATA(mTarID,mTitle,mType,mCon);
            }
        });
        // Inflate the layout for this fragment

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String Tag,String number) {
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
    public boolean onTouch(View v, MotionEvent event) {
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
        void onFragmentInteraction(String Tag,String number);
    }
}
