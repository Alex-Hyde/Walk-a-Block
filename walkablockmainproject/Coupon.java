package hyde.development.walkablockmainproject;

import java.util.List;

public class Coupon {
    private String id;
    private String name;
    private String description;
    private List<String> imageURL;
    private PointOfInterest pointOfInterest;

    Coupon(
            String id,
            String name,
            String description,
            List<String> imageURL,
            PointOfInterest pointOfInterest
    ) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.pointOfInterest = pointOfInterest;
        this.pointOfInterest.add_coupon(this);
        MapsActivity.id_coupon_dict.put(id, this);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageURL() {
        return imageURL;
    }

    public PointOfInterest getPointOfInterest() {
        return pointOfInterest;
    }

}
