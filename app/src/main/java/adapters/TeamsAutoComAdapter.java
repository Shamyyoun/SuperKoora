package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.smartinnovationtechnology.superkoora.AppController;
import net.smartinnovationtechnology.superkoora.R;

import java.util.ArrayList;
import java.util.List;

import datamodels.Constants;
import datamodels.Team;
import utils.ViewUtil;

/**
 * Created by Shamyyoun on 11/5/2015.
 */
public class TeamsAutoComAdapter extends ArrayAdapter<Team> {
    private Context context;
    private List<Team> data;
    private int viewResourceId;
    private String language;

    public TeamsAutoComAdapter(Context context, int viewResourceId, List<Team> data) {
        super(context, viewResourceId, data);
        this.context = context;
        this.data = data;
        this.viewResourceId = viewResourceId;
        language = AppController.getInstance(context).getLanguage();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Team team = data.get(position);
        if (team != null) {
            // find views
            final ImageView imageDefault = (ImageView) v.findViewById(R.id.image_default);
            ImageView imageImage = (ImageView) v.findViewById(R.id.image_image);
            TextView textTitle = (TextView) v.findViewById(R.id.text_title);

            // set title
            textTitle.setText(language.equals(Constants.LANG_EN) ? team.getNameEn() : team.getNameAr());

            // load logo
            if (!team.getLogo().isEmpty()) {
                Picasso.with(context).load(team.getLogo()).into(imageImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        ViewUtil.fadeView(imageDefault, false);
                    }

                    @Override
                    public void onError() {
                        ViewUtil.fadeView(imageDefault, true);
                    }
                });
            }
        }

        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            Team team = (Team) resultValue;
            String str = language.equals(Constants.LANG_EN) ? team.getNameEn() : team.getNameAr();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count = data.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Team> filteredList = (ArrayList<Team>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Team shop : filteredList) {
                    add(shop);
                }
                notifyDataSetChanged();
            }
        }
    };
}