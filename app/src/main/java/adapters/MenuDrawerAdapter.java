package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.MenuItem;

public class MenuDrawerAdapter extends ArrayAdapter<MenuItem> {
    private Context context;
    private int layoutResourceId;
    private List<MenuItem> data = null;

    public MenuDrawerAdapter(Context context, int layoutResourceId, List<MenuItem> data) {
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
            holder.textTitle = (TextView) row.findViewById(R.id.text_title);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        // set data
        final MenuItem item = data.get(position);
        holder.textTitle.setText(item.getTitle());

        return row;
    }

    static class ViewHolder {
        TextView textTitle;
    }
}
