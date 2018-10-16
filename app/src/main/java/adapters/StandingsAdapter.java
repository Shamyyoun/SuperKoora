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

import datamodels.Standing;

public class StandingsAdapter extends ArrayAdapter<Standing> {
    private Context context;
    private int layoutResourceId;
    private List<Standing> data = null;

    public StandingsAdapter(Context context, int layoutResourceId, List<Standing> data) {
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
            holder.imageTeamLogo = (ImageView) row.findViewById(R.id.image_teamLogo);
            holder.textTeamTitle = (TextView) row.findViewById(R.id.text_teamTitle);
            holder.textGP = (TextView) row.findViewById(R.id.text_gp);
            holder.textW = (TextView) row.findViewById(R.id.text_w);
            holder.textD = (TextView) row.findViewById(R.id.text_d);
            holder.textL = (TextView) row.findViewById(R.id.text_l);
            holder.textPTS = (TextView) row.findViewById(R.id.text_pts);
            holder.textGS = (TextView) row.findViewById(R.id.text_gs);
            holder.textGC = (TextView) row.findViewById(R.id.text_gc);
            holder.textGD = (TextView) row.findViewById(R.id.text_gd);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        // set data
        Standing standing = data.get(position);
        holder.imageTeamLogo.setImageResource(standing.getTeamLogo());
        holder.textTeamTitle.setText((position + 1) + "-  " + standing.getTeamTitle());
        holder.textGP.setText("" + standing.getGp());
        holder.textW.setText("" + standing.getW());
        holder.textD.setText("" + standing.getD());
        holder.textL.setText("" + standing.getL());
        holder.textPTS.setText("" + standing.getPts());
        holder.textGS.setText("" + standing.getGs());
        holder.textGC.setText("" + standing.getGc());
        holder.textGD.setText("" + standing.getGd());

        return row;
    }

    static class ViewHolder {
        ImageView imageTeamLogo;
        TextView textTeamTitle;
        TextView textGP;
        TextView textW;
        TextView textD;
        TextView textL;
        TextView textPTS;
        TextView textGS;
        TextView textGC;
        TextView textGD;
    }
}
