package com.example.ruterbillett4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<Model> modelArrayList;

    private LayoutInflater inflater;
    private Context ctx;

    public CustomAdapter(ArrayList<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, null);
        CustomAdapter.MyViewHolder viewHolder = new CustomAdapter.MyViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, int position) {

        holder.tvFruit.setText(Tab_1_Fragment_Activity.modelArrayList.get(position).getFruit());
        holder.tvnumber.setText(String.valueOf(Tab_1_Fragment_Activity.modelArrayList.get(position).getNumber()));

    }

    @Override
    public int getItemCount() {
        return Tab_1_Fragment_Activity.modelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected Button btn_plus, btn_minus;
        private TextView tvFruit, tvnumber;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvFruit = (TextView) itemView.findViewById(R.id.animal);
            tvnumber = (TextView) itemView.findViewById(R.id.number);
            btn_plus = (Button) itemView.findViewById(R.id.plus);
            btn_minus = (Button) itemView.findViewById(R.id.minus);

            btn_plus.setTag(R.integer.btn_plus_view, itemView);
            btn_minus.setTag(R.integer.btn_minus_view, itemView);
            btn_plus.setOnClickListener(this);
            btn_minus.setOnClickListener(this);

        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == btn_plus.getId()){

                View tempview = (View) btn_plus.getTag(R.integer.btn_plus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.number);
                int number = Integer.parseInt(tv.getText().toString()) + 1;
                tv.setText(String.valueOf(number));
                Tab_1_Fragment_Activity.modelArrayList.get(getAdapterPosition()).setNumber(number);

            } else if(v.getId() == btn_minus.getId()) {

                View tempview = (View) btn_minus.getTag(R.integer.btn_minus_view);
                TextView tv = (TextView) tempview.findViewById(R.id.number);
                if(Integer.parseInt(tv.getText().toString()) > 0){
                    int number = Integer.parseInt(tv.getText().toString()) - 1;
                    tv.setText(String.valueOf(number));
                    Tab_1_Fragment_Activity.modelArrayList.get(getAdapterPosition()).setNumber(number);
                }

            }
        }

    }
}
