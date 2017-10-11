package com.learn2crack.recyclerswipeview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn2crack.recyclerswipeview.databinding.RowLayoutBinding;
import com.learn2crack.recyclerswipeview.model.User;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {
    ArrayList<User> list;
    OnCardClickListner onCardClickListner;
    Context mContext;

    public UserAdapter(Context context ,ArrayList<User> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View statusContainer = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new UserAdapter.ViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {;
        User status = list.get(position);
        holder.binding.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, holder.getAdapterPosition());
            }
        });
        holder.bindUser(status);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addUser(User user) {
        list.add(user);
        notifyItemInserted(list.size());
    }

    public int getUserId(int position) {
       return list.get(position).getId();
    }


    public void removeUser(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private RowLayoutBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bindUser(User user) {
            binding.setUser(user);
        }
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner occl) {
        this.onCardClickListner = occl;
    }

}