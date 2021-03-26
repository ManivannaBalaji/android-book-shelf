package com.balaji.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.balaji.bookshelf.DB.AppDatabase;
import com.balaji.bookshelf.DB.FavEntity;
import com.balaji.bookshelf.adapters.BookList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BooklistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Book> books;
    String url = "https://demo5479419.mockable.io/books/";
    BookList adapter;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        recyclerView = findViewById(R.id.booklistRecyclerView);
        books = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            category = (String) bundle.get("category");
            Log.i("Category", category);
        }
        setTitle(category);
//        selectFormDB();
        parseJsonData();
    }

    private void parseJsonData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray array = jsonObject.getJSONArray("categories");
                        for(int j=0; j<array.length();j++){
                            if(array.getString(j).equals(category)){
                                String pgCount = jsonObject.getString("pageCount");
                                JSONArray authArray = jsonObject.getJSONArray("authors");
                                String authors = "";
                                String date="";
                                for(int a=0;a<authArray.length();a++){
                                    authors += authArray.getString(a) + " | ";
                                }
                                JSONObject dateObject = jsonObject.getJSONObject("publishedDate");
                                date = dateObject.getString("$date");
                                date = date.substring(0, 10);
                                Log.i("authors", authors);
                                Log.i("pgcount", pgCount);
                                Log.i("Date", date);
                                Book book = new Book();
                                book.setTitle(jsonObject.getString("title").toString());
                                book.setImageUrl(jsonObject.getString("thumbnailUrl").toString());
                                book.setAuthors(authors);
                                book.setDate(date);
                                book.setPgCount(pgCount);
                                books.add(book);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Log.i("Book len", String.valueOf(books.size()));
                adapter = new BookList(getApplicationContext(), books);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage().toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

//    private void selectFormDB() {
//        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
//        List<FavEntity> dataList = db.favDao().getAllFav();
//        for (int i = 0; i < dataList.size(); i++) {
//            Log.i("Retrieved list", dataList.get(i).favourites);
//            favList.add(dataList.get(i).favourites);
//        }
//    }

}