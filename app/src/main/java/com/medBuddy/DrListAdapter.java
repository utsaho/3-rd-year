package com.medBuddy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
        import com.google.android.material.textview.MaterialTextView;

        import java.util.ArrayList;
        import java.util.Map;


public class DrListAdapter extends RecyclerView.Adapter<DrListAdapter.ViewHolder> {
    Context context;

    ArrayList<Object> data = new ArrayList<>();
    LayoutInflater inflater;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;


    public DrListAdapter(Context context, ArrayList<Object> data, FragmentTransaction fragmentTransaction, Fragment fragment)
    {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.fragmentTransaction = fragmentTransaction;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public DrListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drlistsmplitem, parent, false);
        return new DrListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrListAdapter.ViewHolder holder, int position) {
        Map<String, String> map = (Map<String, String>) data.get(position);
//                                                text2.setText(map.get("drName"));

        holder.drName.setText(map.get("drName"));
        holder.drPhnNmbr.setText(map.get("drHelpLine"));
        Glide.with(context).load(map.get("drPhotoUrl").toString()).into(holder.drPic);
//        image url = https://www.mayapur.com/wp-content/uploads/2020/01/81576281_10158139498613413_2530521523085639680_o-768x512.jpg
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public MaterialTextView drName;
        public ShapeableImageView drPic;
        public TextView drPhnNmbr;
        public CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drName = itemView.findViewById(R.id.smpldrname);
            drPic = itemView.findViewById(R.id.smpldrpic);
            drPhnNmbr = itemView.findViewById(R.id.phnNmbr);
            card = itemView.findViewById(R.id.drlistcard);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TextView phnNmbrtext = v.findViewById(R.id.phnNmbr);
            String phnNmbr = phnNmbrtext.getText().toString();

            Bundle args = new Bundle();
            args.putString("phnNmbr", phnNmbr);
            DProfileFragment.phn = phnNmbr;
//            context.setArguments(args);

//            Bundle arg = new Bundle();
//            args.putString("phn", phnNmbr);
//            ldf.setArguments(arg);

//            String kk = ldf.getArguments().getString("phnNmbr");

//            Toast.makeText(context, "nmbr is: "+phnNmbr +" and "+kk, Toast.LENGTH_SHORT).show();




            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

//Inflate the fragment

        }
    }
}
