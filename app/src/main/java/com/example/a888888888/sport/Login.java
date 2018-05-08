package com.example.a888888888.sport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
    private EditText login1,login2;
    private String showUri = "http://172.30.4.170:1335/login.php";
    private HttpPost myPost;
    private DefaultHttpClient httpClient;
    private HttpEntity httpEntity;
    private HttpResponse httpResponse;
    public static String PHPSESSID = null;
    public static String userimage;
    com.android.volley.RequestQueue requestQueue;
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
                                if(Objects.equals(login1.getText().toString(), jasondata.getString("account")) &&
                                    Objects.equals(login2.getText().toString(), jasondata.getString("password")))
                                {
                                    userimage = jasondata.getString("img");
                                    Toast.makeText(getActivity(), "登入成功", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }
                                else
                                {   login1.setText("");
                                    login2.setText("");
                                    Toast.makeText(getActivity(), "登入失敗，請重新輸入", Toast.LENGTH_SHORT).show();}
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
                params.put("login", login1.getText().toString());
                return params;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        login1 = (EditText)view.findViewById(R.id.editText);
        login2 = (EditText)view.findViewById(R.id.editText2);
        Button login = (Button) view.findViewById(R.id.button18);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        
        return view;
    }

    public String executeRequest( List<NameValuePair> params) {
        String ret = "none";
        try {
            myPost = new HttpPost("http://172.30.4.170:1335/data_save.php");
            httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            myPost.setEntity(httpEntity);
//第一次一般是还未被赋值，若有值则将SessionId发给服务器
            if(null != PHPSESSID){
                myPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
            }
            httpClient = new DefaultHttpClient();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            httpResponse = httpClient.execute(myPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                ret = EntityUtils.toString(entity);
                CookieStore mCookieStore = httpClient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
//这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                    if ("PHPSESSID".equals(cookies.get(i).getName())) {
                        PHPSESSID = cookies.get(i).getValue();
                        break;
                    }

                }

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "1 "+PHPSESSID, Toast.LENGTH_SHORT).show();
        return ret;
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

