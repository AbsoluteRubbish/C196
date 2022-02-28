package com.example.c196.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.activities.DetailedTermActivity;
import com.example.c196.entities.EntityTerm;

import java.util.List;



public class AdapterTerm extends RecyclerView.Adapter<AdapterTerm.ViewHolderTerm> {

    private List<EntityTerm> t;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterTerm(Context context){
        inflater=LayoutInflater.from(context);
        this.context = context;
    }

    class ViewHolderTerm extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private ViewHolderTerm(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.text_view_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityTerm current = t.get(position);
                    Intent intent = new Intent(context, DetailedTermActivity.class);//move to detailed view
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("termStartDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterTerm.ViewHolderTerm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderTerm(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTerm holder, int position) {
        if (t != null) {
            EntityTerm currentTerm = t.get(position);
            holder.termItemView.setText(currentTerm.getTermTitle());
         }else{
            holder.termItemView.setText("No Term Title");
        }
    }

    public void setTerms(List<EntityTerm> t) {
        this.t = t;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount(){
        if (t!=null){
            return t.size();
        }
        else return 0;
    }



}
