package com.example.myapp;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.List;

public class ApiServiceTest {
    private MockWebServer mockWebServer;
    private JsonPlaceholderApi api;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.trampoline()))
                .build();

        api = retrofit.create(JsonPlaceholderApi.class);
    }

    @Test
    public void testFetchPosts_Success() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("[{\"id\":1,\"title\":\"Test Post\"}]")
                .setResponseCode(200));

        Single<List<Post>> single = api.getPosts();
        TestObserver<List<Post>> testObserver = single.test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(posts -> posts.size() == 1 && posts.get(0).getTitle().equals("Test Post"));
    }

    @Test
    public void testFetchPosts_ServerError() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        Single<List<Post>> single = api.getPosts();
        TestObserver<List<Post>> testObserver = single.test();

        testObserver.assertError(Throwable.class);
    }

    @Test
    public void testFetchPosts_EmptyResponse() {
        mockWebServer.enqueue(new MockResponse().setBody("[]").setResponseCode(200));

        Single<List<Post>> single = api.getPosts();
        TestObserver<List<Post>> testObserver = single.test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(posts -> posts.isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }
}