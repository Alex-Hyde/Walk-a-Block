package hyde.development.walkablockmainproject;

import java.util.ArrayList;
import java.util.List;

public class PointOfInterest {
    private String id;
    private String address;
    private String name;
    private String description;  // fast food or fancy or something
    private String phone;
    private List<String> tags;
    private List<Coupon> couponList = new ArrayList<>();

    PointOfInterest(
            String id,
            String address,
            String name,
            String description,
            String phone,
            List<String> tags
    ) {

        this.id = id;
        this.address = address;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.tags = tags;
        MapsActivity.id_POI_dict.put(id, this);
    }

    public void add_coupon(Coupon coupon) {
        couponList.add(coupon);
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getTags() {
        return tags;
    }
}
