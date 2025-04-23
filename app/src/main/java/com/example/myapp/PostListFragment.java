package com.example.myapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.util.ArrayList;

public class PostListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private PostRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // Инициализация репозитория для работы с данными
        repository = new PostRepository(requireContext());

        // Настройка RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(new ArrayList<>(), post -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", post.getId());
            bundle.putString("title", post.getTitle());
            bundle.putString("body", post.getBody());


            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        // Загрузка данных
        loadData();

        return view;
    }

    private void loadData() {
        disposable.add(repository.getPostsFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> adapter.updatePosts(posts), Throwable::printStackTrace));

        disposable.add(repository.fetchPostsFromApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {}, Throwable::printStackTrace));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear(); // Очищаем подписки, чтобы избежать утечек памяти
    }
}