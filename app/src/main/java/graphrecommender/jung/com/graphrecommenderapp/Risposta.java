package graphrecommender.jung.com.graphrecommenderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Risposta extends Fragment {

    static JSONObject risposta_json;
    TextView risposta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View retView = inflater.inflate(R.layout.risposta, container, false);

        risposta=retView.findViewById(R.id.risposta);


        HashMap<String, Integer> mappa = new HashMap<>();

        try {
            JSONObject json = risposta_json.getJSONObject("recommendation");
            Iterator<String> it = json.keys();

            while (it.hasNext())
            {
                String key = it.next();
                mappa.put(key, json.getInt(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Object[] ordinati = mappa.entrySet().stream()
                .sorted((k1, k2) -> k1.getValue().compareTo(k2.getValue())).toArray();

        String write="";
        for (int i =0 ; i< ordinati.length ; i++) {
            String nome = ordinati[i].toString().split("=")[0];
            write=write+(i+1)+". "+ nome +"\n";
        }

        risposta.setText(write);
        return retView;
    }

    public static void setRisposta(JSONObject json){
        risposta_json = json;
    }
}