package com.balaji.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.balaji.bookshelf.DB.AppDatabase;
import com.balaji.bookshelf.DB.FavEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookdetailActivity extends AppCompatActivity {

    TextView title, authors, date, isbn, sDes, lDes;
    ImageView bookPoster;
    FloatingActionButton favBtn;
    String stitle, sauthors, sdate, sisbn, ssDes, slDes, imgUrl;
    String url = "https://demo5479419.mockable.io/books/";
    boolean selected;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);

        db = AppDatabase.getInstance(this.getApplicationContext());

        title = findViewById(R.id.titleTextView);
        authors = findViewById(R.id.authText);
        date = findViewById(R.id.dateText);
        isbn = findViewById(R.id.isbnTextView);
        sDes = findViewById(R.id.shortDesText);
        lDes = findViewById(R.id.longDesText);
        bookPoster = findViewById(R.id.bookPoster);
        favBtn = findViewById(R.id.favBtn);

        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getExtras();
        if(bundle != null){
            stitle = (String) bundle.get("title");
            imgUrl = (String) bundle.get("image");
            sauthors = (String) bundle.get("authors");
            sdate = (String) bundle.get("date");
        }
        parseJsonData();

    }

    private void parseJsonData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(jsonObject.getString("title").equals(stitle)){
                            sisbn = jsonObject.getString("isbn");
                            ssDes = jsonObject.getString("shortDescription");
                            slDes = jsonObject.getString("longDescription");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                title.setText(stitle);
                authors.setText(sauthors);
                date.setText(sdate);
                Picasso.get().load(imgUrl).into(bookPoster);
                isbn.setText(sisbn);
                sDes.setText(ssDes);
                lDes.setText(slDes);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public void myInterest(View view){
        if(selected){
            selected = false;
            favBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            FavEntity delEntity = new FavEntity();
            delEntity.favourites = stitle;
            db.favDao().deleteFav(delEntity);
        }else{
            favBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e74c3c")));
            selected = true;
            FavEntity favEntity = new FavEntity();
            favEntity.favourites = stitle;
            db.favDao().insertFav(favEntity);
        }
    }

}