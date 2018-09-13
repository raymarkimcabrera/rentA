package com.skuld.user.rent_a.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.car.Car;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.utils.ImageUtil;
import com.skuld.user.rent_a.utils.ModelUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersRecyclerViewAdapter extends  RecyclerView.Adapter<OffersRecyclerViewAdapter.OffersRecyclerViewHolder>{
    public static final String TAG = OffersRecyclerViewAdapter.class.getSimpleName();


    private Context mContext;
    private List<Car> mCarList;
    private OnClickListener mOnClickListener;
    private List<Transaction> mTransactionList;

    public interface OnClickListener{

        void onCarSelected(Car car);
    }

    public OffersRecyclerViewAdapter(Context context, List<Car> carList, OnClickListener onClickListener, List<Transaction> transactionList) {
        this.mContext = context;
        this.mCarList = carList;
        this.mOnClickListener = onClickListener;
        this.mTransactionList = transactionList;
    }

    @NonNull
    @Override
    public OffersRecyclerViewAdapter.OffersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_offer_list_item, parent, false);
        OffersRecyclerViewHolder offersViewHolder = new OffersRecyclerViewHolder(view);
        offersViewHolder.setIsRecyclable(false);
        return offersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersRecyclerViewAdapter.OffersRecyclerViewHolder holder, int position) {
        final Car car = mCarList.get(position);

        ImageUtil.loadImageFromUrl(mContext, holder.mCarImageView, car.getImageUrl());

        holder.mPriceTextView.setText(car.getLowPrice() + " - " + car.getHighPrice());

        holder.mOfferLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onCarSelected(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public class OffersRecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.carImageView)
        ImageView mCarImageView;

        @BindView(R.id.priceTextView)
        TextView mPriceTextView;

        @BindView(R.id.ratingBar)
        RatingBar mRatingBar;

        @BindView(R.id.offerLinearLayout)
        LinearLayout mOfferLinearLayout;

        public OffersRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
