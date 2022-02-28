package com.example.c196.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.activities.DetailedAssessmentActivity;
import com.example.c196.entities.EntityAssessment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAssessment extends RecyclerView.Adapter<AdapterAssessment.ViewHolderAssessment> {
    private List<EntityAssessment> a;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterAssessment(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    class ViewHolderAssessment extends RecyclerView.ViewHolder{
        private TextView assessmentItemView;

        public ViewHolderAssessment(@NonNull View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.text_view_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityAssessment current = a.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentActivity.class);//move to detailed view
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("assessmentType", current.getAssessmentType().name());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public AdapterAssessment.ViewHolderAssessment onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderAssessment(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAssessment holder, int position){
        if (a!= null){
            EntityAssessment currentAssessment = a.get(position);
            holder.assessmentItemView.setText(currentAssessment.getAssessmentTitle());
        }else{
            holder.assessmentItemView.setText("No Assessment Title");
        }
    }

    public void setAssessment(List<EntityAssessment> a){
        this.a =a;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (a != null){
            return a.size();
        }else return 0;
    }


}
