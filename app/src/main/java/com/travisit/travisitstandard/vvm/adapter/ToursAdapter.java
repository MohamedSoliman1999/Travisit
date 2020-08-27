package com.travisit.travisitstandard.vvm.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.data.Const;
import com.travisit.travisitstandard.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class ToursAdapter extends RecyclerView.Adapter<ToursAdapter.TourViewHolder> implements Filterable {
    private final Context context;
    private List<Tour> items;
    private List<Tour> itemsToDisplay;
    private SelectionPropagator observer;

    public ToursAdapter(List<Tour> items, Context context, SelectionPropagator observer) {
        this.items = items;
        this.itemsToDisplay = new ArrayList<>();
        this.context = context;
        this.observer = observer;
    }

    @Override
    public TourViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_explore, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TourViewHolder holder, int position) {
        Tour item = itemsToDisplay.get(position);
        holder.set(item, context, observer);
    }

    class TourViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView tourImage;
        TextView tourName;
        TextView popularity;
        TextView rating;
        TextView date;
        ConstraintLayout container;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            tourImage = itemView.findViewById(R.id.card_explore_sdv_image);
            tourName = itemView.findViewById(R.id.card_explore_tv_name);
            popularity = itemView.findViewById(R.id.card_explore_tv_booked_by_number);
            date = itemView.findViewById(R.id.card_explore_tv_date);
            rating = itemView.findViewById(R.id.card_explore_tv_rating);
            container = itemView.findViewById(R.id.card_explore_container);
        }
        public void set(Tour item, Context context, SelectionPropagator observer) {
            //TODO ADD IMAGE
            /*String tourPhotoPath = Const.IMAGES_SERVER_ADDRESS + item.getFirstImage();
            tourImage.setImageURI(Uri.parse(tourPhotoPath));*/
            tourName.setText(item.getTitle());
            //TODO CHANGE RATING Add Price
            rating.setText("3.5");
            date.setText(context.getString(R.string.ends_on) + " " + item.getStartTime().substring(0, Math.min(item.getStartTime().length(), 10)));
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    observer.tourSelected(item);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if (itemsToDisplay == null) {
            return 0;
        }
        return itemsToDisplay.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String searchString = constraint.toString();
                if(searchString.isEmpty()){
                    itemsToDisplay = items;
                } else {
                    for(Tour tour : items){
                        if(tour.getTitle().toLowerCase().contains(searchString.toLowerCase())){
                            itemsToDisplay.add(tour);
                        }
                    }
                    if(itemsToDisplay.isEmpty()){
                        Toast.makeText(context, "Couldn't Find: "+searchString+" !", Toast.LENGTH_SHORT);
                    }
                }
                results.values = itemsToDisplay;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            }
        };
    }
    private void removeItem(Tour tour){
        items.remove(tour);
        notifyDataSetChanged();
    }
    public interface SelectionPropagator{
        void tourSelected(Tour tour);
    }
}