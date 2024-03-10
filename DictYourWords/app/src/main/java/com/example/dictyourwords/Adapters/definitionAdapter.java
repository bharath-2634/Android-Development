package com.example.dictyourwords.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dictyourwords.Modals.Definitions;
import com.example.dictyourwords.R;

public class definitionAdapter extends RecyclerView.Adapter<definitionAdapter.definitionViewHolder>{

    private ArrayList<Definitions> definitionList = new ArrayList<>();

    public definitionAdapter(ArrayList<Definitions> list){
        this.definitionList =list;
    }

    @NonNull
    @Override
    public definitionAdapter.definitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_layout,parent,false);
        definitionViewHolder viewHolder = new definitionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull definitionAdapter.definitionViewHolder holder, int position) {

        StringBuilder synonyms = new StringBuilder();
        synonyms.append(definitionList.get(position).getSynonyms());
        StringBuilder antonyms = new StringBuilder();
        antonyms.append(definitionList.get(position).getAntonyms());
        holder.Antonyms.setText(antonyms);
        holder.Synonyms.setText(synonyms);
        holder.definition.setText(definitionList.get(position).getDefinition());
        holder.definitionExample.setText(definitionList.get(position).getExample());


    }

    @Override
    public int getItemCount() {
        return definitionList.size();
    }

    public class definitionViewHolder extends RecyclerView.ViewHolder{

        TextView definition,definitionExample,Synonyms,Antonyms;

        public definitionViewHolder(@NonNull View itemView) {
            super(itemView);
            definition = itemView.findViewById(R.id.definitionTxt);
            definitionExample = itemView.findViewById(R.id.definitionExample);
            Synonyms = itemView.findViewById(R.id.Synonyms);
            Antonyms = itemView.findViewById(R.id.Antonyms);

        }
    }
}
