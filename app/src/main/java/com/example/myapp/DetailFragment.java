package com.example.myapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private TextView titleTextView;
    private TextView bodyTextView;

    public DetailFragment() {
        // Обязательный пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Инфлейтим layout для детального экрана (создайте файл fragment_detail.xml)
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = view.findViewById(R.id.text_view_title_detail);
        bodyTextView = view.findViewById(R.id.text_view_body_detail);

        // Получаем данные из аргументов
        Bundle args = getArguments();
        if (args != null) {
            titleTextView.setText(args.getString("title"));
            bodyTextView.setText(args.getString("body"));
        }
        else {
            Log.d("DetailFragment", "Аргументы не найдены!");
        }
        return view;
    }

}