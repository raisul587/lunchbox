package com.lunchbox.lunchboxdelivery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lunchbox.lunchboxdelivery.R;
import com.lunchbox.lunchboxdelivery.adepter.OrderSubAdp;
import com.lunchbox.lunchboxdelivery.model.OrderSubcribe;
import com.lunchbox.lunchboxdelivery.model.SubscribeOrderHistoryItem;
import com.lunchbox.lunchboxdelivery.model.User;
import com.lunchbox.lunchboxdelivery.retrofit.APIClient;
import com.lunchbox.lunchboxdelivery.retrofit.GetResult;
import com.lunchbox.lunchboxdelivery.utils.CustPrograssbar;
import com.lunchbox.lunchboxdelivery.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class OrderSubListActivity extends AppCompatActivity implements GetResult.MyListener, OrderSubAdp.RecyclerTouchListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;

    @BindView(R.id.my_order)
    public RecyclerView myOrder;
    @BindView(R.id.txt_loadmore)
    public TextView txtLoadmore;
    @BindView(R.id.txt_ongoing)
    public TextView txtOngoing;
    @BindView(R.id.txt_complet)
    public TextView txtComplet;
    @BindView(R.id.txt_new)
    public TextView txtNew;
    @BindView(R.id.txt_all)
    public TextView txtAll;
    @BindView(R.id.lvl_notfound)
    public LinearLayout lvlNotfound;
    SessionManager sessionManager;
    CustPrograssbar custPrograssbar;
    User user;
    int count = 1;
    OrderSubAdp orderAdp;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suborder_list);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        custPrograssbar = new CustPrograssbar();
        user = sessionManager.getUserDetails();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrder.setLayoutManager(mLayoutManager);
        status="New";
        getOrders(status);
    }

    private void getOrders(String status) {
        custPrograssbar.prograsscreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rid", user.getId());
            jsonObject.put("did", user.getZoneId());
            jsonObject.put("page", count);
            jsonObject.put("status", status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getOrderSubHistry(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    @OnClick({R.id.img_back, R.id.txt_loadmore, R.id.txt_ongoing, R.id.txt_complet, R.id.txt_new, R.id.txt_all})
    public void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_loadmore:
                getOrders(status);
                break;
            case R.id.txt_ongoing:
                orderHistoryItems = new ArrayList<>();
                txtOngoing.setBackground(getDrawable(R.drawable.border_t1));
                txtOngoing.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtComplet.setBackground(getDrawable(R.drawable.border_t2));
                txtComplet.setTextColor(getResources().getColor(R.color.colorGrey));

                txtNew.setBackground(getDrawable(R.drawable.border_t2));
                txtNew.setTextColor(getResources().getColor(R.color.colorGrey));

                txtAll.setBackground(getDrawable(R.drawable.border_t2));
                txtAll.setTextColor(getResources().getColor(R.color.colorGrey));
                status="Ongoing";
                getOrders(status);


                break;
            case R.id.txt_complet:
                orderHistoryItems = new ArrayList<>();
                txtOngoing.setBackground(getDrawable(R.drawable.border_t2));
                txtOngoing.setTextColor(getResources().getColor(R.color.colorGrey));

                txtComplet.setBackground(getDrawable(R.drawable.border_t1));
                txtComplet.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtNew.setBackground(getDrawable(R.drawable.border_t2));
                txtNew.setTextColor(getResources().getColor(R.color.colorGrey));

                txtAll.setBackground(getDrawable(R.drawable.border_t2));
                txtAll.setTextColor(getResources().getColor(R.color.colorGrey));
                status="Completed";
                getOrders(status);
                break;
            case R.id.txt_new:
                orderHistoryItems = new ArrayList<>();
                txtOngoing.setBackground(getDrawable(R.drawable.border_t2));
                txtOngoing.setTextColor(getResources().getColor(R.color.colorGrey));

                txtComplet.setBackground(getDrawable(R.drawable.border_t2));
                txtComplet.setTextColor(getResources().getColor(R.color.colorGrey));

                txtNew.setBackground(getDrawable(R.drawable.border_t1));
                txtNew.setTextColor(getResources().getColor(R.color.colorPrimary));

                txtAll.setBackground(getDrawable(R.drawable.border_t2));
                txtAll.setTextColor(getResources().getColor(R.color.colorGrey));
                status="New";
                getOrders(status);
                break;
            case R.id.txt_all:
                orderHistoryItems = new ArrayList<>();
                txtOngoing.setBackground(getDrawable(R.drawable.border_t2));
                txtOngoing.setTextColor(getResources().getColor(R.color.colorGrey));

                txtComplet.setBackground(getDrawable(R.drawable.border_t2));
                txtComplet.setTextColor(getResources().getColor(R.color.colorGrey));

                txtNew.setBackground(getDrawable(R.drawable.border_t2));
                txtNew.setTextColor(getResources().getColor(R.color.colorGrey));

                txtAll.setBackground(getDrawable(R.drawable.border_t1));
                txtAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                status="All";
                getOrders(status);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    List<SubscribeOrderHistoryItem> orderHistoryItems = new ArrayList<>();

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closeprograssbar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                OrderSubcribe order = gson.fromJson(result.toString(), OrderSubcribe.class);
                if (order.getResult().equalsIgnoreCase("true")) {
                    if (orderHistoryItems.size() == 0) {

                        orderHistoryItems = order.getSubscribeOrderHistory();
                        if (order.getSubscribeOrderHistory().size() < 4) {
                            txtLoadmore.setVisibility(View.GONE);


                        }
                        if(order.getSubscribeOrderHistory().size()==0){
                            lvlNotfound.setVisibility(View.VISIBLE);
                        }else {
                            lvlNotfound.setVisibility(View.GONE);

                        }
                        orderAdp = new OrderSubAdp(OrderSubListActivity.this, orderHistoryItems, this);
                        myOrder.setAdapter(orderAdp);
                    } else if (order.getSubscribeOrderHistory().size() != 0) {
                        orderHistoryItems.addAll(order.getSubscribeOrderHistory());
                        orderAdp.notifyDataSetChanged();
                        lvlNotfound.setVisibility(View.GONE);

                    } else {
                        txtLoadmore.setVisibility(View.GONE);
                    }

                }
            }
        } catch (Exception e) {
            e.toString();
        }
    }


    @Override
    public void onOrderItem(String oid) {
        startActivity(new Intent(this, OrderSubActivity.class).putExtra("oid", oid));

    }
}