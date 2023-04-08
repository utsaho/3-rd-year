package com.medBuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class AdapterBlood extends RecyclerView.Adapter<AdapterBlood.ViewHolder> {
    Context context;

    LayoutInflater inflater;
    ArrayList<Object> data = new ArrayList<>();


    public AdapterBlood(Context context, ArrayList<Object> data)
    {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }



    @Override
    public AdapterBlood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bloodview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBlood.ViewHolder holder, int position) {

        Map<String, String> map = (Map<String, String>) data.get(position);
        holder.donorBlood.setText("Blood Group: "+map.get("userBloodGroup"));
        holder.donorName.setText(map.get("userName"));
        holder.uid.setText(map.get("userId"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView donorName;
        public TextView donorBlood;
        public TextView uid;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            donorName = itemView.findViewById(R.id.donorName);
            donorBlood = itemView.findViewById(R.id.donorBloodGrp);
            uid = itemView.findViewById(R.id.uid);
            card = itemView.findViewById(R.id.bloodView);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Hare Krishna size is: ", Toast.LENGTH_SHORT).show();
        }
    }
}
