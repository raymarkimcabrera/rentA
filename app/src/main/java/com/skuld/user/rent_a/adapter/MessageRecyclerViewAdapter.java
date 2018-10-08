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
import com.skuld.user.rent_a.model.conversation.MessageList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder>{

    private List<MessageList> messageLists;
    private Context mContext;

    public MessageRecyclerViewAdapter(Context context, List<MessageList> messageLists) {
        this.messageLists = messageLists;
        this.mContext = context;
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

    }

    @Override
    public int getItemCount() {
        return messageLists.size();
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

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
