package graphrecommender.jung.com.graphrecommenderapp;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;

public class Page1 extends AppCompatActivity {

    Button send;
    Button addC;
    Button addH;
    Spinner spinner;
    Spinner spinner1h;
    Spinner spinner2h;
    TextView contesto;
    TextView historyTV;
    TextView response;
    EditText user;
    ArrayList<String> contesti;
    HashMap<String, ArrayList<String>> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send=findViewById(R.id.send);
        contesto=findViewById(R.id.contesto);
        historyTV=findViewById(R.id.history);
        response=findViewById(R.id.response);
        user=findViewById(R.id.user_input);
        contesti = new ArrayList();
        history = new HashMap();



        spinner = findViewById(R.id.spinner_contesti);
        spinner1h =findViewById(R.id.spinner1h);
        spinner2h =findViewById(R.id.spinner2h);


        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.contesti, android.R.layout.simple_spinner_item);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(this,
                R.array.categorie, android.R.layout.simple_spinner_item);
        spinnerAdapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(spinnerAdapter);
        spinner1h.setAdapter(spinnerAdapter);
        spinner2h.setAdapter(spinnerAdapter2);

        addC= findViewById(R.id.addC);
        addH= findViewById(R.id.addH);

        addC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contesti.add(spinner.getSelectedItem().toString());
                addContesto();
            }
        });

        addH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(history.containsKey(spinner1h.getSelectedItem().toString())) history.get(spinner1h.getSelectedItem().toString()).add(spinner2h.getSelectedItem().toString());
                else {
                    ArrayList<String> list = new ArrayList();
                    list.add(spinner2h.getSelectedItem().toString());
                    history.put(spinner1h.getSelectedItem().toString(), list);
                }
                addHistory();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","SendClick");
                try {
                    sendPost();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void addContesto(){
        String toW ="contesto: [";
        for (String s: contesti) {
            toW=toW.concat(s+",");
        }
        toW=toW.substring(0,toW.length()-1);
        toW=toW+"]";
        contesto.setText(toW);
    }

    public void addHistory(){
        String toW ="history: {";
        for (String s: history.keySet()) {
            toW=toW+s+"->[";
            for (int i = 0; i < history.get(s).size(); i++) {
                toW=toW+history.get(s).get(i)+",";
            }
            toW=toW.substring(0,toW.length()-1);
            toW=toW+"]; ";
        }
        toW=toW.substring(0,toW.length()-2);
        toW=toW+"}";
        historyTV.setText(toW);
    }

    public void sendPost() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://graph-recommender.herokuapp.com/recommendation/post";

        JSONObject json = new JSONObject();
        json.put("user",user.getText());
        JSONArray array=new JSONArray();
        for (String s:contesti) {
            array.put(s);
        }
        json.put("contesto",array);
        JSONObject mappa=new JSONObject();
        for (String s: history.keySet()) {
            JSONArray arr = new JSONArray();
            for (String s1: history.get(s)) {
                arr.put(s1);
            }
            mappa.put(s,arr);
        }
        json.put("history", mappa);

        Log.d("TAG",json.toString());

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        // Display the first 500 characters of the response string.
                        response.setText(res.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                response.setText("ServiceOffline");
            }
        });
        queue.add(jsonRequest);
    }
}
