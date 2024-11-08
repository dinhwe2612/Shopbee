package com.example.shopbee.ui.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.databinding.ChangeCountryBinding;
import com.example.shopbee.databinding.CountryItemBinding;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    List<CountryRespone> countries;
    private int currentPosition;
    public interface Listener{
        void onClickItems(int position);
    }
    Listener listener;
    public CountryAdapter(Listener listener, List<CountryRespone> countries, int currentPosition) {
        this.listener = listener;
        this.countries = countries;
        this.currentPosition = currentPosition;
    }

    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryItemBinding binding = CountryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        CountryRespone country = countries.get(position);
        holder.binding.countryName.setText(country.getName());
        holder.binding.countryCode.setText("(" + country.getCode() + ")");
        if (currentPosition == position){
            holder.binding.countryItemLayout.setBackgroundResource(R.drawable.red_rectangle_gray_stroke);
        } else {
            holder.binding.countryItemLayout.setBackgroundResource(R.drawable.white_rectangle_gray_stroke);
        }
        holder.binding.countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
                listener.onClickItems(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return countries.size();
    }
    public static class CountryViewHolder extends RecyclerView.ViewHolder{
        CountryItemBinding binding;
        public CountryViewHolder(@NonNull CountryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
