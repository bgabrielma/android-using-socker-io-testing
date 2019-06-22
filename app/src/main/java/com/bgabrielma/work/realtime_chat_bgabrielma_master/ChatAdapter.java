package com.bgabrielma.work.realtime_chat_bgabrielma_master;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<String> chat_data = new ArrayList<>();
    ArrayList<String> remetente = new ArrayList<>();

    public ChatAdapter(ArrayList<String> chat_data, ArrayList<String> remetente) {
        this.chat_data = chat_data;
        this.remetente = remetente;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);

        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        boolean isRemetente = remetente.get(position) == "me";

        holder.getText_chat().setText(chat_data.get(position));
        holder.getUser().setText(isRemetente ? remetente.get(position) : "User Lain");
        holder.getLinearLayout().setGravity(isRemetente ? Gravity.RIGHT : Gravity.LEFT);
        holder.getText_chat().setBackground(isRemetente ?
                holder.itemView.getContext().getDrawable(R.drawable.bg_me)
                : holder.itemView.getContext().getDrawable(R.drawable.bg_you));
    }

    @Override
    public int getItemCount() {
        return chat_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_chat, user;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text_chat = itemView.findViewById(R.id.text_chat);
            this.user = itemView.findViewById(R.id.user);
            this.linearLayout = itemView.findViewById(R.id.layout);
        }

        public TextView getText_chat() {
            return text_chat;
        }

        public TextView getUser() {
            return this.user;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }
}
