package com.hp.mss.print.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hp.mss.print.R;
import com.hp.mss.print.helper.AppConfig;
import com.hp.mss.print.helper.NetworkUtil;
import com.hp.mss.print.helper.SQLiteHandler;
import com.hp.mss.print.helper.SessionManager;
import com.hp.mss.print.helper.TAG;

import org.json.JSONException;
import org.json.JSONObject;

import static com.hp.mss.print.helper.Utility.SHA1;

public class LoginActivity extends AppCompatActivity {

    public static Button btnSubmit;
    private EditText txtKey;
    private ProgressDialog pDialog;
    private SessionManager session;

    public static final String TAG_REQUEST = "TRANVANHOP";
    StringRequest stringRequest;
    RequestQueue mRequestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppConfig.DEVICE_ID = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);

        txtKey = (EditText) findViewById(R.id.txtKey);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String key = null;
                key = txtKey.getText().toString().trim().toUpperCase();
                if (!key.isEmpty())
                    active(key);
                else
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Key !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG_REQUEST);
        }

        hideDialog();
    }

    @Override
    protected void onPause () {
        super.onPause();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG_REQUEST);
        }

        hideDialog();
    }

    private void active(String key) {
        mRequestQueue = Volley.newRequestQueue(this);
        String paramTemp = TAG.DEVICE_ID + "=" + AppConfig.DEVICE_ID + "&" + TAG.KEY + "=" + key;
        String hash = SHA1(paramTemp + "&" + TAG.SECURITY + "=" + AppConfig.HASH);
        String url = AppConfig.URL_LOGIN + "?" + paramTemp + "&" + TAG.SECURITY + "=" + hash;

        showDialog();

        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response.trim());
                            boolean error = jObj.getBoolean(TAG.ERROR);

                            if (!error) {
                                session.setLogin(true);
                                Toast.makeText(getApplicationContext(), "Active thành công !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                String errorMsg = jObj.getString("error_message");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                if(NetworkUtil.getConnectivityStatus(getApplicationContext()) == NetworkUtil.TYPE_NOT_CONNECTED)
                    Toast.makeText(getApplicationContext(),
                        "Vui lòng kiểm tra lại mạng !", Toast.LENGTH_LONG)
                        .show();
                else
                    Toast.makeText(getApplicationContext(),
                            "Vui thử lại !", Toast.LENGTH_LONG)
                            .show();
            }
        });
        if(mRequestQueue != null)
            mRequestQueue.cancelAll(TAG_REQUEST);

        stringRequest.setTag(TAG_REQUEST);
        mRequestQueue.add(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
        }

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}