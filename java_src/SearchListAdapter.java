package hyde.development.walkablockmainproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<PointOfInterest> {

    Context context;
    int resource;
    List<PointOfInterest> poiList;

    public SearchListAdapter(Context context, int resource, List<PointOfInterest> poiList) {
        super(context, resource, poiList);

        this.context = context;
        this.resource = resource;
        this.poiList = poiList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resource, null);

        if (poiList.size() > position) {
            TextView search_option = view.findViewById(R.id.search_option_text);
            search_option.setText(poiList.get(position).getName());

            TextView hidden_id = view.findViewById(R.id.hidden_restaurant_id);
            hidden_id.setText(poiList.get(position).getId());
        }
        return view;
    }
}