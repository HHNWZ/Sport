package necowneco.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a888888888.sport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Allsport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Allsport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Allsport extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private ArrayList ArtsID;
    private ArrayList ArtsTitle;
    private ArrayList ArtsAutID;
    private ArrayList ArtsCon;
    private ArrayList ArtsGoodnum;
    private ArrayList ArtsResnum;

    private ListView listV;
    List<ArtListItem> art_list = new ArrayList<ArtListItem>();
    private MyAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public Allsport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Allsport.
     */
    // TODO: Rename and change types and number of parameters
    public static Allsport newInstance(
            ArrayList<String> artsid,
            ArrayList<String> artstitle,
            ArrayList<String> artsautid,
            ArrayList<String> artscon,
            ArrayList<Integer> artsgoodnum,
            ArrayList<Integer> artsresnum) {
        Allsport fragment = new Allsport();
        Bundle args = new Bundle();
        args.putStringArrayList(String.valueOf(ARG_PARAM1), artsid);
        args.putStringArrayList(String.valueOf(ARG_PARAM2), artstitle);
        args.putStringArrayList(String.valueOf(ARG_PARAM3), artsautid);
        args.putStringArrayList(String.valueOf(ARG_PARAM4), artscon);
        args.putIntegerArrayList(String.valueOf(ARG_PARAM5), artsgoodnum);
        args.putIntegerArrayList(String.valueOf(ARG_PARAM6), artsresnum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArtsID = getArguments().getStringArrayList(String.valueOf(ARG_PARAM1));
            ArtsTitle = getArguments().getStringArrayList(String.valueOf(ARG_PARAM2));
            ArtsAutID = getArguments().getStringArrayList(String.valueOf(ARG_PARAM3));
            ArtsCon = getArguments().getStringArrayList(String.valueOf(ARG_PARAM4));
            ArtsGoodnum=getArguments().getIntegerArrayList(String.valueOf(ARG_PARAM5));
            ArtsResnum=getArguments().getIntegerArrayList(String.valueOf(ARG_PARAM6));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_allsport, null);
        view.setOnTouchListener(this);
        ListView Myartlist=(ListView)view.findViewById(R.id.myArtList);
        ArrayAdapter<String> myartlist=new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                ArtsTitle
        );
        Addartlist();
        adapter=new MyAdapter(getContext(),art_list);
        Myartlist.setAdapter(adapter);
        Myartlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((habaActivity) getActivity()).toArtcon(position);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void Addartlist(){
        for(int i=0;i<ArtsTitle.size();i++){
            art_list.add(
                    new ArtListItem(
                            ArtsTitle.get(i).toString(),
                            ArtsAutID.get(i).toString(),
                            ArtsCon.get(i).toString(),
                            (int)ArtsGoodnum.get(i),
                            (int)ArtsResnum.get(i)
                    )
            );
        }
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
        void onFragmentInteraction(String Tag, String number);
    }
}
