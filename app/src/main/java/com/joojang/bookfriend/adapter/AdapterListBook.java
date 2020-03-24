package com.joojang.bookfriend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.joojang.bookfriend.R;
import com.joojang.bookfriend.model.Book;
import com.joojang.bookfriend.utils.Tools;


import java.util.List;

public class AdapterListBook extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> items;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Book obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBook(Context context, List<Book> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView img_readbook;

        public TextView tv_title;
        public TextView tv_author;
        public TextView tv_content;
        public TextView tv_publisher;


        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            img_readbook = (ImageView) v.findViewById(R.id.img_readbook);

            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_author = (TextView) v.findViewById(R.id.tv_author);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_publisher = (TextView) v.findViewById(R.id.tv_publisher);

            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_one_line_light, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Book p = items.get(position);

            view.tv_title.setText(p.getTitle());
            view.tv_author.setText(p.getAuthors());
            view.tv_publisher.setText(p.getPublisher());
            view.tv_content.setText(p.getContents());

            Tools.displayImageOriginal(ctx, view.image, p.getThumbnail());

            if ( p.read_check ){
                view.img_readbook.setVisibility(View.VISIBLE);
            }else{
                view.img_readbook.setVisibility(View.INVISIBLE);
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}