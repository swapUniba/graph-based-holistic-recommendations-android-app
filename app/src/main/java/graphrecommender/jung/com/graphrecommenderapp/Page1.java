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
    TextView contesto;
    TextView historyTV;
    EditText user;
    int posizione=0;
    Fragment fragment;
    String nome;
    TextView titolo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        //TODO
        //getSupportActionBar().
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = new Domanda1();
        setDefaultFragment(fragment);

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
                historyTV = findViewById(R.id.history);
                if(!historyTV.getText().toString().equals("Preferenze : ")){
                    next.setText("Invia Richiesta");
                    check= true;}
                fragment = new Domanda3();
                break;
            case 2:
                contesto = findViewById(R.id.contesto);
                if(!contesto.getText().toString().equals("Contesto : ")) check= true;
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
        else Toast.makeText(this,"Campo Obbligatorio", Toast.LENGTH_SHORT).show();
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
        for (String s: Domanda2.getMappa().keySet()) {
            JSONArray arr = new JSONArray();
            for (String s1: Domanda2.getMappa().get(s)) {
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
                Toast.makeText(Page1.this,"Errore di comunicazione con il server", Toast.LENGTH_SHORT).show();
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




/*
    private void toNext(int posizione) {

        if (View != null) ViewHolder.removeView(View);

            int domandaID=0;
            switch (posizione) {
                case 0:
                    domandaID = R.layout.domanda1;
                    posizione++;
                    break;
                case 1:
                    domandaID = R.layout.domanda2;
                    posizione++;
                    break;
                case 2:
                    domandaID = R.layout.domanda3;
                    posizione++;
                    break;
            }

            View = getLayoutInflater().inflate(domandaID, null);
            ViewHolder.addView(View);
    }

    private void domanda12(){
        if(user.getText()!=null){
        nome = user.getText().toString();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Domanda2 fragment = new Domanda2();
            //fragmentTransaction.add(R.id.viewer, fragment);
            fragmentTransaction.commit();

            posizione++;
        }
        else Toast.makeText(this,"Campo Obbligatorio", Toast.LENGTH_SHORT).show();
    }

*/
    /*

        <fragment android:name="graphrecommender.jung.com.graphrecommenderapp.Domanda1"
        android:id="@+id/viewer"
        android:layout_marginTop="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/titolo"/>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        next=findViewById(R.id.next);
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

        next.setOnClickListener(new View.OnClickListener() {
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
*/

}
