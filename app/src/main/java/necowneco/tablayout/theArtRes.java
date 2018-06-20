package necowneco.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a888888888.sport.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link theArtRes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link theArtRes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class theArtRes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RES_NAME_LIST="rnl";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private ArrayList<String> mReslist;
    private ArrayList<String> mResname;
    private String mArtTitle;
    private int mArtID;

    private OnFragmentInteractionListener mListener;

    public theArtRes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment theArtRes.
     */
    // TODO: Rename and change types and number of parameters
    public static theArtRes newInstance(ArrayList<String> resNL,ArrayList<String> param1, String param2,int param3) {
        theArtRes fragment = new theArtRes();
        Bundle args = new Bundle();
        args.putStringArrayList(RES_NAME_LIST, resNL);
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResname = getArguments().getStringArrayList(RES_NAME_LIST);
            mReslist = getArguments().getStringArrayList(ARG_PARAM1);
            mArtTitle = getArguments().getString(ARG_PARAM2);
            mArtID=getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_the_art_res, container, false);
        // Inflate the layout for this fragment
        final Button backart=(Button)view.findViewById(R.id.backArt);
        final ListView theartReslist=(ListView)view.findViewById(R.id.theReslist);
        final EditText theartNewres=(EditText)view.findViewById(R.id.theNewres);
        final Button addartNewres=(Button)view.findViewById(R.id.addNewres);
        backart.setText("返回貼文："+mArtTitle);
        List<HashMap<String , String>> list = new ArrayList<>();
        for(int i = 0 ; i < mResname.size() ; i++){
            HashMap<String , String> hashMap = new HashMap<>();
            hashMap.put("title" , mResname.get(i));
            hashMap.put("text" , mReslist.get(i));
            //把title , text存入HashMap之中
            list.add(hashMap);
            //把HashMap存入list之中
        }
        ListAdapter theartreslist=new SimpleAdapter(
                view.getContext(),list,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"title" , "text"},
                new int[]{android.R.id.text1 , android.R.id.text2}
        );/*
        ArrayAdapter theartreslist=new ArrayAdapter(
                view.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                mReslist
                );*/
        theartReslist.setAdapter(theartreslist);
        addartNewres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "留言："+theartNewres.getText(), Toast.LENGTH_SHORT).show();
                ((habaActivity) getActivity()).addRes(mArtID,theartNewres.getText().toString());
                ((habaActivity) getActivity()).toResList(mArtID);
            }
        });
        backart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((habaActivity) getActivity()).toArtcon(mArtID);
            }
        });
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
