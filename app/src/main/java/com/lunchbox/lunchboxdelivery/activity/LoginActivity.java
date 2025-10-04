package com.lunchbox.lunchboxdelivery.activity;

import static com.lunchbox.lunchboxdelivery.utils.SessionManager.currncy;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lunchbox.lunchboxdelivery.R;
import com.lunchbox.lunchboxdelivery.model.LoginUser;
import com.lunchbox.lunchboxdelivery.retrofit.APIClient;
import com.lunchbox.lunchboxdelivery.retrofit.GetResult;
import com.lunchbox.lunchboxdelivery.utils.CustPrograssbar;
import com.lunchbox.lunchboxdelivery.utils.SessionManager;
import com.lunchbox.lunchboxdelivery.utils.Utiles;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements GetResult.MyListener {


    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (sessionManager.getBooleanData("rlogin")) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_loginnow)
    public void onClick() {
        bottonLogin();
    }

    public void bottonLogin() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View rootView = getLayoutInflater().inflate(R.layout.login_layout, null);
        mBottomSheetDialog.setContentView(rootView);
        edUsername = rootView.findViewById(R.id.ed_username);
        edPassword = rootView.findViewById(R.id.ed_password);
        chkRemember = rootView.findViewById(R.id.chk_remember);
        TextView txtLogin = rootView.findViewById(R.id.txt_login);
        txtLogin.setOnClickListener(view -> {
            if (validation()) {
                loginUser();
            }
        });
        mBottomSheetDialog.show();


    }

    CheckBox chkRemember;
    TextInputEditText edUsername;
    TextInputEditText edPassword;

    private void loginUser() {
        custPrograssbar.prograsscreate(LoginActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edUsername.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            jsonObject.put("imei", Utiles.getIMEI(LoginActivity.this));
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getLogin((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.closeprograssbar();
            Gson gson = new Gson();
            LoginUser response = gson.fromJson(result.toString(), LoginUser.class);
            Toast.makeText(LoginActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
            if (response.getResult().equals("true")) {
                JSONObject tags = new JSONObject();
                tags.put("riderid", response.getUser().getId());
                tags.put("dzoneid", response.getUser().getZoneId());
                OneSignal.sendTags(tags);
                sessionManager.setUserDetails(response.getUser());
                sessionManager.setStringData(currncy, response.getCurrency());
                if (response.getUser().getStatus().equalsIgnoreCase("1")) {
                    sessionManager.setBooleanData("status", true);

                } else {
                    sessionManager.setBooleanData("status", false);

                }
                if (chkRemember.isChecked()) {
                    sessionManager.setBooleanData("rlogin", true);
                }
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        } catch (Exception e) {
            Log.e("error", " --> " + e.toString());
        }
    }

    public boolean validation() {
        if (edUsername.getText().toString().isEmpty()) {
            edUsername.setError("Enter Email");
            return false;
        }
        if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Password");
            return false;
        }
        return true;
    }
}
