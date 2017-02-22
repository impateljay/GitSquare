package com.jay.gitsquare;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jay on 22-02-2017.
 */

public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.MyViewHolder> {

    private List<Contributor> contributorList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView loginName, contributions;
        public CircleImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            loginName = (TextView) view.findViewById(R.id.txt_login_name);
            contributions = (TextView) view.findViewById(R.id.txt_contributions);
            avatar = (CircleImageView) view.findViewById(R.id.avatar_image);
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
        holder.avatar.setImageResource(R.drawable.image);
    }

    @Override
    public int getItemCount() {
        return contributorList.size();
    }
}
