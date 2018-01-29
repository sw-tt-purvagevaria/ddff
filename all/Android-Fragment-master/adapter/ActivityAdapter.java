package com.css.bcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.css.bcg.R;
import com.css.bcg.model.ActivityModel;

import java.util.ArrayList;

/**
 * Created by PG  on 10/5/17.
 * Contains work out data
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.WorkOutHolder> {

    private String TAG = "ActivityAdapter";
    private Context context;
    private View.OnClickListener onClickListener;
    private ArrayList<ActivityModel> arrayListActivity;

    public ActivityAdapter(Context context, ArrayList<ActivityModel> arrayListActivity) {
        this.context = context;
        this.arrayListActivity = arrayListActivity;
        this.onClickListener = onClickListener;
    }

    @Override
    public WorkOutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(context).inflate(R.layout.list_activity, parent, false);

        // create ViewHolder
        WorkOutHolder viewHolder = new WorkOutHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(WorkOutHolder holder, int position) {

        ActivityModel activityModel = arrayListActivity.get(position);


        holder.txtPoints.setText(activityModel.getTotalPoints() + " Points Earned");
        if (activityModel.getWorkoutCompleted().equalsIgnoreCase("0")) {
            holder.txtWorkOutCompleted.setText("No workouts done");
        } else {
            holder.txtWorkOutCompleted.setText(activityModel.getTotalWorkOut() + " Of " + activityModel.getWorkoutCompleted() + " workouts completed");
        }


        if (activityModel.getSetStepsCounted().equalsIgnoreCase("0")) {
            holder.txtStepsCounted.setText("Steps are not tracked");
        } else {

            holder.txtStepsCounted.setText(activityModel.getSetStepsCounted() + " Steps Count - " + activityModel.getDistance() + " Km walk");
        }

        if (activityModel.getTotalPoints().equalsIgnoreCase("0")) {
            holder.txtPoints.setText("No Points Earned");
        } else {
            holder.txtPoints.setText(activityModel.getTotalPoints() + " Points Earned");
        }
        holder.txtDate.setText(activityModel.getActivityDate());
    }


    @Override
    public int getItemCount() {
        return arrayListActivity.size();
    }

    public class WorkOutHolder extends RecyclerView.ViewHolder {


        public TextView txtPoints, txtStepsCounted, txtWorkOutCompleted, txtDate;

        public WorkOutHolder(View itemView) {
            super(itemView);

            txtPoints = (TextView) itemView.findViewById(R.id.txtPoints);
            txtStepsCounted = (TextView) itemView.findViewById(R.id.txtStepsCounted);
            txtWorkOutCompleted = (TextView) itemView.findViewById(R.id.txtWorkOutCompleted);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);

        }
    }
}   //end of class WorkOutAdapter
