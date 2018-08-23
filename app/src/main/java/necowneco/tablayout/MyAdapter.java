package necowneco.tablayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a888888888.sport.R;

import java.util.List;
/**
 * Created by 黃小黃 on 2017/12/19.
 */

public class MyAdapter extends BaseAdapter{
    private LayoutInflater myInflater;
    private List<ArtListItem> artlists;
    private String nowuser;
    private String[] SportICON={"運","氧","走","跑","伏","坐"};//只是對照表;

    public MyAdapter(Context context,List<ArtListItem> theartlist,String nowuserID){
        myInflater=LayoutInflater.from(context);
        this.artlists=theartlist;
        this.nowuser=nowuserID;
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
        TextView txtClass;
        public ViewHolder(TextView thetxtArt,
                          TextView thetxtAut,
                          TextView thetxtCon,
                          TextView thetxtGdnum,
                          TextView thetxtRsnum,
                          TextView thetxtClass){
            this.txtArt = thetxtArt;
            this.txtAut = thetxtAut;
            this.txtCon=thetxtCon;
            this.txtGdnum=thetxtGdnum;
            this.txtRsnum=thetxtRsnum;
            this.txtClass=thetxtClass;
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
                    (TextView) convertView.findViewById(R.id.myClassICON)

            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ArtListItem artlistitem=(ArtListItem)getItem(position);
        holder.txtArt.setText(artlistitem.getMyTitle());
        holder.txtAut.setText(artlistitem.getMyAut());
        holder.txtCon.setText(artlistitem.getMyCon());
        holder.txtGdnum.setText(""+artlistitem.getMyGoodnum());
        holder.txtRsnum.setText(""+(artlistitem.getMyResnum()));
        holder.txtClass.setText(SportICON[artlistitem.getMyClass()]);
        return convertView;
    }
}

