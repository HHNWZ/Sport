package qwer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link foodAndKLL.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link foodAndKLL#newInstance} factory method to
 * create an instance of this fragment.
 */
public class foodAndKLL extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private int foodType;
    private int foodID;
    private ArrayList<Integer> foodNum;
    private int seleDate;

    private OnFragmentInteractionListener mListener;

    public foodAndKLL() {
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
     * @return A new instance of fragment foodAndKLL.
     */
    // TODO: Rename and change types and number of parameters
    public static foodAndKLL newInstance(int param1, int param2, ArrayList<Integer> param3, int param4) {
        foodAndKLL fragment = new foodAndKLL();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putIntegerArrayList(ARG_PARAM3,param3);
        args.putInt(ARG_PARAM4,param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodType = getArguments().getInt(ARG_PARAM1);
            foodID = getArguments().getInt(ARG_PARAM2);
            foodNum = getArguments().getIntegerArrayList(ARG_PARAM3);
            seleDate = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_and_kll, container, false);
        final ListView foodlist=(ListView)view.findViewById(R.id.myfoodList);
        foodlist.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
        ListAdapter adapter = new ArrayAdapter(getContext() , android.R.layout.simple_list_item_single_choice
                ,food_list_Item(((MainActivity)getActivity()).food_list,((MainActivity)getActivity()).food_KLL));
        foodlist.setAdapter(adapter);//將ListAdapter設定至ListView裡面
        foodlist.setItemChecked(foodID,true);
        TextView totalkll=(TextView)view.findViewById(R.id.totalKLL);
        totalkll.setText("總共"+getTheMealKLL(((MainActivity)getActivity()).food_KLL)+"大卡");
        Button addbtn=(Button)view.findViewById(R.id.ItemAdd);
        Button removebtn=(Button)view.findViewById(R.id.ItemRemove);
        Button backbtn=(Button)view.findViewById(R.id.OKBtn);
        Button okbtn=(Button)view.findViewById(R.id.backBtn);
        foodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                foodID=position;
                ((MainActivity)getActivity()).toFoodList(foodType,foodID);
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).diarys.get(seleDate).addEated(foodType,foodID,1);
                ((MainActivity)getActivity()).toFoodList(foodType,foodID);
            }
        });
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).diarys.get(seleDate).addEated(foodType,foodID,-1);
                ((MainActivity)getActivity()).toFoodList(foodType,foodID);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ShowMyDiary();
            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).ShowMyDiary();
            }
        });
        return view;
    }

    private int getTheMealKLL(ArrayList<Integer> KLL) {
        int countKLL=0;
        for(int i=0;i<foodNum.size();i++){
            countKLL+=foodNum.get(i)*KLL.get(i);
        }
        return countKLL;
    }

    public ArrayList<String> food_list_Item(ArrayList<String> food,ArrayList<Integer> KLL){
        ArrayList<String> mylist=new ArrayList<String>();
        for(int i=0;i<food.size();i++){
            mylist.add(food.get(i)+"："+KLL.get(i)+"大卡 X "+foodNum.get(i)+"份");
        }
        return mylist;
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
