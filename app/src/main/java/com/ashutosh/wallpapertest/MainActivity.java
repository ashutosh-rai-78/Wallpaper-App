package com.ashutosh.wallpapertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashutosh.wallpapertest.adapter.CategoryList_Adapter;
import com.ashutosh.wallpapertest.adapter.ColorsAdapter;
import com.ashutosh.wallpapertest.adapter.Wallpaper_Adapter;
import com.ashutosh.wallpapertest.model.CategoryModelList;
import com.ashutosh.wallpapertest.model.ColorsModel;
import com.ashutosh.wallpapertest.model.Model;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView rVBOM, recyclerViewCategoryList, rvColors, rvPopular;
    Wallpaper_Adapter wallpaperAdapter;
    CategoryList_Adapter categoryListAdapter;
    ColorsAdapter colorsAdapter;
    List<ColorsModel> colorsModels;
    List<Model> wallpaperModelList;
    List<CategoryModelList> categoryModelLists;
    int page_no = 1;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;


    TextView moreButton;

    public ArrayList<Model> globalList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API api = new API(this);
        buildColors();


        rVBOM = findViewById(R.id.recyclerView);
        rvColors = findViewById(R.id.colorList);
        moreButton = findViewById(R.id.moreButton);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllWallpaperActivity.class);
                startActivity(i);
