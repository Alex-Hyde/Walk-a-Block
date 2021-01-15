//package hyde.development.walkablockmainproject;
//
//import android.content.Context;
//import android.graphics.Point;
//import android.support.annotation.NonNull;
//import android.support.v4.view.PagerAdapter;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class ImageAdapter extends PagerAdapter {
//
//    private Context context;
//    private List<String> mImageURLS;
//
//    public ImageAdapter(Context context, List<String> mImageURLS) {
//        this.context = context;
//        this.mImageURLS = mImageURLS;
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//        return view == o;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View view = inflater.inflate(R.layout.image_layout, null);
//
//        Picasso.with(context).load(mImageURLS.get(position)).resize(POIActivity.width-50, POIActivity.width-50)
//                .centerCrop().into((ImageView) view.findViewById(R.id.picture_POI));
//        container.addView(view, position);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((ImageView) object);
//    }
//}
