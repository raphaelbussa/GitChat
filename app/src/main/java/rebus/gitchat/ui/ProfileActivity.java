package rebus.gitchat.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.klinker.android.sliding.SlidingActivity;

import java.util.List;

import rebus.gitchat.R;
import rebus.utils.Utils;

/**
 * Created by raphaelbussa on 23/08/16.
 */
public class ProfileActivity extends SlidingActivity {

    @Override
    public void init(Bundle savedInstanceState) {
        setContent(R.layout.activity_profile);
        String username = "";
        if (!getIntent().hasExtra("USERNAME")) {
            Uri data = getIntent().getData();
            List<String> pathSegments = data.getPathSegments();
            if (!pathSegments.isEmpty()) {
                username = pathSegments.get(pathSegments.size() - 1);
            }
        } else {
            username = getIntent().getExtras().getString("USERNAME");
        }
        DownloadImage downloadImage = new DownloadImage();
        downloadImage.execute(username);
        if (username != null) setTitle(username);
        setPrimaryColors(ContextCompat.getColor(ProfileActivity.this, R.color.primary), ContextCompat.getColor(ProfileActivity.this, R.color.primary_dark));
    }

    private class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return Glide.with(ProfileActivity.this).load(Utils.getAvatarUrl(strings[0], false)).asBitmap().into(460, 460).get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) setImage(bitmap);
        }

    }

}
