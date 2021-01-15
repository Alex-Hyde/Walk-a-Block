package hyde.development.walkablockmainproject;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CouponActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks, View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private GoogleMap mMap;
    private Coupon coupon;
    private GestureDetector gestureDetector;
    private View grey_overlay;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    boolean recieved = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        gestureDetector = new GestureDetector(this, this);

        coupon = MapsActivity.id_coupon_dict.get(id);

        grey_overlay = findViewById(R.id.grey_overlay);
        grey_overlay.setVisibility(View.INVISIBLE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(coupon.getPointOfInterest().getAddress(), 1);
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            // Add a marker and move the camera to the coupon location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            TextView distance_text = findViewById(R.id.distance_text_view);
            Location destination = new Location(LocationManager.GPS_PROVIDER);
            destination.setLatitude(latitude);
            destination.setLongitude(longitude);
            float distance_to = (location.distanceTo(destination) / 1000);
            String string;
            if (distance_to < 1) {
                string = Integer.toString((int)(distance_to * 1000)) + "m";
            } else if (distance_to < 10) {
                string = String.format("%.1f", distance_to) + "km";
            } else {
                string = Integer.toString((int) distance_to) + "km";
            }
            distance_text.setText(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView title = findViewById(R.id.coupon_title);
        TextView description = findViewById(R.id.coupon_description);
        ImageView image = findViewById(R.id.coupon_image);
        TextView id_view = findViewById(R.id.coupon_id);

        title.setText(coupon.getName());
        description.setText(coupon.getDescription());
        Picasso.with(this).load(coupon.getImageURL().get(0)).resize(130, 130)
                .centerCrop().into(image);
        id_view.setText(coupon.getId());


        // overriding scrollview when scrolling the map fragment
        View transparentTouchPanel = findViewById (R.id.transparent_map_overlay_coupon);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        ViewGroup.LayoutParams params = findViewById(R.id.coupon_map).getLayoutParams();

        params.height = (int)(height + 10 * getResources().getDisplayMetrics().density);
        findViewById(R.id.coupon_map).setLayoutParams(params);
        findViewById(R.id.grey_overlay).setLayoutParams(params);


        ViewGroup.LayoutParams params2 = transparentTouchPanel.getLayoutParams();

        params2.height = (int)(height - 200 * getResources().getDisplayMetrics().density);
        transparentTouchPanel.setLayoutParams(params2);

        transparentTouchPanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        ScrollView scrollView = findViewById(R.id.coupon_scroll_view);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (grey_overlay.getVisibility() == View.VISIBLE) {
                        Intent intent = new Intent(CouponActivity.this, POIActivity.class);

                        intent.putExtra("id", coupon.getPointOfInterest().getId());

                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
                    }
                }
                return false;
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                ScrollView scrollView = findViewById(R.id.coupon_scroll_view);
                if (scrollView.getScrollY() > 8) {
                    grey_overlay.setVisibility(View.VISIBLE);
                } else {
                    grey_overlay.setVisibility(View.INVISIBLE);
                }
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
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_dark_mode));

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(coupon.getPointOfInterest().getAddress(), 1);
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            // Add a marker and move the camera to the coupon location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(coupon.getPointOfInterest().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScrollView scrollView = findViewById(R.id.coupon_scroll_view);
        scrollView.setScrollY(0);
        grey_overlay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(coupon.getPointOfInterest().getAddress(), 1);
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            // Add a marker and move the camera to the coupon location
            TextView distance_text = findViewById(R.id.distance_text_view);
            Location destination = new Location(LocationManager.GPS_PROVIDER);
            destination.setLatitude(latitude);
            destination.setLongitude(longitude);
            float distance_to = (location.distanceTo(destination) / 1000);
            String string;
            if (distance_to < 1) {
                string = (int) (distance_to * 1000) + "m";
                if (distance_to < 0.05 && findViewById(R.id.title_floating_button).getVisibility() == View.INVISIBLE && !recieved) {
                    showCoupon();
                }
            } else if (distance_to < 10) {
                string = String.format("%.1f", distance_to) + "km";
            } else {
                string = (int) distance_to + "km";
            }
            distance_text.setText(string);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Function testing average speed every 15 seconds to verify the user is walking/biking

//        if (started) {
//            if (last_location != null && start_time != 0) {
//                float distance = location.distanceTo(last_location);
//                if (System.currentTimeMillis() - start_time > 5000) {
//                    float speed = distance / 5;
//                    if (speed > 0.5) {
//                        cancelWalk();
//                    }
//                }
//
//            }
//            last_location = location;
//            start_time = System.currentTimeMillis();
//        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void open_coupon(View view) {
    }

    public void go(View view) {
        Button cancel = findViewById(R.id.title_floating_button_cancel);
        view.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            builder.include(new LatLng(location.getLatitude(), location.getLongitude()));
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(coupon.getPointOfInterest().getAddress(), 1);
            double latitude= addresses.get(0).getLatitude();
            double longitude= addresses.get(0).getLongitude();
            builder.include(new LatLng(latitude, longitude));
            LatLngBounds bounds = builder.build();

            int padding = (int)(150 * getResources().getDisplayMetrics().density); // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            mMap.animateCamera(cu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        showCoupon();
    }

    public void cancel(View view) {
        Button go = findViewById(R.id.title_floating_button);
        view.setVisibility(View.INVISIBLE);
        go.setVisibility(View.VISIBLE);
    }

    public void blank(View view) {
    }

    public void close_barcode(View view) {
        View complete = findViewById(R.id.finished_coupon_view);
        complete.setVisibility(View.INVISIBLE);
        onBackPressed();
    }

    public void go_home(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void showCoupon() {
        recieved = true;
        View complete = findViewById(R.id.finished_coupon_view);
        complete.setVisibility(View.VISIBLE);

        TextView restaurant_title = findViewById(R.id.coupon_barcode_title_restaurant);
        restaurant_title.setText(coupon.getPointOfInterest().getName());

        TextView title = findViewById(R.id.coupon_barcode_title);
        title.setText(coupon.getDescription());

        ImageView barcode_image = findViewById(R.id.barcode_image);
        Picasso.with(this).load("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/QR_code_for_mobile_English_Wikipedia.svg/1200px-QR_code_for_mobile_English_Wikipedia.svg.png").resize(130, 130)
                .centerCrop().into(barcode_image);

        TextView barcode_string_code = findViewById(R.id.barcode_string_code);

        int count = 7;
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        barcode_string_code.setText(builder.toString());
    }
}
