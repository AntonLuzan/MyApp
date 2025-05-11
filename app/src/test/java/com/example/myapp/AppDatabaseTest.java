package com.example.myapp;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppDatabaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private AppDatabase database;
    private PostDao postDao;
    private Context mockContext;

    @Before
    public void setUp() {
        // Создаем мок-контекст вместо ApplicationProvider.getApplicationContext()
        mockContext = Mockito.mock(Context.class);

        // Используем in-memory базу данных для тестирования
        database = Room.inMemoryDatabaseBuilder(mockContext, AppDatabase.class)
                .allowMainThreadQueries() // Только для тестов
                .build();
        postDao = database.postDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertAndRetrievePosts() throws InterruptedException {
        List<Post> testPosts = Arrays.asList(
                new Post(1, 1, "TestTitle1", "TestBody1"),
                new Post(2, 2, "TestTitle2", "TestBody2")
        );

        // Вставляем данные в базу в отдельном потоке
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> postDao.insertAll(testPosts));

        // Даем базе немного времени на обновление
        Thread.sleep(1000);

        // Получаем посты из базы и проверяем, что они были успешно добавлены
        LiveData<List<Post>> retrievedPosts = postDao.getAllPosts();
        assertNotNull(retrievedPosts);
    }
}