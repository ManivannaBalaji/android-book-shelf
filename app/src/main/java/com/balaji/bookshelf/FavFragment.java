package com.balaji.bookshelf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.balaji.bookshelf.DB.AppDatabase;
import com.balaji.bookshelf.DB.FavEntity;
import com.balaji.bookshelf.DB.MyEntity;
import com.balaji.bookshelf.adapters.BookList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavFragment extends Fragment {

    RecyclerView favRecyclerView;
    List<Book> favBooks;
    List<String> nameList;
    BookList adapter;
    String url = "https://demo5479419.mockable.io/books/";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        nameList = new ArrayList<>();
        favBooks = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRecyclerView = (RecyclerView) view.findViewById(R.id.favRecyclerView);
        selectFormDB();
        parseJsonData();
    }

    private void selectFormDB() {
        AppDatabase db = AppDatabase.getInstance(this.getContext());
        List<FavEntity> dataList = db.favDao().getAllFav();
        for (int i = 0; i < dataList.size(); i++) {
            Log.i("Retrieved list", dataList.get(i).favourites);
            nameList.add(dataList.get(i).favourites);
        }
    }

    private void parseJsonData(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        for(int j=0; j<nameList.size();j++){
                            if(nameList.get(j).equals(jsonObject.getString("title"))){
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
                                favBooks.add(book);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new BookList(getContext(), favBooks);
                favRecyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage().toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

}