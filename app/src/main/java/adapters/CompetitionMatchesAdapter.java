package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.CompetitionMatches;
import views.ExpandableHeightListView;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class CompetitionMatchesAdapter extends RecyclerView.Adapter<CompetitionMatchesAdapter.ViewHolder> {
    private Context context;
    private List<CompetitionMatches> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public CompetitionMatchesAdapter(Context context, List<CompetitionMatches> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // set data
        final CompetitionMatches competitionMatchesItem = data.get(position);
        holder.imageLogo.setImageResource(competitionMatchesItem.getLogo());
        holder.textTitle.setText(competitionMatchesItem.getTitle());
        holder.textRound.setText(context.getString(R.string.round) + " " + competitionMatchesItem.getRound());
        MatchesAdapter matchesAdapter = new MatchesAdapter(context, R.layout.list_ls_matches_item, competitionMatchesItem.getMatches());
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
        ImageView imageLogo;
        TextView textTitle;
        TextView textRound;
        ExpandableHeightListView listMatches;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            imageLogo = (ImageView) v.findViewById(R.id.image_logo);
            textTitle = (TextView) v.findViewById(R.id.text_title);
            textRound = (TextView) v.findViewById(R.id.text_round);
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