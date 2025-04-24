package com.example.myapp;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class PostRepository {
    private JsonPlaceholderApi api;
    private PostDao postDao;

    public PostRepository(Context context) {
        api = RetrofitInstance.getInstance().create(JsonPlaceholderApi.class);

        AppDatabase db = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "app_database")
                .fallbackToDestructiveMigration()
                .build();
        postDao = db.postDao();
    }

    // Получение данных из API – сохранение в БД (UI обновится через LiveData из базы)
    public Single<List<Post>> fetchPostsFromApi() {
        return api.getPosts()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(posts -> postDao.insertAll(posts));
    }

    // Доступ к постам непосредственно из базы через LiveData
    public LiveData<List<Post>> getPosts() {
        return postDao.getAllPosts();
    }
}