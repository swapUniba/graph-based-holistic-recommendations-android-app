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

public class Domanda3 extends Fragment {

    Button addC;
    Spinner spinner;
    static ArrayList<String> contesti;
    TextView contesto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.domanda3, container, false);

        contesto=retView.findViewById(R.id.contesto);
        contesti = new ArrayList();
        spinner = retView.findViewById(R.id.spinner_contesti);



        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(retView.getContext(),
                R.array.contesti, android.R.layout.simple_spinner_item);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(spinnerAdapter);

        addC= retView.findViewById(R.id.addC);

        addC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contesti.add(spinner.getSelectedItem().toString());
                addContesto();
            }
        });

        return retView;
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

    public static ArrayList<String> getContesti(){
        return contesti;
    }
}