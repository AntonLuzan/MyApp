package com.example.myapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import io.reactivex.rxjava3.core.Single;

public interface JsonPlaceholderApi {
    @GET("posts")
    Single<List<Post>> getPosts(); // Используем RxJava Single для асинхронной загрузки
}