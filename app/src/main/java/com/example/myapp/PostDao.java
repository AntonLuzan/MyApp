package com.example.myapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PostDao {

    // Получение всех постов
    @Query("SELECT * FROM posts")
    Flowable<List<Post>> getAllPosts();

    // Вставка списка постов
    @Insert
    void insertAll(List<Post> posts);

}