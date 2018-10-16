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

import datamodels.Competition;
import datamodels.MenuItem;

public class CompetitionsAdapter extends ArrayAdapter<Competition> {
    private Context context;
    private int layoutResourceId;
    private List<Competition> data = null;

    public CompetitionsAdapter(Context context, int layoutResourceId, List<Competition> data) {
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
            holder.imageLogo = (ImageView) row.findViewById(R.id.image_logo);
            holder.textTitle = (TextView) row.findViewById(R.id.text_title);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        // set data
        final Competition competition = data.get(position);
        holder.textTitle.setText(competition.getTitle());
        holder.imageLogo.setImageResource(competition.getLogo());

        return row;
    }

    static class ViewHolder {
        ImageView imageLogo;
        TextView textTitle;
    }
}
