package com.example.firebase_mvp_app.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_mvp_app.Core.MainActivityContract;
import com.example.firebase_mvp_app.Model.Player;
import com.example.firebase_mvp_app.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    public ArrayList<Player> players;
    public  onPlayerClickListener mOnPlayerListener;

    public RecyclerViewAdapter(ArrayList<Player> players, onPlayerClickListener mOnPlayerListener) {
        this.players = players;
        this.mOnPlayerListener = mOnPlayerListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new RecyclerViewHolder(v,mOnPlayerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.Name.setText(players.get(position).getName());
        holder.Age.setText(players.get(position).getAge());
        holder.Position.setText(players.get(position).getPosition());


    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name;
        TextView Age;
        TextView Position;
        Button Update;
        Button Delete;

        public onPlayerClickListener mClickListener;
        public RecyclerViewHolder(@NonNull View itemView , onPlayerClickListener onPlayerClickListener) {
            super(itemView);

            this.mClickListener=onPlayerClickListener;
            Name = itemView.findViewById(R.id.tv_name);
            Age = itemView.findViewById(R.id.tv_age);
            Position = itemView.findViewById(R.id.tv_position);
            Update = itemView.findViewById(R.id.btn_update);
            Delete = itemView.findViewById(R.id.btn_delete);

            Update.setOnClickListener(this);
            Delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_update:
                mClickListener.onPlayerUpdateClick(getAdapterPosition());
                break;
                case R.id.btn_delete:
                mClickListener.onPlayerDeleteClick(getAdapterPosition());
                break;

            }

        }
    }

    public  interface  onPlayerClickListener
    {
        void onPlayerUpdateClick(int position);
        void onPlayerDeleteClick(int position);
    }
}
