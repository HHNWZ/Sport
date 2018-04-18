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
    private String mParam1;
    private int mParam2;
    private int mParam3;
    private int mParam4;

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
    public static foodAndKLL newInstance(String param1, int param2, int param3, int param4) {
        foodAndKLL fragment = new foodAndKLL();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3,param3);
        args.putInt(ARG_PARAM4,param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            mParam4 = getArguments().getInt(ARG_PARAM4);
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
        foodlist.setItemChecked(mParam3,true);
        TextView itemnum=(TextView)view.findViewById(R.id.ItemNum);
        //Toast.makeText(getActivity(), ""+mParam4, Toast.LENGTH_SHORT).show();
        itemnum.setText(Integer.toString(mParam4));
        Button addbtn=(Button)view.findViewById(R.id.ItemAdd);
        Button removebtn=(Button)view.findViewById(R.id.ItemRemove);
        Button backbtn=(Button)view.findViewById(R.id.OKBtn);
        Button okbtn=(Button)view.findViewById(R.id.backBtn);
        foodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mParam3=position;
                ((MainActivity)getActivity()).toFoodList(mParam1,mParam3);
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).datelist.get(mParam2).eat(mParam1,foodlist.getSelectedItemPosition());
                ((MainActivity)getActivity()).toFoodList(mParam1,mParam3);
            }
        });
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public ArrayList<String> food_list_Item(ArrayList<String> food,ArrayList<Integer> KLL){
        ArrayList<String> mylist=new ArrayList<String>();
        for(int i=0;i<food.size();i++){
            mylist.add(food.get(i).concat(KLL.get(i).toString()));
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
