package com.jay.gitsquare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jay on 22-02-2017.
 */

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.MyViewHolder> {

    private List<Contributor> contributorList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView loginName, contributions, contributedRepos;
        public CircleImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            loginName = (TextView) view.findViewById(R.id.txt_login_name);
            contributedRepos = (TextView) view.findViewById(R.id.txt_contributed_repos);
            contributions = (TextView) view.findViewById(R.id.txt_contributions);
            avatar = (CircleImageView) view.findViewById(R.id.avatar_image);
            contributedRepos.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == contributedRepos.getId()) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(contributorList.get(getAdapterPosition()).getReposUrl()));
                context.startActivity(i);
            }
        }
    }

    public ContributorsAdapter(List<Contributor> contributorList) {
        this.contributorList = contributorList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributor_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contributor contributor = contributorList.get(position);
        holder.loginName.setText(contributor.getLogin());
        holder.contributions.setText("Contributions : " + contributor.getContributions());
        Glide.with(context).load(contributor.getAvatarUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return contributorList.size();
    }
}
