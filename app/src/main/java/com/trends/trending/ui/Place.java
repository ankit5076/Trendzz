package com.trends.trending.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.trends.trending.R;
import com.trends.trending.adapter.PlaceAdapter;
import com.trends.trending.model.PlaceToVisitModel;
import com.trends.trending.repository.PlaceResponseList;
import com.trends.trending.utils.ExtraHelper;

import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.app.SearchManager;
import android.widget.EditText;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;

import static com.trends.trending.utils.Keys.PlaceInfo.KEY_PLACE_OBJECT;
import static com.trends.trending.utils.Keys.VideoInfo.PLACE_TO_VISIT;


public class Place extends AppCompatActivity {

    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;

    //@BindView(R.id.toolbar)
    //Toolbar toolbar;
    private Toolbar toolbar;

    private PlaceAdapter mAdapter;

    private ArrayList<PlaceToVisitModel> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        // ButterKnife.bind(this);
        findViews();
        initToolbar("Places to Visit");
        setAdapter();

    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.place_search, menu);


        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) this.getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        //changing edittext color
        EditText searchEdit = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<PlaceToVisitModel> filterList = new ArrayList<>();
                if (s.length() > 0) {
                    for (int i = 0; i < modelList.size(); i++) {
                        if (modelList.get(i).getPlaceName().toLowerCase().contains(s.toLowerCase())) {
                            filterList.add(modelList.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {
                    mAdapter.updateList(modelList);
                }
                return false;
            }
        });


        return true;
    }


    private void setAdapter() {

        Gson gson = new Gson();
        String jsonString = ExtraHelper.parseJson(Place.this, PLACE_TO_VISIT);
        if (jsonString != null) {
            PlaceResponseList placeResponseList = gson.fromJson(jsonString, PlaceResponseList.class);
            modelList.addAll(placeResponseList.getBestPlaces());
        } else {
            modelList = null;
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        mAdapter = new PlaceAdapter(Place.this, modelList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);


        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        // dividerItemDecoration.setDrawable(ContextCompat.getDrawable(Place.this, R.drawable.divider_recyclerview));
        //recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, PlaceToVisitModel model) {
                //handle item click events here
                Toast.makeText(Place.this, model.getPlaceName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Place.this, PlaceDetail.class);
                intent.putExtra(KEY_PLACE_OBJECT, model);
                startActivity(intent);
            }
        });


    }


}
