package com.example.ruterbillett4;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Tab_1_Fragment_Activity extends Fragment {
    private RecyclerView recyclerView;
    public static ArrayList<Model> modelArrayList;
    private com.example.ruterbillett4.CustomAdapter customAdapter;
    private String[] fruitlist = new String[]{"Voksen", "Barn", "Honnør"};
    Context context;
    private Button btnnext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_1_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        modelArrayList = getModel();
        customAdapter = new com.example.ruterbillett4.CustomAdapter(modelArrayList);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return  rootView;
    }

    private ArrayList<Model> getModel(){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < fruitlist.length; i++){

            Model model = new Model();
            model.setNumber(1);
            model.setFruit(fruitlist[i]);
            list.add(model);
        }
        return list;
    }
}

