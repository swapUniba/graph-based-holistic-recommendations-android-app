package graphrecommender.jung.com.graphrecommenderapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Domanda3 extends Fragment {

    static ArrayList<String> contesti = new ArrayList<String>();
    ToggleButton set1;
    ToggleButton set2;
    ToggleButton comp1;
    ToggleButton comp2;
    ToggleButton comp3;
    ToggleButton um1;
    ToggleButton um2;
    ToggleButton att1;
    ToggleButton att2;
    ToggleButton cond1;
    ToggleButton cond2;
    ToggleButton tem1;
    ToggleButton tem2;
    ToggleButton tem3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.domanda3, container, false);

        set1= retView.findViewById(R.id.set1);
        set2= retView.findViewById(R.id.set2);
        comp1= retView.findViewById(R.id.comp1);
        comp2= retView.findViewById(R.id.comp2);
        comp3= retView.findViewById(R.id.comp3);
        um1= retView.findViewById(R.id.um1);
        um2= retView.findViewById(R.id.um2);
        att1= retView.findViewById(R.id.att1);
        att2= retView.findViewById(R.id.att2);
        cond1= retView.findViewById(R.id.cond1);
        cond2= retView.findViewById(R.id.cond2);
        tem1= retView.findViewById(R.id.tem1);
        tem2= retView.findViewById(R.id.tem2);
        tem3= retView.findViewById(R.id.tem3);

        ArrayList<ToggleButton> row1 = new ArrayList<>(Arrays.asList(set1,set2));
        ArrayList<ToggleButton> row2 = new ArrayList<>(Arrays.asList(comp1,comp2,comp3));
        ArrayList<ToggleButton> row3 = new ArrayList<>(Arrays.asList(um1,um2));
        ArrayList<ToggleButton> row4 = new ArrayList<>(Arrays.asList(att1,att2));
        ArrayList<ToggleButton> row5 = new ArrayList<>(Arrays.asList(cond1,cond2));
        ArrayList<ToggleButton> row6 = new ArrayList<>(Arrays.asList(tem1,tem2,tem3));

        ArrayList<ArrayList> rows = new ArrayList<>(Arrays.asList(row1,row2,row3,row4,row5,row6));


        HashMap<ArrayList, String[]> map = new HashMap();
        map.put(row1,getResources().getStringArray(R.array.contesti1));
        map.put(row2,getResources().getStringArray(R.array.contesti2));
        map.put(row3,getResources().getStringArray(R.array.contesti3));
        map.put(row4,getResources().getStringArray(R.array.contesti4));
        map.put(row5,getResources().getStringArray(R.array.contesti5));
        map.put(row6,getResources().getStringArray(R.array.contesti6));

        for (ArrayList<ToggleButton> row : rows) {
            for (ToggleButton btn: row) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (ToggleButton b: row) {
                            b.setChecked(false);
                            remContesto(map.get(row)[row.indexOf(b)]);
                        }
                        btn.setChecked(true);
                        addContesto(map.get(row)[row.indexOf(btn)]);
                    }
                });
            }
        }


        return retView;
    }

    public void addContesto(String cont){
        contesti.add(cont);
    }

    public void remContesto(String cont){
        contesti.remove(cont);
    }

    public static ArrayList<String> getContesti(){
        return contesti;
    }
}