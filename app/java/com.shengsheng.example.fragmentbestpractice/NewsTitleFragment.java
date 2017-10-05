package com.example.shengsheng.fragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by XQS on 2017/10/5 0005.
 */

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStae)
    {
        View view=inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView newsTitleRecycleView=(RecyclerView)view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        newsTitleRecycleView.setLayoutManager(layoutManager);
        NewsAdapter adapter=new NewsAdapter(getNews());
        newsTitleRecycleView.setAdapter(adapter);
        return view;
    }
    private List<News>getNews()
    {
        List<News>newsList=new ArrayList<>();
        for(int i=1;i<=50;i++)
        {
            News news=new News();
            news.setTitle("this is newstitle "+i);
            news.setContent(getRandomLengthContent("this is newstitle "+i+"."));
            newsList.add(news);
        }
        return newsList;
    }
    private String getRandomLengthContent(String content)
    {
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++)
        {
            builder.append(content);
        }
        return builder.toString();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout)!=null)
        {
            isTwoPane=true;
        }
        else
        {
            isTwoPane=false;
        }
    }
    class   NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>
    {
        private List<News> mNewsList;
        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView newsTitleText;
            public RecyclerView.ViewHolder(View view)
            {
                super(view);
                newsTitleText=(TextView)view.findViewById(R.id.news_title);
            }
        }
        public NewsAdapter(List<News>newsList)
        {
            mNewsList=newsList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
        {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            view.setOnClickListener(new View.onClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    News news=mNewsList.get(holder.getAdapterPosition());
                    if(isTwoPane)
                    {
                        NewsContentFragment newsContentFragment=(NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }
                    else
                    {
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder,int position)
        {
            News news=mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }
        @Override
        public int getItemCount()
        {
            return mNewsList.size();
        }
    }
}
