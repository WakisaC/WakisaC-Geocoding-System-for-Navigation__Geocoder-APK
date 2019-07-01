package com.example.wakisac.geocoder;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {

    private static  Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private Mysingleton(Context context){

        ////get request queue from constructor
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    ///// method for getting the request quee


    public RequestQueue getRequestQueue() {

        if (requestQueue==null){

            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Mysingleton getmInstance(Context context){
        //check if instance exists

        if (mInstance==null){
            mInstance= new Mysingleton(context);
        }
        return  mInstance;
    }


    public<T> void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }

}


