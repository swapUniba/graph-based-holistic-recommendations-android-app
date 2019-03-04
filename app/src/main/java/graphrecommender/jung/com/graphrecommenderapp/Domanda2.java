package graphrecommender.jung.com.graphrecommenderapp;

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

import java.util.ArrayList;
import java.util.HashMap;

public class Domanda2 extends Fragment {

    Spinner spinner1h;
    Spinner spinner2h;
    Button addH;
    TextView historyTV;
    static HashMap<String, ArrayList<String>> history;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.domanda2, container, false);



        spinner1h =retView.findViewById(R.id.spinner1h);
        spinner2h =retView.findViewById(R.id.spinner2h);
        addH =retView.findViewById(R.id.addH);
        historyTV =retView.findViewById(R.id.history);
        history = new HashMap();


        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(retView.getContext(),
                R.array.contesti, android.R.layout.simple_spinner_item);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(retView.getContext(),
                R.array.categorie, android.R.layout.simple_spinner_item);
        spinnerAdapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner1h.setAdapter(spinnerAdapter);
        spinner2h.setAdapter(spinnerAdapter2);

        addH= retView.findViewById(R.id.addH);

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

        return retView;
    }


    public void addHistory(){
        String toW ="Preferenze : {";
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

    public static HashMap<String, ArrayList<String>> getMappa(){
        return history;
    }
}