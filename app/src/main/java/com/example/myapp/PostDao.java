package com.example.myapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapp.Post;

import java.util.List;

@Dao
public interface PostDao {

    // Получение всех постов (LiveData)
    @Query("SELECT * FROM posts")
    LiveData<List<Post>> getAllPosts();

    // Вставка списка постов
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

}
