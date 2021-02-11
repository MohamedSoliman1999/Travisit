package com.travisit.travisitstandard.vvm.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travisit.travisitstandard.R;
import com.travisit.travisitstandard.data.BusinessGuid;

public class BusinessGuidAdapter extends RecyclerView.Adapter<BusinessGuidAdapter.BusinessGuidHolder> {
    public List<BusinessGuid> arrayList = new ArrayList<>();
    private Context mContext;

    public BusinessGuidAdapter(List<BusinessGuid> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    public void setList(List<BusinessGuid> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BusinessGuidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Display item cardView in parent layout in Recycle View
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.businesses_or_guides_item, parent, false);
        return new BusinessGuidHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final BusinessGuidHolder holder, final int position) {
        //holder.textView.setText(arrayList.get(position).textLine);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class BusinessGuidHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        public BusinessGuidHolder(@NonNull final View itemView) {
            super(itemView);
            /*textView=itemView.findViewById(R.id.textLine_txt);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),arrayList.get(getAdapterPosition()).textLine+"",Toast.LENGTH_LONG).show();
                }
            });*/
        }
    }

    public BusinessGuid getWordItemAt(int id) {
        return arrayList.get(id);
    }
}
