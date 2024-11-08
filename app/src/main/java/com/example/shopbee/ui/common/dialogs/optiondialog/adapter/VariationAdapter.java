package com.example.shopbee.ui.common.dialogs.optiondialog.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.VariationItemBinding;
import com.xiaofeng.flowlayoutmanager.Alignment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VariationAdapter extends RecyclerView.Adapter<VariationAdapter.VariationViewHolder> implements VariationDetailsAdapter.Listener{
    public interface Listener{
        void setValid(boolean valid);
    }
    Listener listener;
    List<Pair<String, List<AmazonProductDetailsResponse.Data.VariationDetail>>> variations = new ArrayList<>();
    List<VariationDetailsAdapter> adapters = new ArrayList<>();
    List<Pair<String, String>> decisions = new ArrayList<>();
    int cnt = 0;
    public VariationAdapter(Listener listener){
        this.listener = listener;
    }
    public void setVariations(HashMap<String, List<AmazonProductDetailsResponse.Data.VariationDetail>> variations){
        this.variations.clear();
        for(String key : variations.keySet()){
            this.variations.add(new Pair<>(key, variations.get(key)));
            adapters.add(new VariationDetailsAdapter(this, this.variations.size() - 1, variations.get(key)));
            decisions.add(new Pair<>(key, ""));
        }
        notifyDataSetChanged();
        updateValid();
    }
    void updateValid() {
        int sz = getItemCount();
        if (cnt == sz) {
            listener.setValid(true);
        } else {
            listener.setValid(false);
        }
    }
    @NonNull
    @Override
    public VariationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VariationViewHolder(VariationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VariationViewHolder holder, int position) {
        holder.binding.title.setText(variations.get(position).first);
        holder.binding.detailsRCV.setAdapter(adapters.get(position));
        if (holder.binding.detailsRCV.getLayoutManager() == null) {
            FlowLayoutManager layoutManager = new FlowLayoutManager();
            layoutManager.setAlignment(Alignment.LEFT);
            layoutManager.setAutoMeasureEnabled(true);
            layoutManager.removeItemPerLineLimit();
            holder.binding.detailsRCV.setLayoutManager(layoutManager);
        }
    }

    @Override
    public int getItemCount() {
        return variations.size();
    }

    @Override
    public void onClick(int posPar, int posChild, boolean isUnselected) {
        if (isUnselected) {
            --cnt;
            decisions.set(posPar, new Pair<>(decisions.get(posPar).first, ""));
        } else {
            if (decisions.get(posPar).second == "") ++cnt;
            decisions.set(posPar, new Pair<>(decisions.get(posPar).first, variations.get(posPar).second.get(posChild).getValue()));
        }
        updateValid();
    }

    public List<Pair<String, String>> getDecisions(){
        return decisions;
    }

    static public class VariationViewHolder extends RecyclerView.ViewHolder{
        VariationItemBinding binding;
        public VariationViewHolder(@NonNull VariationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
