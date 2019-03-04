package graphrecommender.jung.com.graphrecommenderapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyQueue {
    static RequestQueue queue;

    VolleyQueue(Context context){
         queue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getQueue(){
        return queue;
    }
}
