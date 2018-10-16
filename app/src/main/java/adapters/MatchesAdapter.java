package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.Match;

public class MatchesAdapter extends ArrayAdapter<Match> {
    private Context context;
    private int layoutResourceId;
    private List<Match> data = null;

    public MatchesAdapter(Context context, int layoutResourceId, List<Match> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textTeamTitle1 = (TextView) row.findViewById(R.id.text_teamTitle1);
            holder.textTeamTitle2 = (TextView) row.findViewById(R.id.text_teamTitle2);
            holder.imageTeamLogo1 = (ImageView) row.findViewById(R.id.image_teamLogo1);
            holder.imageTeamLogo2 = (ImageView) row.findViewById(R.id.image_teamLogo2);
            holder.textTime = (TextView) row.findViewById(R.id.text_time);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        // set data
        final Match match = data.get(position);
        holder.textTeamTitle1.setText(match.getTeam1().getTitle());
        holder.textTeamTitle2.setText(match.getTeam2().getTitle());
        holder.imageTeamLogo1.setImageResource(match.getTeam1().getLogo());
        holder.imageTeamLogo2.setImageResource(match.getTeam2().getLogo());
        holder.textTime.setText(match.getDate());

        // check layout res id
        if (layoutResourceId == R.layout.list_schedule_matches_item) {
            // set item bg color
            int color = context.getResources().getColor((position % 2 == 0) ? R.color.schedule_red_item : R.color.schedule_yellow_item);
            row.setBackgroundColor(color);
        }

        return row;
    }

    static class ViewHolder {
        TextView textTeamTitle1;
        TextView textTeamTitle2;
        ImageView imageTeamLogo1;
        ImageView imageTeamLogo2;
        TextView textTime;
    }
}
