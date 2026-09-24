package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<Long> pantryIds;
    private ArrayList<String> pantryNames;
    private ArrayList<String> pantryQuantities;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;



    public interface OnEditClickListener {
        void onEditClick(long id);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(long id);
    }



    public PantryAdapter(
            ArrayList<Long> pantryIds,
            ArrayList<String> pantryNames,
            ArrayList<String> pantryQuantities,
            OnEditClickListener editClickListener,
            OnDeleteClickListener deleteClickListener) {

        this.pantryIds = pantryIds;
        this.pantryNames = pantryNames;
        this.pantryQuantities = pantryQuantities;

        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }





    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.pantry_item,
                        parent,
                        false
                );

        return new PantryViewHolder(view);
    }



    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        long id = pantryIds.get(position);

        holder.txtIngredientName.setText(
                pantryNames.get(position)
        );

        holder.txtIngredientQuantity.setText(
                pantryQuantities.get(position)
        );

        holder.btnEditIngredient.setOnClickListener(v -> {

            editClickListener.onEditClick(id);

        });

        holder.btnDeleteIngredient.setOnClickListener(v -> {

            deleteClickListener.onDeleteClick(id);

        });
    }



    @Override
    public int getItemCount() {

        return pantryNames.size();

    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtIngredientName;
        TextView txtIngredientQuantity;

        Button btnEditIngredient;
        Button btnDeleteIngredient;

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtIngredientName =
                    itemView.findViewById(
                            R.id.txtIngredientName
                    );

            txtIngredientQuantity =
                    itemView.findViewById(
                            R.id.txtIngredientQuantity
                    );

            btnEditIngredient =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );

            btnDeleteIngredient =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}