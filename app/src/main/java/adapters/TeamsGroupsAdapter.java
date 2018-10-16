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
import views.ExpandableHeightListView;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class TeamsGroupsAdapter extends RecyclerView.Adapter<TeamsGroupsAdapter.ViewHolder> {
    private Context context;
    private List<TeamGroup> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public TeamsGroupsAdapter(Context context, List<TeamGroup> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // set data
        final TeamGroup group = data.get(position);
        holder.textTitle.setText(context.getString(R.string.group) + " " + group.getTitle().toUpperCase());
        StandingsAdapter matchesAdapter = new StandingsAdapter(context, R.layout.list_standings_item, group.getStandings());
        holder.listStandings.setAdapter(matchesAdapter);
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
        TextView textTitle;
        ExpandableHeightListView listStandings;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            textTitle = (TextView) v.findViewById(R.id.text_title);
            listStandings = (ExpandableHeightListView) v.findViewById(R.id.list_standings);

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