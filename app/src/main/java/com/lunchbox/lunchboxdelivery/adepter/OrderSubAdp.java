package com.lunchbox.lunchboxdelivery.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lunchbox.lunchboxdelivery.R;
import com.lunchbox.lunchboxdelivery.model.SubscribeOrderHistoryItem;
import com.lunchbox.lunchboxdelivery.retrofit.APIClient;
import com.lunchbox.lunchboxdelivery.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSubAdp extends RecyclerView.Adapter<OrderSubAdp.MyViewHolder> {
    private Context mContext;

    private List<SubscribeOrderHistoryItem> itemList;
    private RecyclerTouchListener listener;
    SessionManager sessionManager;

    public interface RecyclerTouchListener {
        public void onOrderItem(String oid);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_rtitle)
        TextView txtRtitle;
        @BindView(R.id.txt_delivery)
        TextView txtDelivery;
        @BindView(R.id.txt_location)
        TextView txtLocation;
        @BindView(R.id.txt_totoal)
        TextView txtTotoal;
        @BindView(R.id.txt_ptitle)
        TextView txtPtitle;
        @BindView(R.id.txt_dates)
        TextView txtDates;
        @BindView(R.id.txt_trackorder)
        TextView txtTrackorder;
        @BindView(R.id.txt_orderrates)
        TextView txtOrderrates;
        @BindView(R.id.lvl_itemclick)
        LinearLayout lvlItemclick;
        @BindView(R.id.txt_dtitle)
        TextView txtDtitle;
        @BindView(R.id.txt_drates)
        TextView txtDrates;
        @BindView(R.id.txt_rrtitle)
        TextView txtRrtitle;
        @BindView(R.id.txt_rrates)
        TextView txtRrates;
        @BindView(R.id.txt_totoalitme)
        TextView txtTotoalitme;
        @BindView(R.id.txt_ordernum)
        TextView txtOrdernum;
        @BindView(R.id.img_icon)
        ImageView imgIcon;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public OrderSubAdp(Context mContext, List<SubscribeOrderHistoryItem> categoryList, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.itemList = categoryList;
        this.listener = listener;
        sessionManager = new SessionManager(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SubscribeOrderHistoryItem item = itemList.get(position);
        holder.txtRtitle.setText("" + item.getRestName());
        holder.txtLocation.setText("" + item.getRestLandmark());
        holder.txtTotoal.setText(sessionManager.getStringData(SessionManager.currncy) + item.getOrderTotal());
        holder.txtPtitle.setText("" + item.getRestSdesc());
        holder.txtDates.setText("" + item.getOrderCompleteDate());
        holder.txtTotoalitme.setText("Days " + item.getTotalOrderItem());
        holder.txtOrdernum.setText("ORDER ID#" + item.getOrderId());
        holder.txtTrackorder.setText(item.getOStatus());
        Glide.with(mContext).load(APIClient.baseUrl + "/" + item.getRestImg()).centerCrop().into(holder.imgIcon);



        holder.lvlItemclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderItem(item.getOrderId());

            }
        });
        holder.txtTrackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.txtOrderrates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

}