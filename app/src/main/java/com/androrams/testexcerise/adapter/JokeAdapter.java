package com.androrams.testexcerise.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androrams.testexcerise.R;
import com.androrams.testexcerise.network.model.Joke;
import com.androrams.testexcerise.viewmodel.JokeViewModel;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Joke> jokeList;
    JokeViewModel jokeViewModel;

    public JokeAdapter(JokeViewModel jokeViewModel) {
        this.jokeViewModel = jokeViewModel;
        this.jokeList = jokeViewModel.getJokeList();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return jokeList == null ? 0 : jokeList.size();
    }

    public void setJokeAfterLoading(Joke joke) {

        int lastElementIndex = jokeList.size() - 1;
        Joke lastJoke = jokeList.get(lastElementIndex);
        lastJoke.setJoke(joke.getJoke());
        lastJoke.setId(joke.getId());
        notifyDataSetChanged();
    }

    public void loadEmptyJoke() {
        jokeList.add(new Joke());
        notifyItemInserted(jokeList.size() - 1);
    }

    public class ViewHolder extends BaseViewHolder {
        TextView jokeTextView;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            jokeTextView = itemView.findViewById(R.id.joke_text_view);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            //  super.onBind(position);
            Joke joke = jokeList.get(position);
            jokeTextView.setText(joke.getJoke());
            if (joke.getJoke() != null) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }
}