package com.example.pdfreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.io.File;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.PdfViewHolder> {

    private ArrayList<File> PdfFiles = new ArrayList<>();
    onPdfFileSelected listener;
    public Adapter(ArrayList<File> pdf,onPdfFileSelected listener){
        this.PdfFiles = pdf;
        this.listener = listener;
    }
    @Override
    public PdfViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_custom_layout,parent,false);
        PdfViewHolder viewHolder = new PdfViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( PdfViewHolder holder, int position) {

       holder.pdfName.setText(PdfFiles.get(position).getName());
        int pos = position;
       holder.pdfCard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.PdfFileSelected(PdfFiles.get(pos));
           }
       });
    }

    @Override
    public int getItemCount() {
        return PdfFiles.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder{
       TextView pdfName;
       CardView pdfCard;
        public PdfViewHolder( View itemView) {
            super(itemView);
            pdfName = itemView.findViewById(R.id.PdfName);
            pdfCard = itemView.findViewById(R.id.pdfCard);
        }
    }
}
