package Utilities;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.parse.ParseUser;

/**
 * Created by Rogger on 06/10/2015.
 */
public class Utils {

    public static User user = new User();
    public static ParseUser parseUser = null;
    public static byte[] imageBuffer = null;
    public static boolean bufferingImage = false;
    public static Polyline ruta;
    public static Marker startPoint;
    public static Marker endPoint;


}
