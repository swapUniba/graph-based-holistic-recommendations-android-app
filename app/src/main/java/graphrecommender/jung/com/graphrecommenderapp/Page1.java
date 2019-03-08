package graphrecommender.jung.com.graphrecommenderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Page1 extends AppCompatActivity {

    Button next;
    ProgressDialog progress;
    ArrayList contesti;
    String[] cont;
    HashMap<String, ArrayList<String>> history = new HashMap<>();
    EditText user;
    int posizione=0;
    Fragment fragment;
    String nome;
    TextView titolo;
    ArrayList<Spinner> spins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        fragment = new Domanda1();
        setDefaultFragment(fragment);

        cont = getResources().getStringArray(R.array.contesti);
        titolo = findViewById(R.id.titolo);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    toNext();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void toNext() throws JSONException {

        Log.d("TAG",""+ posizione);
        boolean check=false;
        switch (posizione) {
            case 0:
                user = findViewById(R.id.user_input);
                if(!user.getText().toString().equals("")) {nome = user.getText().toString();
                check= true;}
                fragment = new Domanda2();
                break;
            case 1:
                if(spinCheck()){
                    mapHistory();
                    Log.d("TAG", history.toString());
                    next.setText("Invia Richiesta");
                    check= true;
                }
                fragment = new Domanda3();
                break;
            case 2:
                contesti = Domanda3.getContesti();
                if(!contesti.isEmpty()) check= true;
                break;
        }


        if(check) {
            if(posizione!=2){
            posizione++;
            replaceFragment(fragment);}
            else{

                sendPost();

                progress = new ProgressDialog(this);
                progress.setTitle("Caricamento");
                progress.setMessage("Il server sta elaborando la richiesta...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
            }
        }
        else Toast.makeText(this,"Compila almeno un campo", Toast.LENGTH_SHORT).show();
    }


    public void sendPost() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://graph-recommender.herokuapp.com/recommendation/post";

        JSONObject json = new JSONObject();
        json.put("user",user.getText());
        JSONArray array=new JSONArray();
        for (String s:Domanda3.getContesti()) {
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
                        Risposta.setRisposta(res);
                        fragment = new Risposta();
                        titolo.setText("Penso di conoscerti abbastanza");
                        next.setText("Home");
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Page1.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
                        progress.dismiss();
                        replaceFragment(fragment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(Page1.this,"Errore di comunicazione con il server", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Page1.this, MainActivity.class);
                startActivity(i);
            }
        });
        queue.add(jsonRequest);
    }


    private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.dynamic_fragment, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }



    private boolean spinCheck(){
        boolean toRet= false;
        spins = Domanda2.getSpins();
        for (int i = 0; i < 28; i++) {
            if(spins.get(i).getSelectedItemPosition() != 0) toRet=true;
        }
        return toRet;
    }

    private void mapHistory(){
        for (int i = 0; i < 28; i++) {
               if(spins.get(i).getSelectedItemPosition()!=0){
                   if(history.containsKey(cont[i/2])){
                       history.get(cont[i/2]).add(spins.get(i).getSelectedItem().toString());
                   }
                   else{
                       ArrayList<String> list = new ArrayList<>();
                       list.add(spins.get(i).getSelectedItem().toString());
                       history.put(cont[i/2], list);
                   }
               }
        }
    }
}
