package com.skuld.user.rent_a.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.autocomplete.SuggestionsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AutoCompleteRecyclerViewAdapter extends  RecyclerView.Adapter<AutoCompleteRecyclerViewAdapter.AutoCompleteViewHolder>{

    public static final String TAG = AutoCompleteRecyclerViewAdapter.class.getSimpleName();
    public static final String PHL_COUNTRY_CODE = "PHL";
    private Context mContext;
    private List<SuggestionsItem> mSuggestionsItemList;
    private OnItemClickLister mOnItemClickLister;

    public interface OnItemClickLister{
        void onItemClick(String query);
    }
    public AutoCompleteRecyclerViewAdapter(Context mContext, List<SuggestionsItem> mSuggestionsItemList, OnItemClickLister onItemClickLister) {
        this.mContext = mContext;
        this.mSuggestionsItemList = mSuggestionsItemList;
        this.mOnItemClickLister = onItemClickLister;
    }

    @NonNull
    @Override
    public AutoCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_places_list_item, parent, false);
        AutoCompleteViewHolder cardsViewHolder = new AutoCompleteViewHolder(view);
        cardsViewHolder.setIsRecyclable(false);
        return cardsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AutoCompleteViewHolder holder, int position) {
        final SuggestionsItem suggestionsItem = mSuggestionsItemList.get(position);
        holder.setIsRecyclable(true);
        if (suggestionsItem.getCountryCode().equals(PHL_COUNTRY_CODE)){
            holder.mCompleteAddressTextView.setText(suggestionsItem.getAddress().getFullAddress());
            holder.mDisplayNameTextView.setText(suggestionsItem.getLabel());
            holder.margin.setVisibility(View.VISIBLE);
            holder.mSuggestionLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLister.onItemClick(suggestionsItem.getLocationId());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mSuggestionsItemList != null ? mSuggestionsItemList.size() : 0;
    }

    public class AutoCompleteViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.displayNameTextView)
        TextView mDisplayNameTextView;

        @BindView(R.id.completeAddressTextView)
        TextView mCompleteAddressTextView;

        @BindView(R.id.suggestionLinearLayout)
        LinearLayout mSuggestionLinearLayout;

        @BindView(R.id.margin)
        View margin;

        public AutoCompleteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
