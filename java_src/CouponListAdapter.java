package hyde.development.walkablockmainproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CouponListAdapter extends ArrayAdapter<Coupon> {

    Context context;
    int resource;
    List<Coupon> couponList;

    public CouponListAdapter(Context context, int resource, List<Coupon> couponList) {
        super(context, resource, couponList);

        this.context = context;
        this.resource = resource;
        this.couponList = couponList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resource, null);

        if (couponList.size() > position) {
            TextView title = view.findViewById(R.id.coupon_title);TextView description = view.findViewById(R.id.coupon_description);
            ImageView image = view.findViewById(R.id.coupon_image);
            TextView id = view.findViewById(R.id.coupon_id);

            Coupon coupon = couponList.get(position);

            title.setText(coupon.getName());
            description.setText(coupon.getDescription());
            Picasso.with(context).load(coupon.getImageURL().get(0)).resize(130, 130)
                    .centerCrop().into(image);
            id.setText(coupon.getId());
        }
        return view;
    }
}
