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

import datamodels.Transfer;

/**
 * Created by Shamyyoun on 2/8/2015.
 */
public class TransfersAdapter extends RecyclerView.Adapter<TransfersAdapter.ViewHolder> {
    private Context context;
    private List<Transfer> data;
    private int layoutResourceId;
    private OnItemClickListener onItemClickListener;

    public TransfersAdapter(Context context, List<Transfer> data, int layoutResourceId) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Transfer transfer = data.get(position);

        // set data
        holder.textPlayerName.setText(transfer.getPlayerName());
        holder.imageNationality.setImageResource(transfer.getNationalityImage());
        holder.imageFromClub.setImageResource(transfer.getFromClubImage());
        holder.textFromClub.setText(transfer.getFromClubTitle());
        holder.imageToClub.setImageResource(transfer.getToClubImage());
        holder.textToClub.setText(transfer.getToClubTitle());
        holder.textFees.setText(transfer.getFormattedFees());

        // set bg color according to position
        int bgColor = context.getResources().getColor((position % 2 == 0) ? R.color.transfers_cell_bg2 : R.color.transfers_cell_bg1);
        holder.rootView.setBackgroundColor(bgColor);
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
        public TextView textPlayerName;
        public ImageView imageNationality;
        public ImageView imageFromClub;
        public TextView textFromClub;
        public ImageView imageToClub;
        public TextView textToClub;
        public TextView textFees;

        public ViewHolder(View v) {
            super(v);
            rootView = v;
            textPlayerName = (TextView) v.findViewById(R.id.text_playerName);
            imageNationality = (ImageView) v.findViewById(R.id.image_nationality);
            imageFromClub = (ImageView) v.findViewById(R.id.image_fromClub);
            textFromClub = (TextView) v.findViewById(R.id.text_fromClub);
            imageToClub = (ImageView) v.findViewById(R.id.image_toClub);
            textToClub = (TextView) v.findViewById(R.id.text_toClub);
            textFees = (TextView) v.findViewById(R.id.text_fees);

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