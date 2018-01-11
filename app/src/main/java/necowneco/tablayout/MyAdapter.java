package necowneco.tablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.a888888888.sport.R;

import java.util.List;
/**
 * Created by 黃小黃 on 2017/12/19.
 */

public class MyAdapter extends BaseAdapter implements View.OnClickListener{
    private LayoutInflater myInflater;
    private List<ArtListItem> artlists;
    private String nowuser;
    private Callback mCallback;
    public interface Callback {
        public void click(View v);
    }
    public MyAdapter(Context context,List<ArtListItem> theartlist,String nowuserID,Callback callback){
        myInflater=LayoutInflater.from(context);
        this.artlists=theartlist;
        this.nowuser=nowuserID;
        this.mCallback=callback;
    }

    @Override
    public int getCount() {
        return artlists.size();
    }

    @Override
    public Object getItem(int position) {
        return artlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return artlists.indexOf(getItem(position));
    }
    private class ViewHolder {
        TextView txtArt;
        TextView txtAut;
        TextView txtCon;
        TextView txtGdnum;
        TextView txtRsnum;
        Button deletbtn;
        public ViewHolder(TextView thetxtArt,
                          TextView thetxtAut,
                          TextView thetxtCon,
                          TextView thetxtGdnum,
                          TextView thetxtRsnum,
                          Button thedeletbtn){
            this.txtArt = thetxtArt;
            this.txtAut = thetxtAut;
            this.txtCon=thetxtCon;
            this.txtGdnum=thetxtGdnum;
            this.txtRsnum=thetxtRsnum;
            this.deletbtn=thedeletbtn;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.art_list_item, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.myTitle),
                    (TextView) convertView.findViewById(R.id.myAut),
                    (TextView) convertView.findViewById(R.id.myCon),
                    (TextView) convertView.findViewById(R.id.myGoodnum),
                    (TextView) convertView.findViewById(R.id.myResnum),
                    (Button) convertView.findViewById(R.id.deletBtn)

            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ArtListItem artlistitem=(ArtListItem)getItem(position);
        holder.txtArt.setText(artlistitem.getMyTitle());
        holder.txtAut.setText(artlistitem.getMyAut());
        holder.txtCon.setText(artlistitem.getMyCon());
        holder.txtGdnum.setText("GOOD："+artlistitem.getMyGoodnum());
        holder.txtRsnum.setText("RES："+artlistitem.getMyResnum());
        if(nowuser==artlistitem.getMyAut()){
            holder.deletbtn.setText("刪除");
            holder.deletbtn.setOnClickListener(this);
        }else{
            holder.deletbtn.setVisibility(convertView.GONE);
        }
        return convertView;
    }
    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}

