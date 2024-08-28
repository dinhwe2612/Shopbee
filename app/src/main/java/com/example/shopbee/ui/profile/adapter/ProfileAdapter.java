package com.example.shopbee.ui.profile.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProfileOptionsBinding;
import com.example.shopbee.ui.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{
    public interface Listener {
        void onItemClicked(int position);
    }
    Listener listener;
    public ProfileAdapter(Listener listener) {
        this.listener = listener;
    }
    private final List<ProfileItem> profileItems = new ArrayList<>();
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProfileOptionsBinding binding = ProfileOptionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProfileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.binding.optionName.setText(profileItems.get(position).option_name);
        holder.binding.optionContent.setText(profileItems.get(position).option_content);
        holder.binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return profileItems.size();
    }

    static class ProfileViewHolder extends RecyclerView.ViewHolder{
        ProfileOptionsBinding binding;
        public ProfileViewHolder(@NonNull ProfileOptionsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void setUpListProfileItem(){
        profileItems.add(new ProfileItem("My orders", "Already have 12..."));
        profileItems.add(new ProfileItem("Shipping Addresses", "3 addresses"));
        profileItems.add(new ProfileItem("Payment methods", "Visa **34"));
        profileItems.add(new ProfileItem("Promocodes", "You have special promocodes"));
        profileItems.add(new ProfileItem("My reviews", "Review for 4 items"));
        profileItems.add(new ProfileItem("Settings", "Notification, password"));
    }
    public static class ProfileItem {
        private String option_name;
        private String option_content;

        ProfileItem(String option_name, String option_content){
            this.option_name = option_name;
            this.option_content = option_content;
        }
        public void setOption_name(String option_name){
            this.option_name = option_name;
        }
        public void setOption_content(String option_content){
            this.option_content = option_content;
        }
        public String getOption_name(){
            return option_name;
        }
        public String getOption_content(){
            return option_content;
        }
    }
}
