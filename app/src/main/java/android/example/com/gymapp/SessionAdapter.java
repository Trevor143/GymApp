package android.example.com.gymapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private Context mContext;
    private ArrayList<SessionItem> mSessionList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SessionAdapter(Context context, ArrayList<SessionItem> SessionList) {
        mContext = context;
        mSessionList = SessionList;
    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.session_item, parent, false);
        return new SessionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        SessionItem currentItem = mSessionList.get(position);

        String exercise = currentItem.getExercise();
        int sets = currentItem.getSets();
        String instructor = currentItem.getInstructor();
        String gym = currentItem.getGym();

        holder.mTextViewExercise.setText("Session Name: "+exercise);
        holder.mTextViewSets.setText("Sets: " + sets);
        holder.mTextViewInstructor.setText("Instructor: "+instructor);
        holder.mTextViewGym.setText("Gym Name: "+gym);

    }

    @Override
    public int getItemCount() {
        return mSessionList.size();
    }

    public class SessionViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewExercise;
        public TextView mTextViewSets;
        public  TextView mTextViewGym;
        public  TextView mTextViewInstructor;

        public SessionViewHolder(View itemView) {
            super(itemView);
            mTextViewExercise = itemView.findViewById(R.id.text_view_exercise);
            mTextViewSets = itemView.findViewById(R.id.text_view_sets);
            mTextViewInstructor = itemView.findViewById(R.id.text_view_instructor);
            mTextViewGym = itemView.findViewById(R.id.text_view_gym);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

