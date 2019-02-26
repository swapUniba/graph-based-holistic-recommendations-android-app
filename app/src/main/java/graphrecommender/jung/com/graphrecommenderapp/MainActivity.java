package graphrecommender.jung.com.graphrecommenderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView centro;
    TextView online;
    Button btn;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centro = findViewById(R.id.main_text);
        online = findViewById(R.id.online);
        btn = findViewById(R.id.btn);
        queue = Volley.newRequestQueue(this);

        centro.setText("Ciao");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","Click");
                Log.d("TAG",online.getText().toString());
                if(online.getText().toString().equals("Service online")) startPage();
                else Toast.makeText(MainActivity.this,"Can't start service is offline", Toast.LENGTH_SHORT).show();
            }
        });

        checkOnline();

    }

    public void checkOnline(){
        Log.d("TAG","checkOnline");

        final String url = "https://graph-recommender.herokuapp.com/recommendation/ping";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        online.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                centro.setText("ServiceOffline");
            }
        });
        queue.add(stringRequest);
    }


    public void startPage(){
        Intent i = new Intent(this, Page1.class);
        startActivity(i);
    }

}