//                i.putExtra("item", wallpaperModelList);
            }
        });
        wallpaperModelList = new ArrayList<>();
        wallpaperAdapter = new Wallpaper_Adapter(this, wallpaperModelList, true);
        rVBOM.setAdapter(wallpaperAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rVBOM.setLayoutManager(layoutManager);


        colorsAdapter = new ColorsAdapter(colorsModels, this);
        rvColors.setAdapter(colorsAdapter);
        LinearLayoutManager lLColor = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvColors.setLayoutManager(lLColor);


        recyclerViewCategoryList = findViewById(R.id.categoryList);
        buildCategories();
        categoryListAdapter = new CategoryList_Adapter(categoryModelLists, this);
        recyclerViewCategoryList.setAdapter(categoryListAdapter);
        LinearLayoutManager linearLayoutCategory = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewCategoryList.setLayoutManager(linearLayoutCategory);

        PWallpaper();

/*        rVBOM.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }


//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                currentItems = gridLayoutManager.getChildCount();
//                totalItems = gridLayoutManager.getItemCount();
//                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                    isScrolling = false;
////                    fetchWallpaper();
//                    fetchData();
//
//                }
//            }
        });*/


        api.fetchWallpapers(null, new API.OnWallpapersResponseListener() {
            @Override
            public void onResponse(ArrayList<Model> models) throws JSONException {
                wallpaperModelList.addAll(models);
                wallpaperAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buildCategories() {
        categoryModelLists = new ArrayList<>();
        categoryModelLists.add(new CategoryModelList("Bike", "https://cdn.pixabay.com/photo/2016/04/07/06/53/bmw-1313343_960_720.jpg"));
        categoryModelLists.add(new CategoryModelList("Car", "https://cdn.pixabay.com/photo/2016/11/22/23/44/porsche-1851246_960_720.jpg"));
        categoryModelLists.add(new CategoryModelList("Nature", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"));
    }


    private void buildColors() {
        colorsModels = new ArrayList<>();
//        colorsModels.add(new ColorsModel(1,"aliceblue", 0xFFf0f8ff));
//        colorsModels.add(new ColorsModel(2,"antiquewhite",0xFFfaebd7));
        colorsModels.add(new ColorsModel(2, "aqua", 0xFF00ffff));
        colorsModels.add(new ColorsModel(2, "aquamarine", 0xFF7fffd4));
        colorsModels.add(new ColorsModel(2, "bisque", 0xFFffe4c4));
        colorsModels.add(new ColorsModel(2, "black", 0xFF000000));
        colorsModels.add(new ColorsModel(2, "blue", 0xFF0000ff));
        colorsModels.add(new ColorsModel(2, "blueviolet", 0xFF8a2be2));
        colorsModels.add(new ColorsModel(2, "brown", 0xFFa52a2a));
        colorsModels.add(new ColorsModel(2, "cadetblue", 0xFF5f9ea0));
        colorsModels.add(new ColorsModel(2, "chocolate", 0xFFd2691e));
        colorsModels.add(new ColorsModel(2, "cyan", 0xFF00ffff));
    }



    private void PWallpaper() {
        rvPopular = findViewById(R.id.rVPopular);
        wallpaperAdapter = new Wallpaper_Adapter(this, wallpaperModelList, true);
        rvPopular.setAdapter(wallpaperAdapter);
        LinearLayoutManager lLPopularWall = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPopular.setLayoutManager(lLPopularWall);

        API api = new API(this);
        Map<String, String> map = new HashMap<>();
        map.put("q", "Popular");

        api.fetchWallpapers(map, new API.OnWallpapersResponseListener() {
            @Override
            public void onResponse(ArrayList<Model> models) throws JSONException {
                wallpaperModelList.addAll(models);
                wallpaperAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }

}

//    public void fetchWallpaper() {
//        StringRequest request = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        JSONArray jsonArray = jsonObject.getJSONArray("photos");
//                        int length = jsonArray.length();
//
//                        for (int i = 0; i < length; i++) {
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            int id = object.getInt("id");
//
//                            JSONObject objectImages = object.getJSONObject("src");
//
//
//                            String originalUrl = objectImages.getString("original");
//                            String mediumUrl = objectImages.getString("medium");
//
//                            Wallpaper_Model wallpaper_model = new Wallpaper_Model(id, originalUrl, mediumUrl);
//                            wallpaperModelList.add(wallpaper_model);
//
//                        }
//
//                        wallpaperAdapter.notifyDataSetChanged();
//                        page_no++;
//                    } catch (JSONException e) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", "563492ad6f917000010000018a0779f0bdec43aa96c50def121a2830");
//                return params;
//            }
//        };
////        21200728-66e656db38c1b714199b8e589
//
////        https://pixabay.com/api/?key=21200728-66e656db38c1b714199b8e589&q=yellow+flowers&image_type=photo&pretty=true
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(request);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.item_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        Toast.makeText(MainActivity.this, "dfsfssf", Toast.LENGTH_SHORT).show();
////
////        if (item.getItemId() == R.id.nav_search){
////              SearchView searchView = findViewById(R.id.searchView);
////              searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////
////                  @Override
////                  public boolean onQueryTextSubmit(String query) {
////                      Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
////
////                      url = "https://api.pexels.com/v1/search/?page=" + page_no + "&per_page=1&query=" + query;
////                      wallpaperModelList.clear();
////                      fetchWallpaper();
////                      return true;
////                  }
////
////                  @Override
////                  public boolean onQueryTextChange(String newText) {
////                      Toast.makeText(MainActivity.this, "ddddddddddd", Toast.LENGTH_SHORT).show();
////
////                      return false;
////                  }
////              });
////          }
//
//
//        if (item.getItemId() == R.id.nav_search) {
//
//
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            final EditText editText = new EditText(this);
//            editText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String query = editText.getText().toString().toLowerCase();
//                    url = "https://api.pexels.com/v1/search/?page=" + page_no + "&per_page=1&query=" + query;
//                    wallpaperModelList.clear();
//                    fetchWallpaper();
//                }
//            });
////            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
////            alert.setMessage("Enter Category e.g. Nature");
////            alert.setTitle("Search Wallpaper");
////            alert.setView(editText);
////            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    String query = editText.getText().toString().toLowerCase();
////                    url = "https://api.pexels.com/v1/search/?page="+ page_no +"&per_page=1&query=" + query;
////                    wallpaperModelList.clear();
////                    fetchWallpaper();
////                }
////            });
////            alert.show();
////        }
//            return super.onOptionsItemSelected(item);
//        }
//    }
