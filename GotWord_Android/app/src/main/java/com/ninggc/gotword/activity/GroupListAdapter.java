package com.ninggc.gotword.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninggc.gotword.R;
import com.ninggc.gotword.activity.word.WordListActivity;
import com.ninggc.gotword.entity.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    Context context;
    List<Group> groups;

    public GroupListAdapter(Context context, List<Group> groups) {
        this.context = context;
        if (groups == null) {
            this.groups = new ArrayList<>();
        } else {
            this.groups = groups;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Group group = groups.get(position);
        holder.tv_group_name.setText(group.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, WordListActivity.class).putExtra("id", groups.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void addItem(Group group) {
        groups.add(group);
        notifyItemChanged(groups.size());
    }

    public void addItem(List<Group> groups) {
        for (Group g : groups) {
            addItem(g);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView tv_group_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tv_group_name = (TextView) itemView.findViewById(R.id.item_tv_group_name);
        }
    }
}
