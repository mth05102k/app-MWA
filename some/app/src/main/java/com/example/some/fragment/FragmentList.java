package com.example.some.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.some.R;
import com.example.some.UpdateDeleteActivity;
import com.example.some.adapter.RecyclerViewAdapter;
import com.example.some.dal.SQLiteHelper;
import com.example.some.model.Work;

import java.util.List;

public class FragmentList extends Fragment implements RecyclerViewAdapter.ItemListener {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Work> listDb = db.getAll();
        adapter.setList(listDb);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Work item = adapter.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("work", item);
        startActivity(intent);
    }

    // Lam tuoi moi lan co doi tuong them vao
    @Override
    public void onResume() {
        super.onResume();
        List<Work> list = db.getAll();
        adapter.setList(list);
    }
}