package com.example.myapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapp.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle args = getArguments();
        if (args != null) {
            binding.textViewTitleDetail.setText(args.getString("postTitle", "Нет заголовка")); // Исправлен ключ
            binding.textViewBodyDetail.setText(args.getString("body", "Нет содержимого"));
        } else {
            binding.textViewTitleDetail.setText("Нет заголовка");
            binding.textViewBodyDetail.setText("Нет содержимого");
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}