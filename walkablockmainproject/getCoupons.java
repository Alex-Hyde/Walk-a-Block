//package hyde.development.walkablockmainproject;
//
//import android.os.AsyncTask;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.List;
//
//class RetrieveFeedTask extends AsyncTask<String, Void, MapsActivity> {
//
//    private Exception exception;
//    private List<Coupon> couponList;
//
//    protected MapsActivity doInBackground(String... urls) {
//        try {
//            // Get the input stream through URL Connection
//            URL url = null;
//            url = new URL("https://walkablock.000webhostapp.com/keonsbigshnoze.htm");
//            URLConnection con = url.openConnection();
//            InputStream is =con.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//            String line;
//            List<String> info = new ArrayList<>();
//
//            while ((line = br.readLine()) != null) {
//                info.add(line);
//            }
//            couponList = get_data3(info);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        MapsActivity.couponList = couponList;
//
//    protected void onPostExecute(RSSFeed feed) {
//        // TODO: check this.exception
//        // TODO: do something with the feed
//    }
//    private static List<Coupon> get_data3(List<String> arr){
//        List<Coupon> Coupons = new ArrayList<>();
//        arr = arr.subList(3,arr.indexOf("@e")+1);
//        while (arr.size()>0) {
//            List<String> items = arr.subList(0, arr.indexOf("@n")+1);
//            if (items.size() > 0) {
//                String PID = items.get(0);
//                String CID = items.get(1);
//                String Title = items.get(2);
//                String Address = items.get(arr.indexOf("@t2")+1);
//                String Phone = items.get(arr.indexOf("@t2")+2);
//                List<String> tags = items.subList(arr.indexOf("@t")+1, arr.indexOf("@t2"));
//                List<String> URLs = items.subList(arr.indexOf("@x") + 1, arr.indexOf("@y"));
//                String Desc = items.get(arr.indexOf("@y") + 1);
//                String CouponDesc = items.get(arr.indexOf("@y") + 2);
//                arr.subList(0, arr.indexOf("@n") + 1).clear();
//                PointOfInterest p = new PointOfInterest(PID, Address, Title, Desc, Phone, tags);
//                Coupon c = new Coupon(CID, "name", CouponDesc, URLs, p);
//                Coupons.add(c);
//            }
//            else {
//                break;
//            }
//        }
//        return Coupons;
//    }
//}