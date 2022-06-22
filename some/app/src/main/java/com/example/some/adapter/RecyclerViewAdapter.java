package com.example.some.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.some.R;
import com.example.some.model.Work;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {
    private List<Work> list;
    private ItemListener itemListener;

    public RecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Work> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Work getItemAtPosition(int position) {
        return list.get(position);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Work item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        holder.status.setText(item.getStatus());
        holder.date.setText(item.getDate());
        if (item.isCooperated()) {
            holder.congTac.setText("Làm chung");
        } else {
            holder.congTac.setText("1 mình");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, content, status, date, congTac;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
            status = itemView.findViewById(R.id.tvStatus);
            date = itemView.findViewById(R.id.tvDate);
            congTac = itemView.findViewById(R.id.tvCongTac);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        public void onItemClick(View view, int position);
    }
}
