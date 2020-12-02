package com.example.zhuangzu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhuangzu.R;
import com.example.zhuangzu.bean.Article;
import com.example.zhuangzu.databinding.ArticleItemBinding;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener{
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private List<Article> articles;
    public ArticleAdapter(List<Article> articles){
        this.articles = articles;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivTitle;
        TextView tvTitle;
        TextView tvContent;
        TextView tvAuthor;

        public ViewHolder(@NonNull ArticleItemBinding itemBinding){
            super(itemBinding.getRoot());
            ivTitle = itemBinding.imageTitleIv;
            tvTitle = itemBinding.titleTv;
            tvContent = itemBinding.contentTv;
            tvAuthor = itemBinding.authorTv;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleItemBinding itemBinding = ArticleItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        itemBinding.getRoot().setOnClickListener(this);
        ViewHolder holder = new ViewHolder(itemBinding);

        return holder;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Article article = articles.get(position);
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvContent.setText(article.getShortContent());
        holder.tvTitle.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
