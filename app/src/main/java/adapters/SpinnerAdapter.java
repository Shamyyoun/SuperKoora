package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shamyyoun on 4/30/2015.
 */
public class SpinnerAdapter<T> extends ArrayAdapter<T> {
    private int layoutRes;
    private LayoutInflater mInflater;
    private List<T> items;

    public SpinnerAdapter(Context context, int layoutRes, List<T> items) {
        super(context, layoutRes, items);
        mInflater = LayoutInflater.from(context);
        this.layoutRes = layoutRes;
        this.items = items;
    }

    public SpinnerAdapter(Context context, int layoutRes, T[] items) {
        this(context, layoutRes, Arrays.asList(items));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(layoutRes, null);
            holder = new ViewHolder();

            holder.textView = (TextView) view;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(items.get(position).toString());

        return view;
    }

    static class ViewHolder {
        TextView textView;
    }
}
