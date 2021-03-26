package com.balaji.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.balaji.bookshelf.DB.AppDatabase;
import com.balaji.bookshelf.DB.MyEntity;
import com.balaji.bookshelf.adapters.CategorySelector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    String url = "https://demo5479419.mockable.io/books/";
    Category category;
    CategorySelector adapter;
    List<String> categoriesList, allCategoryList;
    HashSet<String> hashSet;
    List<Category> myCategoryList;
    List<String> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("Category");
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoriesList = new ArrayList<>();
        allCategoryList = new ArrayList<>();
        hashSet = new HashSet<>();
        myCategoryList = new ArrayList<>();
        selectedItems = new ArrayList<>();

        parseJsonData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.doneBtn:
                saveToDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void parseJsonData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONArray array = jsonObject.getJSONArray("categories");
                        for(int j=0; j<array.length();j++){
                            if(array.getString(j).length()>1){
                                categoriesList.add(array.getString(j));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                allCategoryList.addAll(categoriesList);
                removeDuplicate();
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new CategorySelector(getApplicationContext(), myCategoryList);
                categoryRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void removeDuplicate(){
        hashSet.addAll(categoriesList);
        categoriesList.clear();
        categoriesList.addAll(hashSet);
        countCategories();
    }

    private void countCategories(){
        for(int i=0;i<categoriesList.size();i++){
            category = new Category();
            String name = categoriesList.get(i);
            int count = Collections.frequency(allCategoryList, name);
            category.setCategoryName(name);
            category.setCategoryCount(count);
            myCategoryList.add(category);
        }
    }

    private void saveToDB(){
        selectedItems = adapter.getSelectedItems();
        if(selectedItems.size()>0){
            AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
            db.entityDao().delete();
            for(int i=0; i<selectedItems.size();i++){
                MyEntity myEntity = new MyEntity();
                myEntity.name = selectedItems.get(i);
                db.entityDao().insertData(myEntity);
            }
            Toast.makeText(getApplicationContext(), "Categories saved successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Please select atleast one category", Toast.LENGTH_SHORT).show();
        }
        selectFormDB();
    }

    private void selectFormDB(){
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        List<MyEntity> dataList = db.entityDao().getAllData();
        for(int i=0;i<dataList.size();i++){
            Log.i("Retrieved list", dataList.get(i).name);
        }
    }

}