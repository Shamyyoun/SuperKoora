package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.smartinnovationtechnology.superkoora.AppController;
import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.Constants;
import datamodels.Team;
import datamodels.Team1;
import utils.ViewUtil;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class FavoriteTeamsRecyclerAdapter extends RecyclerView.Adapter<FavoriteTeamsRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Team> data;
    private int layoutResourceId;
    private String language;
    private OnItemClickListener onItemClickListener;

    public FavoriteTeamsRecyclerAdapter(Context context, List<Team> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
        language = AppController.getInstance(context).getLanguage();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Team team = data.get(position);

        // set title
        holder.textTitle.setText(language.equals(Constants.LANG_EN) ? team.getNameEn() : team.getNameAr());
        // load logo
        if (!team.getLogo().isEmpty()) {
            Picasso.with(context).load(team.getLogo()).into(holder.imageImage, new Callback() {
                @Override
                public void onSuccess() {
                    ViewUtil.fadeView(holder.imageDefault, false);
                }

                @Override
                public void onError() {
                    ViewUtil.fadeView(holder.imageDefault, true);
                }
            });
        }
        // add click listener to remove button
        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get position
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // remove this item from favorites
                    data.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });
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
        public ImageView imageDefault;
        public ImageView imageImage;
        public TextView textTitle;
        private ImageButton buttonRemove;

        public ViewHolder(View v) {
            super(v);
            imageDefault = (ImageView) v.findViewById(R.id.image_default);
            imageImage = (ImageView) v.findViewById(R.id.image_image);
            textTitle = (TextView) v.findViewById(R.id.text_title);
            buttonRemove = (ImageButton) v.findViewById(R.id.image_remove);

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