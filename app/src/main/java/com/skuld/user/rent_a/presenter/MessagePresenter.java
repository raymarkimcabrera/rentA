package com.skuld.user.rent_a.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skuld.user.rent_a.model.conversation.Message;
import com.skuld.user.rent_a.model.conversation.MessageList;
import com.skuld.user.rent_a.model.transaction.Transaction;
import com.skuld.user.rent_a.views.MessageListView;

import java.util.ArrayList;
import java.util.List;

public class MessagePresenter extends BasePresenter {

    private Context mContext;
    private List<MessageList> messageList;
    private MessageListView messageListView;

    public MessagePresenter(Context mContext, MessageListView messageListView) {
        this.mContext = mContext;
        this.messageListView = messageListView;
    }

    public void getConversationList(String userID) {
        messageList = new ArrayList<>();
        showProgressDialog(mContext);

        initFirebase();

        Query getAllTransaction = mFirebaseFirestore.collection("transaction").whereEqualTo("userID", userID);

        getAllTransaction.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.getDocuments().size() > 0) {
                    for (final DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Transaction transaction = documentSnapshot.toObject(Transaction.class);

                        initFirebase();
//                        Log.i("MESSAGE_ID", transaction.getConversationID());
                        mFirebaseFirestore.collection("messages").document(transaction.getConversationID()).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        MessageList message = documentSnapshot.toObject(MessageList.class);
                                        Log.i("TASK_MESSAGE_LIST",message.getId());

                                        messageListView.onGetConversationSuccess(message);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        hideProgressDialog();
                                        Log.e("onFailure", "onFailure: " + e.getMessage() );
                                        messageListView.onGetConversationError();
                                    }
                                });
                        if (messageList.size() > 0){
//                            Log.i("Message_List", messageList.size() + "");
                        }
                    }

                    hideProgressDialog();
                    Log.i("Message_List_AFTER", messageList.size() + "");
//                    for (MessageList thread : messageList) {
//                        Log.i("Message_List", thread.getId());
//                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Log.e("onFailure", "onFailure: " + e.getMessage() );
                messageListView.onGetConversationError();
            }
        });
    }
}
