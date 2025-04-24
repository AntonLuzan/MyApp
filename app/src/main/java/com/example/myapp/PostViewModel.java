package com.example.myapp;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PostViewModel extends AndroidViewModel {
    private final PostRepository repository;
    private final LiveData<List<Post>> postsLiveData;
    private final MutableLiveData<List<Post>> favoritesLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    public PostViewModel(Application application) {
        super(application);
        repository = new PostRepository(application);
        postsLiveData = repository.getPosts();

        // Если репозиторий может предоставить избранные посты, можно сразу их получить:
        // favoritesLiveData = repository.getFavoritePosts();

        Disposable apiRequest = repository.fetchPostsFromApi()
                .subscribe(posts -> {
                    // Здесь можно обработать полученные посты и, например, обновить favoritesLiveData,
                    // если это необходимо по вашей логике.
                }, Throwable::printStackTrace);
        disposable.add(apiRequest);
    }

    public LiveData<List<Post>> getPostsLiveData() {
        return postsLiveData;
    }

    public LiveData<List<Post>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public void updateFavorites(List<Post> favoritePosts) {
        favoritesLiveData.setValue(favoritePosts);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear(); // Очищаем подписки при уничтожении ViewModel
    }
}