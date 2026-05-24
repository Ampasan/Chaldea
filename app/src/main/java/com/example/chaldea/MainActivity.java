package com.example.chaldea;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chaldea.adapter.ServantAdapter;
import com.example.chaldea.filter.ServantFilter;
import com.example.chaldea.model.Servant;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvServants;
    private final ArrayList<Servant> list = new ArrayList<>();
    private ServantAdapter servantAdapter;
    private boolean isGridMode = false;

    private MaterialCardView cvClassDetail;
    private TextView tvClassTitle, tvClassDesc;
    private ImageView ivClassIcon;
    private androidx.core.widget.NestedScrollView nestedScrollView;

    private SwipeRefreshLayout swipeRefresh;
    private final String API_URL = "https://api.atlasacademy.io/export/NA/basic_servant.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvServants = findViewById(R.id.rv_servants);
        rvServants.setHasFixedSize(true);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(this::fetchData);

        cvClassDetail = findViewById(R.id.cv_class_detail);
        tvClassTitle = findViewById(R.id.tv_class_title);
        tvClassDesc = findViewById(R.id.tv_class_desc);
        ivClassIcon = findViewById(R.id.iv_class_icon);
        nestedScrollView = findViewById(R.id.nested_scroll_view);

        ChipGroup chipGroup = findViewById(R.id.chip_group_filter);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            TransitionManager.beginDelayedTransition(swipeRefresh);
            if (checkedIds.isEmpty()) {
                cvClassDetail.setVisibility(View.GONE);
                updateList(null);
            } else {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chip_all) {
                    cvClassDetail.setVisibility(View.GONE);
                    updateList(null);
                } else {
                    cvClassDetail.setVisibility(View.VISIBLE);
                    if (checkedId == R.id.chip_saber) {
                        showClassDetail(getString(R.string.class_saber), getString(R.string.desc_saber), R.drawable.saber);
                        updateList("Saber");
                    } else if (checkedId == R.id.chip_archer) {
                        showClassDetail(getString(R.string.class_archer), getString(R.string.desc_archer), R.drawable.archer);
                        updateList("Archer");
                    } else if (checkedId == R.id.chip_lancer) {
                        showClassDetail(getString(R.string.class_lancer), getString(R.string.desc_lancer), R.drawable.lancer);
                        updateList("Lancer");
                    } else if (checkedId == R.id.chip_caster) {
                        showClassDetail(getString(R.string.class_caster), getString(R.string.desc_caster), R.drawable.caster);
                        updateList("Caster");
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showRecyclerList();
        fetchData();
    }

    private void fetchData() {
        swipeRefresh.setRefreshing(true);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                response -> {
                    list.clear();
                    try {
                        String[] targetClasses = {"Saber", "Archer", "Lancer", "Caster"};
                        java.util.Map<String, Integer> classCounts = new java.util.HashMap<>();
                        for (String cls : targetClasses) {
                            classCounts.put(cls, 0);
                        }

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String className = obj.getString("className");

                            String matchedClass = null;
                            for (String cls : targetClasses) {
                                if (cls.equalsIgnoreCase(className)) {
                                    matchedClass = cls;
                                    break;
                                }
                            }

                            if (matchedClass != null) {
                                Integer currentCount = classCounts.get(matchedClass);
                                if (currentCount != null && currentCount < 6) {
                                    String rarityStars = "";
                                    int rarity = obj.getInt("rarity");
                                    for (int j = 0; j < rarity; j++) rarityStars += "★";
                                    
                                    String attr = obj.getString("attribute");
                                    String formattedAttr = attr.substring(0, 1).toUpperCase() + attr.substring(1).toLowerCase();

                                    String description = "<b>Rarity:</b> <font color='#D4AF37'>" + rarityStars + "</font><br/>" +
                                                         "<b>Attribute:</b> <font color='#1f2557'>" + formattedAttr + "</font>";

                                    list.add(new Servant(
                                            obj.getString("name"),
                                            description,
                                            matchedClass,
                                            obj.getString("face")
                                    ));
                                    classCounts.put(matchedClass, currentCount + 1);
                                }
                            }

                            boolean allCollected = true;
                            for (int count : classCounts.values()) {
                                if (count < 6) {
                                    allCollected = false;
                                    break;
                                }
                            }
                            if (allCollected) break;
                        }
                        servantAdapter.setList(list);
                        servantAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "JSON Error", Toast.LENGTH_SHORT).show();
                    }
                    swipeRefresh.setRefreshing(false);
                },
                error -> {
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                });

        queue.add(jsonArrayRequest);
    }

    private void showClassDetail(String title, String desc, int iconRes) {
        tvClassTitle.setText(title);
        tvClassDesc.setText(desc);
        ivClassIcon.setImageResource(iconRes);
    }

    private void updateList(String servantClass) {
        ArrayList<Servant> filteredList = ServantFilter.filterByClass(list, servantClass);
        servantAdapter.setList(filteredList);
        servantAdapter.notifyDataSetChanged();
        nestedScrollView.post(() -> nestedScrollView.fullScroll(View.FOCUS_UP));
    }

    private void showRecyclerList() {
        rvServants.setLayoutManager(new LinearLayoutManager(this));
        if (servantAdapter == null) {
            servantAdapter = new ServantAdapter(new ArrayList<>(list));
            rvServants.setAdapter(servantAdapter);
        }
        servantAdapter.setViewType(false);
        servantAdapter.notifyDataSetChanged();
    }

    private void showRecyclerGrid() {
        rvServants.setLayoutManager(new GridLayoutManager(this, 2));
        if (servantAdapter == null) {
            servantAdapter = new ServantAdapter(new ArrayList<>(list));
            rvServants.setAdapter(servantAdapter);
        }
        servantAdapter.setViewType(true);
        servantAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle_view) {
            isGridMode = !isGridMode;
            TransitionManager.beginDelayedTransition(rvServants);
            if (isGridMode) {
                showRecyclerGrid();
                item.setIcon(R.drawable.baseline_view_list_24);
            } else {
                showRecyclerList();
                item.setIcon(R.drawable.baseline_grid_view_24);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
