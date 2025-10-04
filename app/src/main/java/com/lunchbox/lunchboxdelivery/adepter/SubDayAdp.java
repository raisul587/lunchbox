package com.lunchbox.lunchboxdelivery.adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lunchbox.lunchboxdelivery.R;
import com.lunchbox.lunchboxdelivery.model.OrderItemsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubDayAdp extends RecyclerView.Adapter<SubDayAdp.MyViewHolder> {

    private List<OrderItemsItem> list;
    private RecyclerTouchListener listener;
    private Context context;

    public interface RecyclerTouchListener {
        public void onDayItem(OrderItemsItem item, int position);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        public TextView txtDate;
        @BindView(R.id.txt_day)
        public TextView txtDay;
        @BindView(R.id.txt_catergory)
        public TextView txtCatergory;
        @BindView(R.id.lvl_main)
        public LinearLayout lvlMain;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public SubDayAdp(Context context, List<OrderItemsItem> daysList, final RecyclerTouchListener listener) {
        this.context = context;
        this.list = daysList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subdays, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderItemsItem item = list.get(position);
        holder.txtDate.setText("" + parseDateToddMMyyyy(item.getItemDate()));
        holder.txtDay.setText("" + item.getItemDay());
        holder.txtCatergory.setText("" + item.getCatName());

        switch (item.getStatus()) {
            case "Pending":
                holder.lvlMain.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorWhite));
                holder.txtCatergory.setTextColor(context.getResources().getColor(R.color.colorBalck));
                holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorBalck));
                holder.txtDate.setTextColor(context.getResources().getColor(R.color.colorBalck));
                break;
            case "Completed":
                holder.lvlMain.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorGreen));
                holder.txtCatergory.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.txtDate.setTextColor(context.getResources().getColor(R.color.colorWhite));
                break;
            case "Cancelled":
                holder.lvlMain.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorred));
                holder.txtCatergory.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.txtDay.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.txtDate.setTextColor(context.getResources().getColor(R.color.colorWhite));
                break;
        }
        holder.lvlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDayItem(item,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}