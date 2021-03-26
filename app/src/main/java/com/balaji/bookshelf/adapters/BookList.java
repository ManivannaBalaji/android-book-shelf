package com.balaji.bookshelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.bookshelf.Book;
import com.balaji.bookshelf.BookdetailActivity;
import com.balaji.bookshelf.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookList extends RecyclerView.Adapter<BookList.ViewHolder> {
    LayoutInflater inflater;
    List<Book> books;
    List<String> favList = new ArrayList<>();

    public BookList(Context ctx, List<Book> books){
        this.inflater = LayoutInflater.from(ctx);
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.books_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(books.get(position).getTitle());
        Picasso.get().load(books.get(position).getImageUrl()).into(holder.bookThumb);
        holder.authors.setText(books.get(position).getAuthors());
        holder.date.setText(books.get(position).getDate());
        holder.pgCount.setText(books.get(position).getPgCount());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, authors, date, pgCount;
        ImageView bookThumb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            bookThumb = itemView.findViewById(R.id.bookThumb);
            authors = itemView.findViewById(R.id.authTextView);
            date = itemView.findViewById(R.id.dateTextView);
            pgCount = itemView.findViewById(R.id.pgCountText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BookdetailActivity.class);
                    intent.putExtra("title", books.get(getAdapterPosition()).getTitle());
                    intent.putExtra("image", books.get(getAdapterPosition()).getImageUrl());
                    intent.putExtra("authors", books.get(getAdapterPosition()).getAuthors());
                    intent.putExtra("date", books.get(getAdapterPosition()).getDate());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
