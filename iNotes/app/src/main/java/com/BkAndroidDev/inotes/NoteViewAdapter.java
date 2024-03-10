package com.BkAndroidDev.inotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.List;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.BkAndroidDev.inotes.DataBase.Notes_Table;
import android.content.Context;


public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NotesViewHolder>{

    private List<Notes_Table> notes_list = new ArrayList<>();
    private NotesOnClick onClickListener;
    private pinClickListener pinListener;
    private Context context;
    private DeleteOnClickListener deleteOnClickListener;


    public NoteViewAdapter(List<Notes_Table> table,NotesOnClick onClick,pinClickListener listener,Context context,DeleteOnClickListener deleteOnClickListener){
        this.notes_list = table;
        this.onClickListener = onClick;
        this.pinListener = listener;
        this.context = context;
        this.deleteOnClickListener = deleteOnClickListener;

    }

    @NonNull
    @Override
    public NoteViewAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_modal,parent,false);
        NotesViewHolder viewHolder = new NotesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewAdapter.NotesViewHolder holder, int position) {
        int pos = position;

        holder.title.setText(notes_list.get(position).getNotes_Title());
        holder.notes.setText(notes_list.get(position).getNotes_description());
        holder.day.setText(notes_list.get(position).getDay());
        holder.date.setText(notes_list.get(position).getDate());

        if(notes_list.get(position).isIs_pinned()){
            holder.pin_img.setImageResource(R.drawable.pin);
        }else{
            holder.pin_img.setImageResource(R.drawable.not_pin);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOnClickListener.onDelete(notes_list.get(pos));
            }
        });
        holder.pin_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notes_list.get(pos).isIs_pinned()){
                    holder.pin_img.setImageResource(R.drawable.not_pin);
                    pinListener.updatePin(false,notes_list.get(pos));
                    Toast.makeText(context,"Unpinned Your Notes ",Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,"Please Refresh Your Page",Toast.LENGTH_LONG).show();
                }
                else if(!notes_list.get(pos).isIs_pinned()){
                    holder.pin_img.setImageResource(R.drawable.pin);
                    pinListener.updatePin(true,notes_list.get(pos));
                    Toast.makeText(context,"Pinned Your Notes ",Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,"Please Refresh Your Page",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.notes_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(notes_list.get(pos));
            }
        });


    }

    @Override
    public int getItemCount() {
        return notes_list.size();
    }

    public void onSearchResult(List<Notes_Table> search_result){
        notes_list = search_result;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{

        private TextView date,day,title,notes;
        private CardView notes_card;
        private ImageView pin_img,delete;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_digitTxt);
            day = itemView.findViewById(R.id.date_Txt);
            title = itemView.findViewById(R.id.title_txt);
            notes = itemView.findViewById(R.id.description_txt);
            notes_card = itemView.findViewById(R.id.notes_card);
            pin_img = itemView.findViewById(R.id.pin_img);
            delete = itemView.findViewById(R.id.delete_img);

        }
    }
}
