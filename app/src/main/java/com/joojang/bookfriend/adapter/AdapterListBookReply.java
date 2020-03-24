package com.joojang.bookfriend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.model.BookReply;
import com.joojang.bookfriend.model.Image;
import com.joojang.bookfriend.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListBookReply extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BookReply> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, BookReply obj, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, BookReply obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mOnItemLongClickListener = mItemLongClickListener;
    }

    public AdapterListBookReply(Context context, List<BookReply> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_re_username;
        public TextView tv_re_registdate;
        public TextView tv_re_replycategory;
        public TextView tv_re_replycontent;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);

            tv_re_username = (TextView) v.findViewById(R.id.tv_re_username);
            tv_re_registdate = (TextView) v.findViewById(R.id.tv_re_registdate);
            tv_re_replycategory = (TextView) v.findViewById(R.id.tv_re_replycategory);
            tv_re_replycontent = (TextView) v.findViewById(R.id.tv_re_replycontent);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_book_reply, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            BookReply p = items.get(position);

            view.tv_re_username.setText(p.getUser_name());
            view.tv_re_registdate.setText(p.getCreate_at());
            view.tv_re_replycategory.setText("["+p.getGubun_name()+"]");
            view.tv_re_replycontent.setText(p.getContents());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            view.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(view, items.get(position), position);
                    }
                    return false;
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}