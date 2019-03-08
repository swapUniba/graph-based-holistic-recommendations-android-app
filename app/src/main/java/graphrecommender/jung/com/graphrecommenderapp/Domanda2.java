package graphrecommender.jung.com.graphrecommenderapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Domanda2 extends Fragment {

    static ArrayList<Spinner> spins;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.domanda2, container, false);

        spins = new ArrayList();

        ArrayAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(retView.getContext(),
                R.array.categorie, android.R.layout.simple_spinner_item);
        spinnerAdapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_item);

        for (int i = 0; i < 28; i++) {
            Spinner spin = retView.findViewById(getResources().getIdentifier("spin" + i, "id", MainActivity.class.getPackage().getName()));
            spin.setAdapter(spinnerAdapter2);
            spins.add(spin);
        }

        return retView;
    }

    public static ArrayList<Spinner> getSpins(){
        return spins;
    }

}