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
import android.widget.TextView;

import com.skuld.user.rent_a.R;
import com.skuld.user.rent_a.model.conversation.Message;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.driver.Driver;
import com.skuld.user.rent_a.model.user.User;
import com.skuld.user.rent_a.presenter.UsersPresenter;
import com.skuld.user.rent_a.utils.ImageUtil;
import com.skuld.user.rent_a.utils.Preferences;
import com.skuld.user.rent_a.views.UsersView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder> implements UsersView {

    private List<MessageList> mMessageLists;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private UsersPresenter mUserPresenter;
    private MessageViewHolder mMessageViewHolder;

    public MessageRecyclerViewAdapter(Context context, List<MessageList> messageLists, OnItemClickListener onItemClickListener) {
        this.mMessageLists = messageLists;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClicked(MessageList messageList, String title);
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_message, parent, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        messageViewHolder.setIsRecyclable(true);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        mMessageViewHolder = holder;
        final MessageList messageList = mMessageLists.get(position);

        mUserPresenter = new UsersPresenter(mContext, this);

        int latestMessageIndex = messageList.getThread().size() - 1;
        String driverName = "";
        String userID = "";

        for(Message message : messageList.getThread()){
            if (!message.getSenderID().equals(Preferences.getString(mContext, Preferences.USER_ID))){
                driverName = message.getSenderName();
                userID = message.getSenderID();
            }
        }

        mUserPresenter.getDriverProfile(userID);

        holder.mDriverTextView.setText(driverName);
        final String title = driverName;
        PrettyTime prettyTime = new PrettyTime();
        holder.mLastUpdateTextView.setText(prettyTime.format(messageList.getThread().get(latestMessageIndex).getCreatedAt()));
        holder.mMessageSummaryTextView.setText(messageList.getThread().get(latestMessageIndex).getContent());

        holder.mMessageLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(messageList, title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessageLists.size();
    }

    public void updateItems(List<MessageList> messageList){
        this.mMessageLists = messageList;
        notifyDataSetChanged();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.driverTextView)
        TextView mDriverTextView;

        @BindView(R.id.messageSummaryTextView)
        TextView mMessageSummaryTextView;

        @BindView(R.id.lastUpdateTextView)
        TextView mLastUpdateTextView;

        @BindView(R.id.messageLinearLayout)
        LinearLayout mMessageLinearLayout;

        @BindView(R.id.messageImageVIew)
        CircleImageView mMessageImageView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public void onGetUserSuccess(User user) {

    }

    @Override
    public void onGetUserError() {

    }

    @Override
    public void onUserUpdateSuccess() {

    }

    @Override
    public void onUserUpdateError() {

    }

    @Override
    public void onGetDriverProfileSuccess(Driver driver) {
        if (!driver.getImageUrl().isEmpty())
            ImageUtil.loadImageFromUrl(mContext, mMessageViewHolder.mMessageImageView, driver.getImageUrl());
    }

    @Override
    public void onGetDriverProfileError() {

    }

}
