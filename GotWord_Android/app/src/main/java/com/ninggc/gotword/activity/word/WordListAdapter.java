package com.ninggc.gotword.activity.word;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninggc.gotword.R;
import com.ninggc.gotword.entity.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ning on 12/5/2017 0005.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    List<Word> words;

    public WordListAdapter(List<Word> words) {
        if (words == null) {
            this.words = new ArrayList<>();
        } else {
            this.words = words;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_word_name.setText(words.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


    public void addItem(Word word) {
        words.add(word);
        notifyItemChanged(words.size());
    }

    public void addItem(List<Word> words) {
        for (Word word : words) {
            addItem(word);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_word_name;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_word_name = (TextView) itemView.findViewById(R.id.item_tv_word_name);
        }
    }
}
