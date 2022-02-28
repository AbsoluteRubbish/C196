package com.example.c196.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c196.R;
import com.example.c196.activities.DetailedCourseActivity;
import com.example.c196.entities.EntityCourses;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.ViewHolderCourse> {
    private List<EntityCourses> c;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterCourse(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    class ViewHolderCourse extends RecyclerView.ViewHolder{
       private final TextView courseItemView;

        public ViewHolderCourse(@NonNull View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.text_view_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityCourses current = c.get(position);
                    Intent intent = new Intent(context, DetailedCourseActivity.class);//move to detailed view
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("courseStatus", current.getCourseStatus().name());
                    intent.putExtra("courseInstructorName", current.getCourseInstructorName());
                    intent.putExtra("courseInstructorPhone", current.getCourseInstructorPhone());
                    intent.putExtra("courseInstructorEmail", current.getCourseInstructorEmail());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public AdapterCourse.ViewHolderCourse onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderCourse(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCourse holder, int position){
        if (c != null) {
            EntityCourses currentCourse = c.get(position);
            holder.courseItemView.setText(currentCourse.getCourseTitle());
        }else{
            holder.courseItemView.setText("No Course Title");
        }
    }

    public void setCourses(List<EntityCourses> c){
        this.c = c;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (c != null) {
            return c.size();
        } else return 0;
    }



}
