package kelvin.tablayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.a888888888.sport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kelvin_running_tag_friend.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link kelvin_running_tag_friend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class kelvin_running_tag_friend extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> studentList;

    private Button btnSelection;

    private OnFragmentInteractionListener mListener;

    public kelvin_running_tag_friend() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment kelvin_running_tag_friend.
     */
    // TODO: Rename and change types and number of parameters
    public static kelvin_running_tag_friend newInstance(String param1, String param2) {
        kelvin_running_tag_friend fragment = new kelvin_running_tag_friend();
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
        final View view = inflater.inflate(R.layout.fragment_kelvin_running_tag_friend, null);
        view.setOnTouchListener(this);
        List<Student> rowListItem = getAllItemList();
        btnSelection = (Button) view.findViewById(R.id.btnShow);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CardViewDataAdapter(studentList,rowListItem);
        mRecyclerView.setAdapter(mAdapter);
        btnSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> stList = ((CardViewDataAdapter) mAdapter).getStudentList();

                for (int i = 0; i < stList.size(); i++) {
                    Student singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + "\n" + singleStudent.getName().toString();
                        /*
                        * Toast.makeText( CardViewActivity.this, " " +
                        * singleStudent.getName() + " " +
                        * singleStudent.getEmailId() + " " +
                        * singleStudent.isSelected(),
                        * Toast.LENGTH_SHORT).show();
                        */
                    }

                }

                Toast.makeText(getActivity(), "Selected Students: \n" + data, Toast.LENGTH_LONG).show();
            }
        });
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
    private List<Student> getAllItemList() {
        List<Student> allItems = new ArrayList<Student>();

        allItems.add(new Student("twitter","25 Nov",R.drawable.twitter, false));
        allItems.add(new Student("dropbox","26 Nov",R.drawable.dropbox,false));
        allItems.add(new Student("facebook","27 Nov",R.drawable.facebook,false));
        allItems.add(new Student("gmail","27 Nov",R.drawable.gmail,false));
        allItems.add(new Student("googeledrive","27 Nov",R.drawable.googledrive,false));
        allItems.add(new Student("googleplue","27 Nov",R.drawable.googleplus,false));
        allItems.add(new Student("star","27 Nov",R.drawable.start,false));
        allItems.add(new Student("linkedin","27 Nov",R.drawable.linkedin,false));
        allItems.add(new Student("photoshop","27 Nov",R.drawable.photoshop,false));
        allItems.add(new Student("skype","27 Nov",R.drawable.skype,false));



        return allItems;
    }
}
