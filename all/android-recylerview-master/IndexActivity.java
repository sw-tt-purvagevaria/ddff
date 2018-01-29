package com.pg.demowebservice;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pg.demowebservice.WebserviceHelper.WebServiceHelper;
import com.pg.demowebservice.adapter.DataAdapter;
import com.pg.demowebservice.model.BeanClass;
import com.pg.demowebservice.utils.StringUtils;

import java.util.HashMap;

public class IndexActivity extends ParentActivity implements OnMapReadyCallback {
    private String TAG = "IndexActivity";
    // http://portal.bodycarpenters.com/api/get_trainers.php?action=get_trainers&gym_id=1&branch_id=5
    private final String baseUrl = " http://portal.bodycarpenters.com/api/get_trainers.php?";

    private BeanClass beanClass;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initObjects();

        initUIControls();

        callWebservice();



    }

    /**
     * will call webservice for get data
     */
    private void callWebservice() {
        
        
         if (isNetworkAvailable(this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    return strParams;
                }
            };

            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
            RetryPolicy policy = new DefaultRetryPolicy(10000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            mRequestQueue.add(stringRequest);


        } else {
            Toast.makeText(this, "No netwrok available", Toast.LENGTH_SHORT).show();
        }

        
        
        
        
        
        // String BASE_URL = getResources().getString(R.string.BASE_URL);
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "get_trainers");
        params.put("gym_id", "1");
        params.put("branch_id", "1");

        final WebServiceHelper webServiceHelper = new WebServiceHelper(this);
        webServiceHelper.callWS(baseUrl, params, new WebServiceHelper.JSONRequestHandlerPost() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "Response :: " + response);
                if (isStrNotNull(response)) {
                    webServiceHelper.parseJSONOResponse(response);
                    beanClass = getDataBean(response);
                    Log.d(TAG, "SIZE :: " + beanClass.getData().size());
                    setAdapter();
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
            }

            @Override
            public void networkNotAvailable(boolean isNetworkAvailable) {
                super.networkNotAvailable(isNetworkAvailable);
                if (!isNetworkAvailable) {
                    showToast("No internet connection available");
                }
            }
        });
    }   //end of callWebservice


    @Override
    public void setAdapter() {
        super.setAdapter();
        list.setAdapter(new DataAdapter(this, beanClass.getData()));
        //list.setLayoutManager(new LinearLayoutManager(this));
        list.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void initUIControls() {
        super.initUIControls();
        list = (RecyclerView) findViewById(R.id.list);
    }

    @Override
    public void initObjects() {
        super.initObjects();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng pladi = new LatLng(23.013054, 72.562515);
        googleMap.addMarker(new MarkerOptions().position(pladi).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(pladi));
    }

    /**
     * @param jsonDataObj
     * @return
     */
    public BeanClass getDataBean(String jsonDataObj) {
        Gson gson = new GsonBuilder().create();
        BeanClass subCategoryBean = gson.fromJson(jsonDataObj, BeanClass.class);
        return subCategoryBean;
    }//end o getProductDetailBeanFromJson
    
    
    
    
    
 public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }//end of isNetworkAvailable

}
