package com.example.myapp;

import android.content.Context;
import androidx.room.Room;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

public class PostRepository {
    private JsonPlaceholderApi api;
    private PostDao postDao;

    public PostRepository(Context context) {
        api = RetrofitInstance.getInstance().create(JsonPlaceholderApi.class);

        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "app_database")
                .fallbackToDestructiveMigration()
                .build();
        postDao = db.postDao();
    }

    // Получение постов из API и сохранение их в локальную БД
    public Single<List<Post>> fetchPostsFromApi() {
        return api.getPosts()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(posts -> postDao.insertAll(posts));
    }

    // Получение постов из локальной БД
    public Flowable<List<Post>> getPostsFromDatabase() {
        return postDao.getAllPosts()
                .subscribeOn(Schedulers.io());
    }
}