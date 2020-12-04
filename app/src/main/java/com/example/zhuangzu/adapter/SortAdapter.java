package com.example.zhuangzu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.databinding.ArticleItemBinding;
import com.example.zhuangzu.databinding.SortItemBinding;

import java.util.ArrayList;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<Article> articles;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public SortAdapter(Context context,ArrayList<Article> articles){
        this.articles = articles;
        this.context = context;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SortItemBinding itemBinding = SortItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        itemBinding.getRoot().setOnClickListener(this);
        ViewHolder holder = new ViewHolder(itemBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Article article = articles.get(position);

        holder.tvTitle.setText(article.getTitle());
        holder.tvAuthor.setText(article.getAuthor());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/PMingLiU.ttf");
        holder.tvAuthor.setTypeface(typeface);
        holder.tvTitle.setTypeface(typeface);
        if(article.getPicture()!=null){
            Glide.with(context).load(article.getPicture().getFileUrl()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.ivTitlePic.setImageDrawable(resource);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivTitlePic;
        TextView tvTitle;
        TextView tvAuthor;

        public ViewHolder(@NonNull SortItemBinding binding) {
            super(binding.getRoot());
            ivTitlePic = binding.imagePicIv;
            tvTitle = binding.titleTv;
            tvAuthor = binding.authorTv;
        }
    }
}
