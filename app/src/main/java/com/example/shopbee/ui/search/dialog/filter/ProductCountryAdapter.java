package com.example.shopbee.ui.search.dialog.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.ProductCountry;
import com.example.shopbee.databinding.SelectSizeItemBinding;

import java.util.ArrayList;

public class ProductCountryAdapter extends RecyclerView.Adapter<ProductCountryAdapter.ViewHolder> {
    ArrayList<ProductCountry> productCountries;
    public ProductCountry getProductCondition() {
        return productCountries.get(currentPosition);
    }
    int currentPosition;
    ProductCountryAdapter(ProductCountry productCountry) {
        productCountries = new ArrayList<>();
//        ArrayList<ProductCountry> productCountries = new ArrayList<>(EnumSet.allOf(ProductCountry.class));
        productCountries.add(ProductCountry.US);
        productCountries.add(ProductCountry.AU);
        productCountries.add(ProductCountry.BR);
        productCountries.add(ProductCountry.CA);
        productCountries.add(ProductCountry.CN);
        productCountries.add(ProductCountry.FR);
        productCountries.add(ProductCountry.DE);
        productCountries.add(ProductCountry.IN);
        productCountries.add(ProductCountry.IT);
        productCountries.add(ProductCountry.MX);
        productCountries.add(ProductCountry.NL);
        productCountries.add(ProductCountry.SG);
        productCountries.add(ProductCountry.ES);
        productCountries.add(ProductCountry.TR);
        productCountries.add(ProductCountry.AE);
        productCountries.add(ProductCountry.GB);
        productCountries.add(ProductCountry.JP);
        productCountries.add(ProductCountry.SA);
        productCountries.add(ProductCountry.PL);
        productCountries.add(ProductCountry.SE);
        productCountries.add(ProductCountry.BE);
        productCountries.add(ProductCountry.EG);
        for (int i = 0; i < productCountries.size(); i++) {
            if (productCountries.get(i).equals(productCountry)) {
                currentPosition = i;
                break;
            }
        }
    }
    @NonNull
    @Override
    public ProductCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectSizeItemBinding binding = SelectSizeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductCountryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCountryAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(currentPosition);
                currentPosition = position;
                notifyItemChanged(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SelectSizeItemBinding binding;
        public ViewHolder(@NonNull SelectSizeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(String.valueOf(productCountries.get(position)));
            if (position == currentPosition) {
                binding.constraintLayout.setBackgroundResource(R.drawable.slight_rounded_red_rectangle);
            }
            else {
                binding.constraintLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_gray_stroke);
            }
        }
    }
}
