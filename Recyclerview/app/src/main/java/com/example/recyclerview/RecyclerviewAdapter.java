package com.example.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.databaseCalls.TrendingApi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>{
    private List<TrendingApi> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerviewAdapter(Context context, List<TrendingApi> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_trending_api, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TrendingApi trendingApi = mData.get(position);
        holder.isVisible = false;
        holder.author.setText(trendingApi.getmAuthor());
        holder.name.setText(trendingApi.getmName());
        holder.description.setText(trendingApi.getmDescription());
        holder.description.setVisibility(View.GONE);

        if (trendingApi.getmLanguage() != null && trendingApi.getmLanguage() != "") {
            holder.language.setText(trendingApi.getmLanguage());
        } else {
            holder.language.setVisibility(View.GONE);
        }
        if (trendingApi.getmLanguageColor() != null && trendingApi.getmLanguageColor() !="") {
            holder.languageColor.setBackground(drawCircle
                    (this, 8, 8, trendingApi.getmLanguageColor()));
        } else {
            holder.languageColor.setVisibility(View.GONE);
        }
        holder.stars.setText(trendingApi.getmStars());
        holder.forks.setText(trendingApi.getmForks());
        holder.viewExtraInfo.setVisibility(View.GONE);

        Picasso.get()
                .load(trendingApi.getmAvatar())
                .transform(new ImageTrans_CircleTransform())
                .into(holder.avatar);

        getListener(holder, trendingApi);
    }

    private void getListener(final ViewHolder holder, final TrendingApi trendingApi) {
        holder.apiInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.isVisible) {
                    if (trendingApi.getmDescription() != null && trendingApi.getmDescription() != "") {
                        holder.description.setVisibility(View.VISIBLE);
                    }
                    holder.viewExtraInfo.setVisibility(View.VISIBLE);
                    holder.isVisible = true;

                    holder.apiInfoContainer.setSelected(true);
                } else {
                    holder.description.setVisibility(View.GONE);
                    holder.viewExtraInfo.setVisibility(View.GONE);
                    holder.isVisible = false;
                    holder.apiInfoContainer.setSelected(false);
                }
            }
        });
    }

    public static ShapeDrawable drawCircle (RecyclerviewAdapter context, int width, int height, String color) {

        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setIntrinsicHeight (height);
        oval.setIntrinsicWidth (width);
        oval.getPaint ().setColor (Color.parseColor(color));
        return oval;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setClickListener() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView author, name, description, language, languageColor, stars, forks;
        private LinearLayout apiInfoContainer;
        private LinearLayout viewExtraInfo;
        private ImageView avatar;
        private boolean isVisible = false;
        ViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            avatar = itemView.findViewById(R.id.avatar);
            language =  itemView.findViewById(R.id.language);
            stars =  itemView.findViewById(R.id.stars);
            forks =  itemView.findViewById(R.id.forks);
            apiInfoContainer = itemView.findViewById(R.id.api_info_container);
            viewExtraInfo = itemView.findViewById(R.id.view_extra_info);
            languageColor = itemView.findViewById(R.id.language_color);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
