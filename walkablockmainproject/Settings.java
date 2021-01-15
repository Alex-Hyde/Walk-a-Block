package hyde.development.walkablockmainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView distance_walked = findViewById(R.id.total_walked_value);
        System.out.println(MapsActivity.total_walked);
        float distance_to = Float.parseFloat("2300.1")/1000;
        String string;
        if (distance_to < 1) {
            string = Integer.toString((int)(distance_to * 1000)) + "m";
        } else if (distance_to < 10) {
            string = String.format("%.1f", distance_to) + "km";
        } else {
            string = Integer.toString((int) distance_to) + "km";
        }
        distance_walked.setText(string);
    }

    public void go_home(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.none);
    }

    public void set1k(View view) {
        MapsActivity.distance_meters = 1000;
    }

    public void set200(View view) {
        MapsActivity.distance_meters = 200;
    }

    public void set2k(View view) {
        MapsActivity.distance_meters = 2000;
    }
}
