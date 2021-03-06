package com.balaji.bookshelf;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balaji.bookshelf.DB.AppDatabase;
import com.balaji.bookshelf.DB.MyEntity;
import com.balaji.bookshelf.adapters.CategoryHome;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    RecyclerView homeRecyclerView;
    List<String> homeCategoryList;
    CategoryHome adapter;
    TextView noDataTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        homeCategoryList = new ArrayList<>();
        selectFormDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noDataTextView = (TextView) view.findViewById(R.id.noDataTextView);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        if(homeCategoryList.size()<1){
            homeRecyclerView.setVisibility(View.INVISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
        }else{
            homeRecyclerView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.INVISIBLE);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        homeRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CategoryHome(getActivity(), homeCategoryList);
        homeRecyclerView.setAdapter(adapter);
    }

    private void selectFormDB(){
        AppDatabase db = AppDatabase.getInstance(this.getContext());
        List<MyEntity> dataList = db.entityDao().getAllData();
        for(int i=0;i<dataList.size();i++){
//            Log.i("Retrieved list", dataList.get(i).name);
            homeCategoryList.add(dataList.get(i).name);
        }
    }

}