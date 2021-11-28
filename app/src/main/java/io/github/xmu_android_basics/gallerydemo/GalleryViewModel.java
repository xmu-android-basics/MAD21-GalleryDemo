package io.github.xmu_android_basics.gallerydemo;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
    private MutableLiveData<List<PhotoItem>> _photoListLive = new MutableLiveData<>();

    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PhotoItem>> getPhotoListLive() {
        return _photoListLive;
    }

    public void fetchData(String keyword) {
        GsonRequest<Pixabay> request = new GsonRequest<>(
                geturl(keyword),
                Pixabay.class,
                response -> _photoListLive.setValue(Arrays.asList(response.getHits()))
        );

        VolleySingleton.getInstance(getApplication()).addToRequestQueue(request);
    }

    private String geturl(String key) {
        if (TextUtils.isEmpty(key)) {
            key = "sun";
        }
        return String.format("https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=%s&per_page=10", key);
    }
}
