package com.example.myapp;

import java.util.List;
import retrofit2.http.GET;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Flowable;

public interface JsonPlaceholderApi {

    @GET("posts")
    Single<List<Post>> getPosts(); // Асинхронная загрузка через RxJava Single

    @GET("posts")
    Flowable<List<Post>> getPostsFlowable(); // Если потребуется потоковое получение данных
}