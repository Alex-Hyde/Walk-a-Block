package hyde.development.walkablockmainproject;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private List<Coupon> couponList;
    public static Dictionary<String, Coupon> id_coupon_dict = new Hashtable<>();
    public static Dictionary<String, PointOfInterest> id_POI_dict = new Hashtable<>();
    ListView couponListView;
    public Boolean scrollChange = false;
    private int last_pos=-1;
    private String search_text = "";
    private ListView searchListView;
    private List<PointOfInterest> poiList;
    protected static EditText search_bar;
    protected static ScrollView scrollView;
    View mapView;
    protected static List<Coupon> active_coupon_list;
    protected static int distance_meters = 200;
    public static String total_walked;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

//        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
//        backgroundWorker.execute("login", "qwertqwert", "The name");


//        downloadJSON("http://hydedevelopment.com/stock_service.php");

        if (ReadFile() == null) {
            WriteFile("2300.13");
        }
        total_walked = ReadFile();

        couponList = new ArrayList<>();
        active_coupon_list = new ArrayList<>();
        poiList = new ArrayList<>();

        List<String> urlarray = new ArrayList<>();
        urlarray.add("https://www.thewholesomedish.com/wp-content/uploads/2019/06/THE-BEST-CLASSIC-TACOS-600X900.jpg");
        List<String> AwShucks= new ArrayList<>();
        List<String> AwShucksTags= new ArrayList<>();
        List<String> Locale = new ArrayList<>();
        List<String> Mamis= new ArrayList<>();
        List<String> ShawarTags = new ArrayList<>();
        List<String> State = new ArrayList<>();
        List<String> LocaleTags= new ArrayList<>();
        List<String> Landimg= new ArrayList<>();
        List<String> Mamistags= new ArrayList<>();
        List<String> TCTags = new ArrayList<>();
        List<String> APL = new ArrayList<>();
        List<String> StateTags = new ArrayList<>();
        List<String> OrchidThai = new ArrayList<>();
        List<String> TCs = new ArrayList<>();
        List<String> orchidtags = new ArrayList<>();
        List<String> APLCafe = new ArrayList<>();
        TCs.add("https://s3-media0.fl.yelpcdn.com/bphoto/TaSkyfu--hl22IHPBOs11A/348s.jpg");
        LocaleTags.add("Italian");
        LocaleTags.add("Bar");
        LocaleTags.add("Sit Down");
        APL.add("Cafe");
        Mamistags.add("Bakery");
        Mamistags.add("Pastry");
        Mamistags.add("Dessert");
        APLCafe.add("https://assets.bonappetit.com/photos/57acf62a53e63daf11a4dbee/master/pass/best-ever-grilled-cheese.jpg");
        Landimg.add("https://pbs.twimg.com/media/DAdnuvjXkAEM1DD.jpg");
        TCTags.add("Burgers");
        TCTags.add("Fast Food");
        TCTags.add("Fries");
        TCTags.add("Greek");
        TCTags.add("Souvlaki");
        orchidtags.add("Thai");
        orchidtags.add("Sit Down");
        StateTags.add("Grill");
        StateTags.add("Sit Down");
        OrchidThai.add("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSf07H5g5uNXM9BE0voi2ehYW4Z5tQRcTWEKA5W6G104ceeSlys");
        StateTags.add("Bar");
        StateTags.add("Pizza");
        StateTags.add("Burgers");
        State.add("https://www.stateandmain.ca/themes/stateandmain/images/yellow_logo.jpg");
        Mamis.add("https://www.doorsopenontario.on.ca//assets/inventory_site/DOAurora/Pine-Orchard-Quaker-Meeting-House/Quaker-Meeting-House-4.jpg");
        Locale.add("https://chowhound1.cbsistatic.com/thumbnail/620/0/chowhound1.cbsistatic.com/assets/models/image_uploads/images/50097/original/IMG_0069_1_.JPG");
        AwShucksTags.add("Seafood");
        AwShucksTags.add("Bar");
        AwShucksTags.add("Sit Down");
        AwShucksTags.add("Bistro");
        AwShucks.add("https://d3lawkbdj6aabd.cloudfront.net/singleplatform/image/upload/c_fit/a34d614b5369268c6af90162c9fee72d9c34d858.jpg");
        AwShucks.add("https://i.pinimg.com/originals/07/d4/68/07d4687101855f19cf2a288a2242358b.png");
        AwShucks.add("https://ssmscdn.yp.ca/image/resize/72634c39-3fab-4ddb-a32b-6a9bec230a54/ypui-d-mp-pic-gal-lg/aw-shucks-seafood-bar-bistro-3.jpg");
        Locale.add("https://s3-media0.fl.yelpcdn.com/bphoto/2UGccUjEJfYXg-SCbNZcSA/o.jpg");
        Locale.add("https://media-cdn.tripadvisor.com/media/photo-s/13/e8/8e/e3/tagliata.jpg");
        Mamis.add("https://www.mamisbakery.ca/wp-content/uploads/2017/05/18216501_1680966685541874_5852803878021698333_o-898x1024.jpg");
        Mamis.add("https://s3-media0.fl.yelpcdn.com/bphoto/6PWRF_NqcfS5KWE2Om6Cgw/o.jpg");
        Mamis.add("https://www.mamisbakery.ca/wp-content/uploads/2017/05/17211894_1659860494319160_687167346814916201_o-855x1024.jpg");
        TCs.add("https://s3-media0.fl.yelpcdn.com/bphoto/TaSkyfu--hl22IHPBOs11A/348s.jpg");
        TCs.add("https://b.zmtcdn.com/data/pictures/6/17737726/5f7a58aa5f2f7b2a00f2979786bae0cd_featured_v2.jpg");
        Landimg.add("https://s3-media0.fl.yelpcdn.com/bphoto/a0aTb8VAkbg7u9Sok99v6Q/348s.jpg");
        Landimg.add("https://s3-media0.fl.yelpcdn.com/bphoto/hG7AiVQftb4bnq6Vjubg9g/348s.jpg");
        State.add("https://s3-media0.fl.yelpcdn.com/bphoto/yTCPgoTmMWc0nX8ZMRQGjA/348s.jpg");
        State.add("https://s3-media0.fl.yelpcdn.com/bphoto/mt6yYlsydyqzFcLULRgCHQ/o.jpg");
        OrchidThai.add("https://media-cdn.tripadvisor.com/media/photo-s/04/8e/91/6a/orchid-thai.jpg");
        OrchidThai.add("https://media-cdn.tripadvisor.com/media/photo-s/11/68/81/0e/18-pad-thai-takeout.jpg");
        poiList.add(new PointOfInterest("a", "15230 Yonge St, Aurora, ON L4G 1P2", "Aw, Shucks! Aurora Oyster Bar & Bistro", "Stylish, upbeat seafood specialist features an oyster bar, live entertainment & patio dining.", "(905) 727-5100", AwShucksTags));
        poiList.add(new PointOfInterest("b", "14845 Yonge St, Aurora, ON L4G 1N1", "Locale Aurora", "Local Aurora restaurant with fantastic food, great selection of wines and atmosphere!", "(905) 503-4800", LocaleTags));
        poiList.add(new PointOfInterest("c", "15114 Yonge St, Aurora, ON L4G 1M2", "MaMi's Bakery", "Delicious Treats and Quality Cakes", "(416) 559-9564", Mamistags));
        poiList.add(new PointOfInterest("d", "15198 Yonge St, Aurora, ON L4G 1L9", "T.C's Burgers", "Longtime, mom-&-pop counter serve turning out burgers with fries, along with souvlaki & gyros", "(905) 841-1380", TCTags));
        poiList.add(new PointOfInterest("e", "15531 Yonge St, Aurora, ON L4G 1P3", "ShawarmaLand", "Unpretentious counter serve providing shawarma plates, falafel & other familiar Middle Eastern eats.", "(905) 503-6655", ShawarTags));
        poiList.add(new PointOfInterest("f", "14760 Yonge St, Aurora, ON L4G 7H8", "State & Main Kitchen & Bar", "State and Main is an urban inspired North American Kitchen & Bar serving delicious burgers, fish tacos, flatbreads, salads and more!", "(905) 503-7900", StateTags));
        poiList.add(new PointOfInterest("g", "15474 Yonge St, Aurora, ON L4G 1P2", "Orchid Thai", "Relaxed choice with eclectic decor, plating up traditional Thai dishes & tropical cocktails.", "(905) 726-2988", orchidtags));
        poiList.add(new PointOfInterest("h", "15145 Yonge St, Aurora, ON L4G 1M1", "APL Cafe", "Little cafe serving baked goods and sandwiches at the Aurora Public Library", "(905) 727-9494", APL));
        PointOfInterest tc = new PointOfInterest("d", "15198 Yonge St, Aurora, ON L4G 1L9", "T.C's Burgers", "Longtime, mom-&-pop counter serve turning out burgers with fries, along with souvlaki & gyros", "(905) 841-1380", TCTags);
        couponList.add(new Coupon("1", "Aw, Shucks!", "20% off Entree's",AwShucks, new PointOfInterest("a","15230 Yonge St, Aurora, ON L4G 1P2", "Aw, Shucks! Aurora Oyster Bar & Bistro", "Stylish, upbeat seafood specialist features an oyster bar, live entertainment & patio dining.", "(905) 727-5100",AwShucksTags)));
        couponList.add(new Coupon("2", "LOCALE", "5$ Drinks",Locale, new PointOfInterest("b","14845 Yonge St, Aurora, ON L4G 1N1","Locale Aurora","Local Aurora restaurant with fantastic food, great selection of wines and atmosphere!","(905) 503-4800",LocaleTags)));
        couponList.add(new Coupon("3", "MaMi's Bakery", "15% off Fresh Cookies", Mamis, new PointOfInterest("c","15114 Yonge St, Aurora, ON L4G 1M2","MaMi's Bakery","Delicious Treats and Quality Cakes","(416) 559-9564",Mamistags)));
        couponList.add(new Coupon("4", "T.C's Burgers", "Free Medium Fries", TCs, tc));
        couponList.add(new Coupon("4", "T.C's Burgers", "2 can dine for $12", TCs, tc));
        couponList.add(new Coupon("5", "ShawarmaLand", "30% Your Next Order",Landimg , new PointOfInterest("e","15531 Yonge St, Aurora, ON L4G 1P3","ShawarmaLand","Unpretentious counter serve providing shawarma plates, falafel & other familiar Middle Eastern eats.","(905) 503-6655",ShawarTags)));
        couponList.add(new Coupon("6", "State & Main", "Free Margaritas", State, new PointOfInterest("f", "14760 Yonge St, Aurora, ON L4G 7H8","State & Main Kitchen & Bar","State and Main is an urban inspired North American Kitchen & Bar serving delicious burgers, fish tacos, flatbreads, salads and more!","(905) 503-7900",StateTags)));
        couponList.add(new Coupon("7", "Orchid Thai", "Free Miso Soup", OrchidThai, new PointOfInterest("g", "15474 Yonge St, Aurora, ON L4G 1P2", "Orchid Thai", "Relaxed choice with eclectic decor, plating up traditional Thai dishes & tropical cocktails.","(905) 726-2988",orchidtags)));
        couponList.add(new Coupon("8", "APL Cafe", "Free Combo Upgrade", APLCafe, new PointOfInterest("h", "15145 Yonge St, Aurora, ON L4G 1M1", "APL Cafe", "Little cafe serving baked goods and sandwiches at the Aurora Public Library","(905) 727-9494",APL)));


        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            for (Coupon c :
                    couponList) {
                addresses = geocoder.getFromLocationName(c.getPointOfInterest().getAddress(), 1);
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                Location destination = new Location(LocationManager.GPS_PROVIDER);
                destination.setLatitude(latitude);
                destination.setLongitude(longitude);

//                System.out.println(location.distanceTo(destination));

                if (location.distanceTo(destination) < distance_meters) {
                    active_coupon_list.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (active_coupon_list.isEmpty()) {
            active_coupon_list = couponList;
        }


        search_bar = findViewById(R.id.search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search_text = s.toString();

                List<PointOfInterest> tempPOIList = new ArrayList<>();

                FloatingActionButton settings = findViewById(R.id.settings);

                if (!search_text.isEmpty()) {
                    settings.setVisibility(View.INVISIBLE);
                    for (PointOfInterest poi :
                            poiList) {
                        if (poi.getName().toLowerCase().contains(search_text.toLowerCase())) {
                            tempPOIList.add(poi);
                        } else {
                            for (String tag :
                                    poi.getTags()) {
                                if (tag.toLowerCase().contains(search_text.toLowerCase())) {
                                    tempPOIList.add(poi);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    settings.setVisibility(View.VISIBLE);
                }

                searchListView = findViewById(R.id.search_options_list);
                SearchListAdapter adapter1 = new SearchListAdapter(MapsActivity.this, R.layout.search_layout, tempPOIList);
                searchListView.setAdapter(adapter1);

            }
        });
        search_bar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    searchListView.setVisibility(View.VISIBLE);
                    if (!search_text.isEmpty()) {
                        FloatingActionButton settings = findViewById(R.id.settings);
                        settings.setVisibility(View.INVISIBLE);
                    }
                } else {
                    searchListView.setVisibility(View.INVISIBLE);
                    FloatingActionButton settings = findViewById(R.id.settings);
                    settings.setVisibility(View.VISIBLE);
                }
            }
        });

        couponListView = findViewById(R.id.coupon_list);

        CouponListAdapter adapter = new CouponListAdapter(this, R.layout.coupon_layout, active_coupon_list);
        couponListView.setAdapter(adapter);


        searchListView = findViewById(R.id.search_options_list);
        SearchListAdapter adapter1 = new SearchListAdapter(this, R.layout.search_layout, new ArrayList<PointOfInterest>());
        searchListView.setAdapter(adapter1);

        ViewGroup.LayoutParams params = couponListView.getLayoutParams();
        View view = adapter.getView(1, null, couponListView);
        view.measure(0, 0);
        params.height = active_coupon_list.size() * view.getMeasuredHeight() + (couponListView.getDividerHeight() * (adapter.getCount() - 1));
        couponListView.setLayoutParams(params);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        View map_view = findViewById(R.id.main_map);
        ViewGroup.LayoutParams params2 = map_view.getLayoutParams();
        params2.height = (int)(height - view.getMeasuredHeight()*0.6);
        map_view.setLayoutParams(params2);



        scrollView = findViewById(R.id.main_scroll_view);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int temp = scrollView.getScrollY();
                search_bar.clearFocus();
                scrollView.setScrollY(temp);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(R.id.main_layout).getWindowToken(), 0);
                return false;
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                ScrollView scrollView = findViewById(R.id.main_scroll_view);
                if (!scrollChange) {
                    scrollView.setScrollY(0);
                    ObjectAnimator.ofInt(scrollView, "scrollY",  700).setDuration(1000).start();
                    scrollChange = true;
//                } else {
//                    Display display = getWindowManager().getDefaultDisplay();
//                    Point size = new Point();
//                    display.getSize(size);
//                    int width = size.x;
//                    int height = size.y;
//                    View map_view = findViewById(R.id.main_map);
//                    map_view.measure(0, 0);
//                    if (last_pos != -1) {
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mMap.getProjection().fromScreenLocation(
//                                new Point(width/2, (map_view.getHeight()/2 - (scrollView.getScrollY() - last_pos)/2)))));
//                    }
//                    last_pos = scrollView.getScrollY();
                }
            }
        });

        // overriding scrollview when scrolling the map fragment
        View transparentTouchPanel = findViewById (R.id.transparent_map_overlay);

        transparentTouchPanel.setLayoutParams(params2);

        transparentTouchPanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int temp = scrollView.getScrollY();
                search_bar.clearFocus();
                scrollView.setScrollY(temp);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(R.id.main_layout).getWindowToken(), 0);
                if (event.getAction() == event.ACTION_UP) {
                    v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (search_bar.hasFocus()) {
            int temp = scrollView.getScrollY();
            search_bar.clearFocus();
            scrollView.setScrollY(temp);
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(findViewById(R.id.main_layout).getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_mode));

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        try {
            addresses = geocoder.getFromLocationName("2501 Rutherford Rd, Concord, ON L4K 2N6", 1);
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
            // Add a marker and move the camera to the coupon location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude() + 0.005, location.getLongitude()), 15.0f));
        } catch (IOException e) {
            e.printStackTrace();
        }

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 30);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Function testing average speed every 15 seconds to verify the user is walking/biking
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void open_coupon(View view) {
        Intent intent = new Intent(this, CouponActivity.class);
        TextView id_view = view.findViewById(R.id.coupon_id);
        String id = (String)id_view.getText();

        intent.putExtra("id", id);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.none);
    }

    public void go_to_restaurant_from_search(View view) {
        Intent intent = new Intent(this, POIActivity.class);
        TextView hidden_view = view.findViewById(R.id.hidden_restaurant_id);
        String id = (String) hidden_view.getText();

        intent.putExtra("id", id);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
    }
    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    try {
                        StringBuilder sb = new StringBuilder();
                        System.out.println(1);
                        con.getInputStream();
                        System.out.println(23);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        System.out.println(2);
                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        return sb.toString().trim();
                    }
                    finally {
                        con.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("BROKEN");
                return null;
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        System.out.println("JSON");
        System.out.println(json);
        JSONArray jsonArray = new JSONArray(json);
        System.out.println("JSON 2");
        System.out.println(jsonArray);
        String[] stocks = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            stocks[i] = obj.getString("name") + " " + obj.getString("price");
        }
        System.out.println("stocks");
        System.out.println(stocks);
    }

    public void open_settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_left, R.anim.none);
    }
    public void WriteFile(String textToSave){

        try{
            FileOutputStream fileOutputStream = openFileOutput("SavedFile.txt",MODE_PRIVATE);
            fileOutputStream.write(textToSave.getBytes());
            fileOutputStream.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public String ReadFile(){
        try{
            FileInputStream fileInputStream = openFileInput("SavedFile.txt");
            System.out.println("doesnt get here");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while((lines=bufferedReader.readLine())!=null){
                stringBuffer.append(lines);
            }
            return stringBuffer.toString();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        WriteFile(total_walked);
    }
}
