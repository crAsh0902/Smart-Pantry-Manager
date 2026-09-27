package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;



// recipe_item.xml



public class SuggRecipeAdapter
        extends RecyclerView.Adapter<SuggRecipeAdapter.RecipeViewHolder> {

    private ArrayList<Long> recipeIds;
    private ArrayList<String> recipeNames;
    private OnRecipeClickListener recipeClickListener;



    public interface OnRecipeClickListener {
        void onRecipeClick(long id);
    }



    public SuggRecipeAdapter(
            ArrayList<Long> recipeIds,
            ArrayList<String> recipeNames,
            OnRecipeClickListener recipeClickListener) {

        this.recipeIds = recipeIds;
        this.recipeNames = recipeNames;

        this.recipeClickListener = recipeClickListener;
    }





    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.recipe_item,
                        parent,
                        false
                );

        return new RecipeViewHolder(view);
    }



    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        long id =
                recipeIds.get(position);

        holder.txtRecipeName.setText(
                recipeNames.get(position)
        );

        holder.btnViewRecipe.setOnClickListener(v -> {

            recipeClickListener.onRecipeClick(id);

        });
    }



    @Override
    public int getItemCount() {

        return recipeNames.size();

    }



    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        Button btnViewRecipe;



        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);

            txtRecipeName =
                    itemView.findViewById(
                            R.id.txtRecipeName
                    );

            btnViewRecipe =
                    itemView.findViewById(
                            R.id.btnViewRecipe
                    );
        }
    }
}