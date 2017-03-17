package com.tradiz.prototype.tradiz;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tradiz.prototype.tradiz.adapter.TradeesAdapter;
import com.tradiz.prototype.tradiz.app.AppController;
import com.tradiz.prototype.tradiz.layout.RangeBarVertical;
import com.tradiz.prototype.tradiz.model.Tradees;
import com.tradiz.prototype.tradiz.utils.UrlConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SearchTradizActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, View.OnLongClickListener, RadioGroup.OnCheckedChangeListener, RangeBarVertical.OnRangeBarChangeListener {


    private TextView title;
    private RecyclerView recyclerView;
    private Button btn_rank, btn_rate, btn_availability, btn_distance;
    private boolean rank_boolean = true, rate_boolean = true, availabiltiy_boolean = true, distance_boolean = true;
    private PopupWindow popupWindow1, popupWindow2, popupWindow3, popupWindow4;
    private SearchView searchView;
    private int rank = 5, max_dist = 10, min_rate = 10, max_rate = 90, available_from = 1, available_to = 90, rank_button_check = 5, availability_button_check = 1;
    private Dialog dialog;
    private ArrayList<Tradees> tradeesList = new ArrayList<Tradees>();
    private TradeesAdapter adapter;

    private static final String TAG = "Tradiz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tradiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("TRADIZ");
        btn_rank = (Button) findViewById(R.id.rank);
        btn_rate = (Button) findViewById(R.id.rate);
        btn_availability = (Button) findViewById(R.id.availability);
        btn_distance = (Button) findViewById(R.id.distance);

        searchView = (SearchView) findViewById(R.id.searchView);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TradeesAdapter(getApplicationContext(), tradeesList);
        recyclerView.setAdapter(adapter);
        jsonRequest(UrlConst.URL_SEARCH_TRADIZ);
        btn_rank.setOnClickListener(this);
        btn_rank.setOnLongClickListener(this);
        btn_rate.setOnClickListener(this);
        btn_rate.setOnLongClickListener(this);
        btn_availability.setOnClickListener(this);
        btn_availability.setOnLongClickListener(this);
        btn_distance.setOnClickListener(this);
        btn_distance.setOnLongClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                Log.d(TAG, "OnQueryTextChange");
                return true;
            }

            public boolean onQueryTextSubmit(String searhKeyword) {
                //Here u can get the value "query" which is entered in the search box.
                Log.d(TAG, "OnQueryTextSubmit");
                String URL = "http://www.mexuz.com:8080/Tradiz/api/GetTradees/-33.7327400/151.1470990/10.0/" + searhKeyword + "/*/default/10/1?1=1";
                jsonRequest(URL);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


    }


    //  Button onClick Listner
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rank:
                if (rank_boolean) {
                    rank_boolean = false;
                    jsonRequest(UrlConst.URL_SEARCH_RANK_DESC);
                    btn_rank.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
                } else {
                    rank_boolean = true;
                    jsonRequest(UrlConst.URL_SEARCH_RANK_ASC);
                    btn_rank.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
                }
                break;
            case R.id.rate:
                if (rate_boolean) {
                    rate_boolean = false;
                    jsonRequest(UrlConst.URL_SEARCH_RATE_DESC);
                    btn_rate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
                } else {
                    rate_boolean = true;
                    jsonRequest(UrlConst.URL_SEARCH_RATE_ASC);
                    btn_rate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
                }
                break;
            case R.id.availability:
                if (availabiltiy_boolean) {
                    availabiltiy_boolean = false;
                    btn_availability.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
                    jsonRequest(UrlConst.URL_SEARCH_AVAILABILITY_DESC);
                } else {
                    availabiltiy_boolean = true;
                    jsonRequest(UrlConst.URL_SEARCH_AVAILABILITY_ASC);
                    btn_availability.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
                }
                break;
            case R.id.distance:
                if (distance_boolean) {
                    distance_boolean = false;
                    btn_distance.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
                    jsonRequest(UrlConst.URL_SEARCH_DISTANCE_DESC);
                } else {
                    distance_boolean = true;
                    jsonRequest(UrlConst.URL_SEARCH_DISTANCE_ASC);
                    btn_distance.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
                }
                break;

            default:
                break;
        }
    }

    // Button Long Click Listner
    @Override
    public boolean onLongClick(View view) {
        Handler handler;
        View popupView;
        LayoutInflater layoutInflater;

        switch (view.getId()) {
            case R.id.rank:

                layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.popup_rank, null);
                RadioGroup radioGroup1 = (RadioGroup) popupView.findViewById(R.id.radio_group_rank);
                radioGroup1.setOnCheckedChangeListener(this);
                RadioButton rb_rank1 = (RadioButton) popupView.findViewById(R.id.radio_button_rank1);
                RadioButton rb_rank2 = (RadioButton) popupView.findViewById(R.id.radio_button_rank2);
                RadioButton rb_rank3 = (RadioButton) popupView.findViewById(R.id.radio_button_rank3);
                RadioButton rb_rank4 = (RadioButton) popupView.findViewById(R.id.radio_button_rank4);
                RadioButton rb_rank5 = (RadioButton) popupView.findViewById(R.id.radio_button_rank5);
                switch (rank_button_check) {
                    case 1:
                        rb_rank1.setChecked(true);
                        break;
                    case 2:
                        rb_rank2.setChecked(true);
                        break;
                    case 3:
                        rb_rank3.setChecked(true);
                        break;
                    case 4:
                        rb_rank4.setChecked(true);
                        break;
                    case 5:
                        rb_rank5.setChecked(true);
                        break;
                    default:
                        break;
                }

                if (popupWindow1 == null) {
                    popupWindow1 = new PopupWindow(
                            popupView,
                            btn_rank.getWidth(),
                            DrawerLayout.LayoutParams.WRAP_CONTENT);
                }
                popupWindow1.setOutsideTouchable(false);

                if (popupWindow1.isShowing()) {
                    Log.d(TAG, "popupWindow1.isShowing()");
                    popupWindow1.dismiss();
                    btn_rank.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                } else {
                    Log.d(TAG, "!!!!popupWindow1.isShowing()");
                    popupWindow1.showAsDropDown(view, 0, 0);
                    btn_rank.setBackground(getResources().getDrawable(R.drawable.card_grey));

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // close your dialog
                            btn_rank.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                            popupWindow1.dismiss();
                            jsonRequest(getUrl());
                        }

                    }, 6000);
                }

                break;

            case R.id.rate:
                layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.pop_budget, null);
                RangeBarVertical rangeBarVertical = (RangeBarVertical) popupView.findViewById(R.id.timer1);
                if (popupWindow2 == null) {
                    popupWindow2 = new PopupWindow(
                            popupView,
                            btn_rate.getWidth(),
                            DrawerLayout.LayoutParams.WRAP_CONTENT);
                }
                popupWindow2.setOutsideTouchable(false);

                rangeBarVertical.setMinimumProgress(min_rate, max_rate);


                if (popupWindow2.isShowing()) {

                    popupWindow2.dismiss();
                    btn_rate.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                } else {

                    popupWindow2.showAsDropDown(view, 0, 0);
                    btn_rate.setBackground(getResources().getDrawable(R.drawable.card_grey));
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // close your dialog
                        btn_rate.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                        popupWindow2.dismiss();
                        jsonRequest(getUrl());
                    }

                }, 10000);
                break;

            case R.id.availability:
                layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.popup_availability, null);
                RadioGroup radioGroup2 = (RadioGroup) popupView.findViewById(R.id.radio_group_availability);
                radioGroup2.setOnCheckedChangeListener(this);
                RadioButton rb_free = (RadioButton) popupView.findViewById(R.id.radio_button_free);
                RadioButton rb_tomorrow = (RadioButton) popupView.findViewById(R.id.radio_button_tomorrow);
                RadioButton rb_next_week = (RadioButton) popupView.findViewById(R.id.radio_button_nextweek);
                RadioButton rb_next_month = (RadioButton) popupView.findViewById(R.id.radio_button_nextmonth);

                switch (availability_button_check) {
                    case 1:
                        rb_free.setChecked(true);
                        break;
                    case 2:
                        rb_tomorrow.setChecked(true);
                        break;
                    case 3:
                        rb_next_week.setChecked(true);
                        break;
                    case 4:
                        rb_next_month.setChecked(true);
                        break;
                    default:
                        break;
                }
                if (popupWindow3 == null) {
                    popupWindow3 = new PopupWindow(
                            popupView,
                            btn_availability.getWidth(),
                            DrawerLayout.LayoutParams.WRAP_CONTENT);
                }
                popupWindow3.setOutsideTouchable(false);

                if (popupWindow3.isShowing()) {

                    popupWindow3.dismiss();
                    btn_availability.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                } else {

                    popupWindow3.showAsDropDown(view, 0, 0);
                    btn_availability.setBackground(getResources().getDrawable(R.drawable.card_grey));
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // close your dialog
                        btn_availability.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                        popupWindow3.dismiss();

                        jsonRequest(getUrl());
                    }

                }, 6000);
                break;

            case R.id.distance:

                layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = layoutInflater.inflate(R.layout.popup_distance, null);
                if (popupWindow4 == null) {
                    popupWindow4 = new PopupWindow(
                            popupView,
                            btn_distance.getWidth(),
                            DrawerLayout.LayoutParams.WRAP_CONTENT);
                }
                popupWindow4.setOutsideTouchable(false);

                if (popupWindow4.isShowing()) {

                    popupWindow4.dismiss();
                    btn_distance.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                } else {

                    popupWindow4.showAsDropDown(view, 0, 0);
                    btn_distance.setBackground(getResources().getDrawable(R.drawable.card_grey));
                }
                SeekBar seekBar = (SeekBar) popupView.findViewById(R.id.verticalSeekbar);
                final TextView distance = (TextView) popupView.findViewById(R.id.verticalSeekbarText);
                seekBar.setProgress(max_dist);
                distance.setText(max_dist + " Km");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progress = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                        progress = progresValue;
                        distance.setText("" + progress + "Km");
                        max_dist = progresValue;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                });

                handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // close your dialog
                        btn_distance.setBackground(getResources().getDrawable(R.drawable.card_gray_dark));
                        popupWindow4.dismiss();

                        jsonRequest(getUrl());
                    }

                }, 6000);


                break;
            default:
                break;

        }
        return true;
    }


    // Radio Group Check change listner
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {

            case R.id.radio_button_rank1:
                rank = 1;
                rank_button_check = 1;

                break;
            case R.id.radio_button_rank2:
                rank = 2;
                rank_button_check = 2;

                break;
            case R.id.radio_button_rank3:
                rank = 3;
                rank_button_check = 3;

                break;
            case R.id.radio_button_rank4:
                rank = 4;
                rank_button_check = 4;

                break;
            case R.id.radio_button_rank5:
                rank = 5;
                rank_button_check = 5;

                break;
            case R.id.radio_button_free:
                available_from = 0;
                available_to = 90;
                availability_button_check = 1;

                break;
            case R.id.radio_button_tomorrow:
                available_from = 1;
                available_to = 1;
                availability_button_check = 2;

                break;
            case R.id.radio_button_nextweek:
                available_from = 7;
                available_to = 14;
                availability_button_check = 3;

                break;

            case R.id.radio_button_nextmonth:
                available_from = 30;
                available_to = 90;
                availability_button_check = 4;

                break;

            default:

                break;

        }
    }


    // On Back press listner
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Log.d(TAG, "camera");
        } else if (id == R.id.nav_gallery) {
            Log.d(TAG, "gallery");
        } else if (id == R.id.nav_slideshow) {
            Log.d(TAG, "slide show");
        } else if (id == R.id.nav_manage) {
            Log.d(TAG, "manage");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "share");
        } else if (id == R.id.nav_send) {
            Log.d(TAG, "send");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // show progress dialog
    private void showPDialog() {
        if (dialog == null) {
            dialog = new Dialog(SearchTradizActivity.this);
            dialog.setContentView(R.layout.progress_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }


    // hide progress dialog
    private void hidePDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    // Rest API call
    private void jsonRequest(String url) {
        Log.d(TAG, url);
        // Showing progress dialog before making http request
        showPDialog();
        // Creating volley request obj
        JsonArrayRequest jobReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        tradeesList.clear();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Tradees tradees = new Tradees();
                                tradees.setFullName(obj.getString("FullName"));
                                tradees.setImageURL(obj.getString("ImageURL"));
                                tradees.setRank(obj.getInt("Rank"));
                                tradees.setRate(obj.getInt("Rate"));
                                tradees.setDistance(obj.getInt("Distance"));
                                tradees.setAvailability(obj.getInt("Availability"));
                                // adding jobs to jobs array
                                tradeesList.add(tradees);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jobReq);
    }

    public void onRangeBarChange(int min, int max) {
        min_rate = min;
        max_rate = max;
    }

    private String getUrl() {
        String URL = UrlConst.BASE_URL + max_dist + "/*/*/default/10/1?Rank lt" + " " + rank + " and " + "Rate gt" + " " + min_rate + " and Rate lt" + " " + max_rate + " and " + "Availability gt" + " " + available_from + " and Availability lt" + " " + available_to;
        URL = URL.replaceAll(" ", "%20");
        return URL;
    }
}
