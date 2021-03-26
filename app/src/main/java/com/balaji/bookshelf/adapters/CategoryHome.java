package com.balaji.bookshelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.bookshelf.BooklistActivity;
import com.balaji.bookshelf.MainActivity;
import com.balaji.bookshelf.R;

import java.util.List;

public class CategoryHome extends RecyclerView.Adapter<CategoryHome.ViewHolder> {

    List<String> homeCategoryList;
    LayoutInflater inflater;

    public CategoryHome(Context ctx, List<String> homeCategoryList){
        inflater = LayoutInflater.from(ctx);
        this.homeCategoryList = homeCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(homeCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.homeCategoryTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), homeCategoryList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), BooklistActivity.class);
                    intent.putExtra("category", homeCategoryList.get(getAdapterPosition()).toString());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
