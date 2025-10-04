package com.lunchbox.lunchboxdelivery.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lunchbox.lunchboxdelivery.R;
import com.lunchbox.lunchboxdelivery.adepter.SubDayAdp;
import com.lunchbox.lunchboxdelivery.model.OrderItemsItem;
import com.lunchbox.lunchboxdelivery.model.OrderSubcribeInfo;
import com.lunchbox.lunchboxdelivery.model.RestResponse;
import com.lunchbox.lunchboxdelivery.model.User;
import com.lunchbox.lunchboxdelivery.retrofit.APIClient;
import com.lunchbox.lunchboxdelivery.retrofit.GetResult;
import com.lunchbox.lunchboxdelivery.utils.CustPrograssbar;
import com.lunchbox.lunchboxdelivery.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class OrderSubActivity extends AppCompatActivity implements GetResult.MyListener, SubDayAdp.RecyclerTouchListener {


    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.txt_orderid)
    public TextView txtOrderid;
    @BindView(R.id.txt_rtitle)
    public TextView txtRtitle;
    @BindView(R.id.txt_rlocation)
    public TextView txtRlocation;
    @BindView(R.id.txt_customer)
    public TextView txtCustomer;
    @BindView(R.id.txt_caddress)
    public TextView txtCaddress;
    @BindView(R.id.txt_deliveryboy)
    public TextView txtDeliveryboy;
    @BindView(R.id.txt_completdate)
    public TextView txtCompletdate;
    @BindView(R.id.lvl_completdate)
    public LinearLayout lvlCompletdate;
    @BindView(R.id.img_select)
    public ImageView imgSelect;
    @BindView(R.id.txt_title)
    public TextView txtTitle;
    @BindView(R.id.txt_dsct)
    public TextView txtDsct;
    @BindView(R.id.txt_more)
    public TextView txtMore;
    @BindView(R.id.txt_prize)
    public TextView txtPrize;
    @BindView(R.id.lvl_itemclick)
    public LinearLayout lvlItemclick;
    @BindView(R.id.lvl_accept_reject)
    public LinearLayout lvlAcceptReject;
    @BindView(R.id.lvl_comple)
    public LinearLayout lvlComple;
    @BindView(R.id.txt_pmethod)
    public TextView txtPmethod;
    @BindView(R.id.txt_comple)
    public TextView txtComple;
    @BindView(R.id.txt_accept)
    public TextView txtAccept;
    @BindView(R.id.txt_reject)
    public TextView txtReject;
    @BindView(R.id.recycler_days)
    public RecyclerView recyclerDays;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    String oID;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suborder);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
        custPrograssbar = new CustPrograssbar();
        oID = getIntent().getStringExtra("oid");
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDays.setLayoutManager(mLayoutManager);
        getOrdersHistry();
    }

    private void getOrdersHistry() {
        custPrograssbar.prograsscreate(OrderSubActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderid", oID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<JsonObject> call = APIClient.getInterface().getSubOrderDetalis(bodyRequest);
        GetResult getResult = new GetResult();
        getResult.setMyListener(this);
        getResult.callForLogin(call, "1");

    }

    private void orderStatus(String status, String itemId, String date, String comment) {
        custPrograssbar.prograsscreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rid", user.getId());
            jsonObject.put("oid", oID);
            jsonObject.put("status", status);
            jsonObject.put("req_date", date);
            jsonObject.put("item_id", itemId);
            jsonObject.put("reject_reason", comment);
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getOstatuss((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    OrderSubcribeInfo orderDetail;

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closeprograssbar();
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                orderDetail = gson.fromJson(result.toString(), OrderSubcribeInfo.class);
                if (orderDetail.getResult().equalsIgnoreCase("true")) {
                    txtOrderid.setText("ORDER ID# " + orderDetail.getSubscribeOrderInfo().getOrderId());
                    txtRtitle.setText("" + orderDetail.getSubscribeOrderInfo().getRestName());
                    txtRlocation.setText("" + orderDetail.getSubscribeOrderInfo().getRestAddress());
                    txtCustomer.setText("" + orderDetail.getSubscribeOrderInfo().getAddressType());
                    txtCaddress.setText("" + orderDetail.getSubscribeOrderInfo().getCustAddress());

                    txtTitle.setText("" + orderDetail.getSubscribeOrderInfo().getPackTitle());
                    txtPmethod.setText("" + orderDetail.getSubscribeOrderInfo().getPMethodName());
                    txtPrize.setText(sessionManager.getStringData(SessionManager.currncy) + orderDetail.getSubscribeOrderInfo().getOrderTotal());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtDsct.setText(Html.fromHtml(orderDetail.getSubscribeOrderInfo().getPackDescription(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        txtDsct.setText(Html.fromHtml(orderDetail.getSubscribeOrderInfo().getPackDescription()));
                    }

                    Glide.with(this).load(APIClient.baseUrl + "/" + orderDetail.getSubscribeOrderInfo().getPackImg()).centerCrop().into(imgSelect);

                    txtMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            detailPlan(OrderSubActivity.this, orderDetail.getSubscribeOrderInfo().getPackDescription());
                        }
                    });
                    if (orderDetail.getSubscribeOrderInfo().getOStatus().equalsIgnoreCase("Pending")) {
                        lvlAcceptReject.setVisibility(View.VISIBLE);
                    } else if (orderDetail.getSubscribeOrderInfo().getOStatus().equalsIgnoreCase("Processing")) {
                        lvlAcceptReject.setVisibility(View.GONE);
                        lvlComple.setVisibility(View.VISIBLE);
                    }
                    SubDayAdp subDayAdp = new SubDayAdp(this, orderDetail.getSubscribeOrderInfo().getOrderItems(), this);
                    recyclerDays.setAdapter(subDayAdp);
                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result.toString(), RestResponse.class);
                if (response.getResult().equalsIgnoreCase("true")) {
                    recreate();
                } else {
                    Toast.makeText(OrderSubActivity.this, response.getResponseMsg(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e("Error", "-->" + e.toString());
        }

    }


    @OnClick({R.id.img_back, R.id.txt_accept, R.id.txt_reject, R.id.lvl_comple})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_accept:
                orderStatus("accept", "0", "0", "");
                break;
            case R.id.txt_reject:
                orderStatus("reject", "0", "0", "");

                break;
            case R.id.lvl_comple:
                orderStatus("complete", "0", "0", "");

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void detailPlan(Context context, String s) {


        Activity activity = (Activity) context;
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        View rootView = activity.getLayoutInflater().inflate(R.layout.sdcp_layout, null);
        mBottomSheetDialog.setContentView(rootView);

        TextView txtDscp = rootView.findViewById(R.id.txt_dscp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtDscp.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtDscp.setText(Html.fromHtml(s));
        }


        mBottomSheetDialog.show();


    }

    @Override
    public void onDayItem(OrderItemsItem item, int position) {

        if (orderDetail.getSubscribeOrderInfo().getOStatus().equalsIgnoreCase("Processing") && item.getStatus().equalsIgnoreCase("Pending")) {
            bottonOrderMakeDecision(item);

        }
    }

    public void bottonOrderMakeDecision(OrderItemsItem item) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.daycomplet_layout, null);
        mBottomSheetDialog.setContentView(sheetView);

        TextView txtCancel = sheetView.findViewById(R.id.txt_cancel);
        TextView txtComplet = sheetView.findViewById(R.id.txt_complet);
        EditText edUsername = sheetView.findViewById(R.id.ed_username);
        TextView txtDate = sheetView.findViewById(R.id.txt_date);
        txtDate.setText("" + parseDateToddMMyyyy(item.getItemDate()));

        txtComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderStatus("complete_date", item.getItemId(), item.getItemDate(), "");
                mBottomSheetDialog.cancel();

            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edUsername.getText().toString())) {
                    orderStatus("cancell_date", item.getItemId(), item.getItemDate(), edUsername.getText().toString());

                } else {
                    edUsername.setError("Cancel Reason");

                }
                mBottomSheetDialog.cancel();
            }
        });
        mBottomSheetDialog.show();


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