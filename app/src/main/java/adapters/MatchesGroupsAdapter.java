package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.TeamGroup;
import datamodels.MatchesGroup;
import views.ExpandableHeightListView;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class MatchesGroupsAdapter extends RecyclerView.Adapter<MatchesGroupsAdapter.ViewHolder> {
    private Context context;
    private List<MatchesGroup> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public MatchesGroupsAdapter(Context context, List<MatchesGroup> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // set data
        MatchesGroup group = data.get(position);
        holder.textDate.setText(group.getDate());
        MatchesAdapter matchesAdapter = new MatchesAdapter(context, R.layout.list_schedule_matches_item, group.getMatches());
        holder.listMatches.setAdapter(matchesAdapter);
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
        TextView textDate;
        ExpandableHeightListView listMatches;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            textDate = (TextView) v.findViewById(R.id.text_date);
            listMatches = (ExpandableHeightListView) v.findViewById(R.id.list_matches);

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