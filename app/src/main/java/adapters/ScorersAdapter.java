package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.Scorer;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class ScorersAdapter extends RecyclerView.Adapter<ScorersAdapter.ViewHolder> {
    private Context context;
    private List<Scorer> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public ScorersAdapter(Context context, List<Scorer> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // set data
        Scorer scorer = data.get(position);
        holder.textPlayerName.setText(scorer.getPosition() + "-  " + scorer.getTitle());
        holder.textTeam.setText(scorer.getTeam().getTitle());
        holder.textTeam.setTextColor(Color.parseColor(scorer.getTeam().getColor()));
        holder.textGoals.setText("" + scorer.getGoals());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View rootView;
        public TextView textPlayerName;
        public TextView textTeam;
        public TextView textGoals;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            textPlayerName = (TextView) v.findViewById(R.id.text_playerName);
            textTeam = (TextView) v.findViewById(R.id.text_team);
            textGoals = (TextView) v.findViewById(R.id.text_goals);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}