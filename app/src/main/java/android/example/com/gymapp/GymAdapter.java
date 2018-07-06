package android.example.com.gymapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class GymAdapter extends RecyclerView.Adapter<GymAdapter.GymViewHolder> {
    private Context mContext;
    private ArrayList<GymItem> mGymList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public GymAdapter(Context context, ArrayList<GymItem> gymList) {
        mContext = context;
        mGymList = gymList;
    }

    @Override
    public GymViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.gym_item, parent, false);
        return new GymViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GymViewHolder holder, int position) {
        GymItem currentItem = mGymList.get(position);

        String gymName = currentItem.getGymName();
        String mobile = currentItem.getMobile();
        int rating = currentItem.getRating();

        holder.mTextViewGymName.setText("Gym Name: "+gymName);
        holder.mTextViewRating.setText("Rating: " + rating);
        holder.mTextViewMobile.setText("Contact: "+mobile);
    }

    @Override
    public int getItemCount() {
        return mGymList.size();
    }

    public class GymViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewGymName;
        public TextView mTextViewMobile;
        public  TextView mTextViewRating;

        public GymViewHolder(View itemView) {
            super(itemView);
            mTextViewGymName = itemView.findViewById(R.id.text_view_gymName);
            mTextViewMobile = itemView.findViewById(R.id.text_view_mobile);
            mTextViewRating = itemView.findViewById(R.id.text_view_rating);

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
