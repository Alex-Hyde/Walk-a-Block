package hyde.development.walkablockmainproject;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class POIActivity extends AppCompatActivity {

    private PointOfInterest pointOfInterest;
    ListView couponListView;
    private Boolean scrollChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        pointOfInterest = MapsActivity.id_POI_dict.get(getIntent().getStringExtra("id"));

        TextView title = findViewById(R.id.title_POI);
        title.setText(pointOfInterest.getName());
        TextView title_invisible_background = findViewById(R.id.background_behind_POI_title);
        title_invisible_background.setText(pointOfInterest.getName());
        TextView description = findViewById(R.id.description_POI);
        description.setText(pointOfInterest.getDescription());
        ImageView image = findViewById(R.id.picture_POI);
        Picasso.with(this).load(pointOfInterest.getCouponList().get(0).getImageURL().get(0)).resize(width-50, width-50)
                .centerCrop().into(image);

        couponListView = findViewById(R.id.coupon_list_POI);

        CouponListAdapter adapter = new CouponListAdapter(this, R.layout.coupon_layout, pointOfInterest.getCouponList());
        couponListView.setAdapter(adapter);

        ViewGroup.LayoutParams params = couponListView.getLayoutParams();
        View view = adapter.getView(1, null, couponListView);
        view.measure(0, 0);
        params.height = pointOfInterest.getCouponList().size() * view.getMeasuredHeight() + (couponListView.getDividerHeight() * (adapter.getCount() - 1));
        couponListView.setLayoutParams(params);


        ScrollView scrollView = findViewById(R.id.scroll_view_poi_layout);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                ScrollView scrollView = findViewById(R.id.scroll_view_poi_layout);
                if (!scrollChange) {
                    scrollView.setScrollY(0);
                    scrollChange = true;
                }
            }
        });

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.title_floating_button);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    public void open_coupon(View view) {
        Intent intent = new Intent(this, CouponActivity.class);
        TextView id_view = view.findViewById(R.id.coupon_id);
        String id = (String)id_view.getText();

        intent.putExtra("id", id);

        startActivity(intent);
    }

    public void dial_phone(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pointOfInterest.getPhone(), null)));
    }

    public void go_home(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
