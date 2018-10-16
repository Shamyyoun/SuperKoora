package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.smartinnovationtechnology.superkoora.R;

import java.util.List;

import datamodels.NewsItem;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<NewsItem> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<NewsItem> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NewsItem item = data.get(position);

        // check id
        if (item.getId() != -1) {
            // not footer
            // show main layout
            holder.layoutMain.setVisibility(View.VISIBLE);
            holder.layoutFooter.setVisibility(View.GONE);
            // set title
            holder.textTitle.setText(item.getTitle());
            // load image
            if (!item.getImage().isEmpty())
                Picasso.with(context).load(item.getImage()).into(holder.imageImage);
            // show or hide favorite icon
            holder.imageFavorite.setVisibility(item.isFavorite() ? View.VISIBLE : View.GONE);

            // check position to add margin or not
            int margin = 0;
            if (position != 0) {
                margin = (int) context.getResources().getDimension(R.dimen.content_items_spacing);
            }
            // add margin
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.rootView.getLayoutParams();
            params.topMargin = margin;
        } else {
            // this is a footer
            // show footer
            holder.layoutFooter.setVisibility(View.VISIBLE);
            holder.layoutMain.setVisibility(View.GONE);
        }
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
        public View layoutMain;
        public View layoutFooter;
        public ImageView imageImage;
        public TextView textTitle;
        public ImageView imageFavorite;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            layoutMain = v.findViewById(R.id.layout_main);
            layoutFooter = v.findViewById(R.id.layout_footer);
            imageImage = (ImageView) v.findViewById(R.id.image_image);
            textTitle = (TextView) v.findViewById(R.id.text_title);
            imageFavorite = (ImageView) v.findViewById(R.id.image_favoriteIcon);

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