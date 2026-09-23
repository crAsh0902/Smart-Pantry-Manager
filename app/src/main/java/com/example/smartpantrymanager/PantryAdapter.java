package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<String> pantryNames;
    private ArrayList<String> pantryQuantities;



    public PantryAdapter(
            ArrayList<String> pantryNames,
            ArrayList<String> pantryQuantities) {

        this.pantryNames = pantryNames;
        this.pantryQuantities = pantryQuantities;
    }



    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_item, parent, false);

        return new PantryViewHolder(view);
    }



    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        holder.txtIngredientName.setText(
                pantryNames.get(position)
        );

        holder.txtIngredientQuantity.setText(
                pantryQuantities.get(position)
        );
    }



    @Override
    public int getItemCount() {
        return pantryNames.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientQuantity;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIngredientName =
                    itemView.findViewById(
                            R.id.txtIngredientName
                    );

            txtIngredientQuantity =
                    itemView.findViewById(
                            R.id.txtIngredientQuantity
                    );
        }
    }
}