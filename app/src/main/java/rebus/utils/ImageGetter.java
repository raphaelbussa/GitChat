package rebus.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.koushikdutta.ion.Ion;

/**
 * Created by raphaelbussa on 18/02/16.
 */
public class ImageGetter implements Html.ImageGetter {

    private static final String TAG = ImageGetter.class.getName();

    private Context context;

    public ImageGetter(Context context) {
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        try {
            Bitmap bitmap = Ion.with(context).load(source).asBitmap().get();
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

}
