package com.BkAndroidDev.wallpaperplus;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private ArrayList<Photo> list = new ArrayList<>();
    private cardOnClickListener listener;
    private Context context;
    public Adapter(ArrayList<Photo> list,cardOnClickListener listener,Context context){
        this.list = list;
        this.listener = listener;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_paper_modal_layout,parent,false);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       int pos = position;
        Picasso.get().load(list.get(position).src.getMedium()).into(holder.image);
        holder.imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CardView imageCard;
        ImageView image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageCard = itemView.findViewById(R.id.imgCard);
            image = itemView.findViewById(R.id.WallImage);
        }
    }
}
