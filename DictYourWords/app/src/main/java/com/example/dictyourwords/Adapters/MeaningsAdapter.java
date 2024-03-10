package com.example.dictyourwords.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dictyourwords.Modals.Meanings;
import com.example.dictyourwords.R;

public class MeaningsAdapter extends RecyclerView.Adapter<MeaningsAdapter.MeaningsViewHolder>{

    private ArrayList<Meanings> meaningsList = new ArrayList<>();
    private Context context;

    public MeaningsAdapter(ArrayList<Meanings> meanings,Context context){
        this.meaningsList = meanings;
        this.context = context;
    }

    @NonNull
    @Override
    public MeaningsAdapter.MeaningsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meanings_layout,parent,false);
        MeaningsViewHolder viewHolder = new MeaningsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningsAdapter.MeaningsViewHolder holder, int position) {
       holder.MeaningWord.setText(meaningsList.get(position).getPartOfSpeech());
       definitionAdapter adapter = new definitionAdapter(meaningsList.get(position).getDefinitions());
       LinearLayoutManager layoutManager = new LinearLayoutManager(context);
       layoutManager.setOrientation(holder.definition.VERTICAL);
       holder.definition.setLayoutManager(layoutManager);
       holder.definition.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }

    public class MeaningsViewHolder extends RecyclerView.ViewHolder{
        TextView MeaningWord;
        RecyclerView definition;

        public MeaningsViewHolder(@NonNull View itemView) {
            super(itemView);
            MeaningWord = itemView.findViewById(R.id.meaning_Word);
            definition = itemView.findViewById(R.id.meaningsWordRecycler);

        }
    }
}
