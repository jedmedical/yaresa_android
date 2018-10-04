package net.apps.jed.yaresa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import io.rmiri.buttonloading.ButtonLoading;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SigninActivity extends AppCompatActivity {
   ButtonLoading buttonLoading;
   EditText phone, pin;
    RequestQueue queue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        buttonLoading = (ButtonLoading)findViewById(R.id.loginbut);
        phone = (EditText)findViewById(R.id.phone);
        pin = (EditText)findViewById(R.id.pass);
        queue =  Volley.newRequestQueue(getApplicationContext());

        buttonLoading.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {

                if(phone.getText().toString().isEmpty() || pin.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill form completely",Toast.LENGTH_LONG).show();
                    buttonLoading.setProgress(false);
                }else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    //signin();
                }
            }

            @Override
            public void onStart() {
                //...
            }

            @Override
            public void onFinish() {
                //...
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    public  void  signin(){

        String url = SAppUtil.baseUrl+"/signin";
        Log.d("Url",url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            Log.d("Response", response);
                            buttonLoading.setProgress(false);
                            JSONObject login_detail = new JSONObject(response);

                            Log.d("Responsed", login_detail.getString("status"));


                            if(login_detail.opt("status").toString().equals("ok")){
                                PrefConstants.putAppPrefString(getApplicationContext(),"user_id",login_detail.getString("user_id"));
                                //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            }
                            else if(login_detail.opt("status").toString().equals("error")){
                                Toast.makeText(getApplicationContext(),login_detail.getString("result"),Toast.LENGTH_SHORT).show();

                            }


                        }
                        catch (JSONException v){
                            v.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();


                params.put("username", phone.getText().toString());
                params.put("password", pin.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

    }


}
