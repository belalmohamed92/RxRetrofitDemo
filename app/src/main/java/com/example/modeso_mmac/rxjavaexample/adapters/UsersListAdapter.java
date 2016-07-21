package com.example.modeso_mmac.rxjavaexample.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modeso_mmac.rxjavaexample.BR;
import com.example.modeso_mmac.rxjavaexample.R;
import com.example.modeso_mmac.rxjavaexample.databinding.RecyclerItemBinding;
import com.example.modeso_mmac.rxjavaexample.datamodel.User;

import java.util.List;


/**
 * Created by Belal Mohamed on 7/21/16.
 * www.modeso.ch
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private List<User> mUsers;

    public UsersListAdapter(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setVariable(BR.user, mUsers.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
