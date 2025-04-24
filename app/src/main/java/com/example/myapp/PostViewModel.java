package com.example.myapp;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PostViewModel extends AndroidViewModel {
    private final PostRepository repository;
    private final LiveData<List<Post>> postsLiveData;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public PostViewModel(Application application) {
        super(application);
        repository = new PostRepository(application);
        postsLiveData = repository.getPosts();


        Disposable apiRequest = repository.fetchPostsFromApi()
                .subscribe(posts -> {  }, Throwable::printStackTrace);

        disposable.add(apiRequest);
    }

    public LiveData<List<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Очищаем подписки при уничтожении ViewModel
    }
}