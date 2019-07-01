package com.example.wakisac.geocoder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class physical_address extends AppCompatActivity {
    Button button;
    EditText address;
    String getAddress;
    String url ="http://192.168.43.10/connect2.php";
    AlertDialog.Builder builder;
    ProgressBar spinner;
    ProgressDialog dialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button =(Button)findViewById(R.id.goLocation);
        address = (EditText)findViewById(R.id.physicalAddress);
        dialogue = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
            }
        });


    }
    public void result(){

        //   getLocation();
        getAddress = address.getText().toString();

        //validate user input

        if (TextUtils.isEmpty(getAddress)) {
            Toast.makeText(this, "Enter adress please!", Toast.LENGTH_SHORT).show();
        }
      else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Proceed to get location to :");
            builder1.setMessage(getAddress);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogue.setMessage("Loading...");
                            dialogue.show();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equalsIgnoreCase("success")){
                                                dialogue.dismiss();
                                                getLocation();
                                            }else {
                                                noExist();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(physical_address.this, "Connection Error!!", Toast.LENGTH_SHORT).show();
                                    dialogue.dismiss();
                                    error.printStackTrace();

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("address", getAddress);
                                    return params;
                                }
                            };
                            Mysingleton.getmInstance(physical_address.this).addToRequestque(stringRequest);


                        }
                    });
            builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }
    public void getLocation(){
        //go to maps activity and push the values there
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("address", getAddress);
        startActivity(intent);
    }
    public void noExist(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Location Error");
        builder1.setMessage("Sorry the area Does not Exist..\n\nType correctly & try again !!");
        builder1.setCancelable(true);
        builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogue.dismiss();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
