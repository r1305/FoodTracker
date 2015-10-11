package Utilities;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.parse.ParseUser;

import java.util.Calendar;

/**
 * Created by Rogger on 06/10/2015.
 */
public class Utils {

    public static User user = new User();
    public static ParseUser parseUser = null;
    public static byte[] imageBuffer = null;
    public static boolean bufferingImage = false;
    public static Calendar datePick = Calendar.getInstance();
    public static Marker startPoint;
    public static Marker endPoint;
    public static String startAddress;
    public static String endAddress;
    public static Polyline ruta;
    public static Bitmap bit;

}
