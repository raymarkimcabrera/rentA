package com.skuld.user.rent_a.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.presenter.UsersPresenter;
import com.skuld.user.rent_a.views.UsersView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewsViewHolder> implements UsersView {

    private Context mContext;
    private List<Transaction> mTransactionList;
    private UsersPresenter mUsersPresenter;
    private ReviewsViewHolder mReviewViewHolder;

    public ReviewsRecyclerViewAdapter(Context context, List<Transaction> transactionList) {
        this.mContext = context;
        this.mTransactionList = transactionList;
    }

    @NonNull
    @Override
    public ReviewsRecyclerViewAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        ReviewsViewHolder reviewsViewHolder = new ReviewsViewHolder(view);
        reviewsViewHolder.setIsRecyclable(true);
        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerViewAdapter.ReviewsViewHolder holder, int position) {
        final Transaction transaction = mTransactionList.get(position);
        mReviewViewHolder = holder;
        mUsersPresenter = new UsersPresenter(mContext, this);
        mReviewViewHolder.mUserReviewTextView.setText("Good driving");
        Log.e("TRANSACTION_DETAILS", "onBindViewHolder: " + transaction.getId() + " / " + transaction.getUserID() );
        mReviewViewHolder.mUserNameTextView.setText("Marcelino Bardelas");
        mReviewViewHolder.mUserImageView.setImageResource(R.drawable.sample_user);

    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    @Override
    public void onGetUserSuccess(User user) {
        mReviewViewHolder.mUserNameTextView.setText(user.getFirstName() +" "+ user.getLastName());
        Log.e("onBindViewHolder", "onBindViewHolder: " + user.getFirstName() +" "+ user.getLastName() );
    }

    @Override
    public void onGetUserError() {
        Log.e("onGetUserError", "onGetUserError: " );
    }

    @Override
    public void onUserUpdateSuccess() {

    }

    @Override
    public void onUserUpdateError() {

    }

    @Override
    public void onGetDriverProfileSuccess(Driver driver) {

    }

    @Override
    public void onGetDriverProfileError() {

    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.userImageView)
        ImageView mUserImageView;

        @BindView(R.id.userNameTextView)
        TextView mUserNameTextView;

        @BindView(R.id.userReviewTextView)
        TextView mUserReviewTextView;

        @BindView(R.id.reviewDateTextView)
        TextView mReviewDateTextView;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
