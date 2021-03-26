package com.balaji.bookshelf.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.balaji.bookshelf.Category;
import com.balaji.bookshelf.R;
import java.util.ArrayList;
import java.util.List;

public class CategorySelector extends RecyclerView.Adapter<CategorySelector.ViewHolder> {

    LayoutInflater inflater;
    List<Category> categoryList;
    List<String> selectedItems = new ArrayList<>();

    public CategorySelector(Context ctx, List<Category> categoryList){
        inflater = LayoutInflater.from(ctx);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        holder.categoryCount.setText(String.valueOf(categoryList.get(position).getCategoryCount()));
        holder.categoryCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedItems.add(categoryList.get(position).getCategoryName());
//                    Log.i("Item added", selectedItems.get(position));
                } else{
//                    Log.i("Item removed", categoryList.get(position).getCategoryName());
                    selectedItems.remove(categoryList.get(position).getCategoryName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        TextView categoryCount;
        CheckBox categoryCheck;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryCount = (TextView) itemView.findViewById(R.id.categoryCount);
            categoryCheck = (CheckBox) itemView.findViewById(R.id.categoryCheck);
        }
    }

    public List<String> getSelectedItems(){
        return selectedItems;
    }

}
