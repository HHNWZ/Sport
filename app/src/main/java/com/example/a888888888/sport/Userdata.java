package com.example.a888888888.sport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Userdata.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Userdata#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Userdata extends Fragment implements View.OnTouchListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Userdata() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Userdata.
     */
    // TODO: Rename and change types and number of parameters
    public static Userdata newInstance(String param1, String param2) {
        Userdata fragment = new Userdata();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private TextView name;
    private TextView sex;
    private TextView brithday;
    private TextView telephone;
    private TextView email;
    private ImageView img;
    com.android.volley.RequestQueue requestQueue;
    private String showUri = "http://172.30.4.170:1335/getuserdata.php";
    private void getData() {

        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.POST,showUri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            JSONArray data = jsonObject.getJSONArray("data");
                            JSONObject jasondata;
                            //這邊要和上面json的名稱一樣
                            //下邊是把全部資料都印出來
                            for (int i = 0; i < data.length(); i++) {
                                jasondata = data.getJSONObject(i);
                                name.setText(jasondata.getString("name"));
                                sex.setText(jasondata.getString("性別"));
                                brithday.setText(jasondata.getString("生日"));
                                telephone.setText("0"+jasondata.getString("電話"));
                                email.setText(jasondata.getString("email"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "登入失敗，請重新輸入", Toast.LENGTH_SHORT).show();//傳資料回來在這裡
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());
                    }
                }

                )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login",Login.user);
                return params;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(Login.userimage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userdata, null);
        name =view.findViewById(R.id.textView49);
        sex =view.findViewById(R.id.textView11);
        brithday =view.findViewById(R.id.textView12);
        telephone =view.findViewById(R.id.textView13);
        email =view.findViewById(R.id.textView14);
        img = (ImageView) view.findViewById(R.id.imageView21);
        view.setOnTouchListener(this);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        getData();


        //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑
        new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(String... params)
            {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result)
            {
                img. setImageBitmap (result);
                super.onPostExecute(result);
            }
        }.execute("圖片連結網址路徑");

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
