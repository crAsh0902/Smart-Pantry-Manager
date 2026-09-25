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

        holder.txtIngName.setText(
                pantryNames.get(position)
        );

        holder.txtIngQuantity.setText(
                pantryQuantities.get(position)
        );

        holder.btnEditIng.setOnClickListener(v -> {

            editClickListener.onEditClick(id);

        });

        holder.btnDeleteIng.setOnClickListener(v -> {

            deleteClickListener.onDeleteClick(id);

        });
    }



    @Override
    public int getItemCount() {

        return pantryNames.size();

    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtIngName;
        TextView txtIngQuantity;

        Button btnEditIng;
        Button btnDeleteIng;

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtIngName =
                    itemView.findViewById(
                            R.id.txtIngName
                    );

            txtIngQuantity =
                    itemView.findViewById(
                            R.id.txtIngQuantity
                    );

            btnEditIng =
                    itemView.findViewById(
                            R.id.btnEditIng
                    );

            btnDeleteIng =
                    itemView.findViewById(
                            R.id.btnDeleteIng
                    );
        }
    }
}