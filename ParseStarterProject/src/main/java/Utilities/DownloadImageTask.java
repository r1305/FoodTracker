package Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    boolean saveOnBuffer;

    public DownloadImageTask(ImageView bmImage, boolean saveOnBuffer) {
        this.bmImage = bmImage;
        this.saveOnBuffer = saveOnBuffer;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            URL img_value = new URL(urldisplay);

            HttpURLConnection connection = (HttpURLConnection) img_value.openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setDoInput(true);
            connection.connect();
            mIcon11 = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (saveOnBuffer) {
            Utils.bufferingImage = true;
            new BitmapToByteArray().execute(result);
        }

        bmImage.setImageBitmap(result);
    }
}
