package com.example.c196.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.activities.DetailedNoteActivity;
import com.example.c196.entities.EntityNote;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolderNote>{
    private List<EntityNote> n;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterNote(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class ViewHolderNote extends RecyclerView.ViewHolder{
        private TextView noteItemView;

        public ViewHolderNote(@NonNull View itemView){
            super(itemView);
            noteItemView = itemView.findViewById(R.id.text_view_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityNote current = n.get(position);
                    Intent intent = new Intent(context, DetailedNoteActivity.class);
                    intent.putExtra("noteID", current.getNoteID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("noteTitle", current.getNoteTitle());
                    intent.putExtra("noteText", current.getNoteText());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public AdapterNote.ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderNote(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int position){
        if (n != null) {
            EntityNote currentNote = n.get(position);
            holder.noteItemView.setText(currentNote.getNoteTitle());
        }else{
            holder.noteItemView.setText("No Note Title");
        }
    }

    public void setNotes(List<EntityNote> n){
        this.n = n;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(n!=null){
        return n.size();
    }
        else return 0;
    }
}
