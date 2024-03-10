package com.example.dictyourwords.Adapters;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictyourwords.Modals.Phonetics;
import com.example.dictyourwords.R;

public class PhoneticsAdapter extends RecyclerView.Adapter<PhoneticsAdapter.PhoneticsViewHolder>{

    private ArrayList<Phonetics> phoneticsList = new ArrayList<>();

    public PhoneticsAdapter(ArrayList<Phonetics> phonetics){
        this.phoneticsList = phonetics;
    }

    @NonNull
    @Override
    public PhoneticsAdapter.PhoneticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonetics_layout,parent,false);
        PhoneticsViewHolder viewHolder = new PhoneticsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticsAdapter.PhoneticsViewHolder holder, int position) {

        int pos =position;
        holder.PhoneticsWord.setText(phoneticsList.get(position).getText());
        holder.sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource("https:"+phoneticsList.get(pos).getAudio());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                }catch(Exception msg){

                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return phoneticsList.size();
    }

    public class PhoneticsViewHolder extends RecyclerView.ViewHolder{
        TextView PhoneticsWord;
        ImageView sound;

        public PhoneticsViewHolder(@NonNull View itemView) {
            super(itemView);
            PhoneticsWord = itemView.findViewById(R.id.phonetic_Word);
            sound = itemView.findViewById(R.id.speakerBtn);

        }
    }
}
