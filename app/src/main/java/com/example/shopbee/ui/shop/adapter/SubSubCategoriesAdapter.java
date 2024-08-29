package com.example.shopbee.ui.shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.databinding.CategoriesShopNewItem2Binding;
import com.example.shopbee.databinding.CategoriesShopNewItem2ItemBinding;
import com.example.shopbee.ui.shop.ShopNavigator;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.example.shopbee.ui.shop.tree.CategoriesTree;
import com.example.shopbee.ui.shop.tree.CategoryNode;

import java.io.IOException;
import java.util.ArrayList;

import coil.ImageLoader;
import coil.ImageLoaders;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubSubCategoriesAdapter extends BaseAdapter<SubSubCategoriesAdapter.ViewHolder> {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Context context;
    private ArrayList<String> subSubCategories;
    public SubSubCategoriesAdapter(Context context, String subCategory, ShopNavigator navigator) throws IOException {
        super(navigator);
        this.context = context;
        subSubCategories = new ArrayList<>();
        for (CategoryNode subSubCategory: CategoriesTree.getInstance(context).findNode(subCategory).getChildren()) {
            subSubCategories.add(subSubCategory.getId());
        }
    }

    @NonNull
    @Override
    public SubSubCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoriesShopNewItem2ItemBinding binding = CategoriesShopNewItem2ItemBinding.inflate(inflater, parent, false);
        return new SubSubCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubSubCategoriesAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getNavigator().navigateToSearchByCategory(subSubCategories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }
    public class ViewHolder extends BaseAdapter.BaseViewHolder {
        private CategoriesShopNewItem2ItemBinding binding;
        public ViewHolder(@NonNull CategoriesShopNewItem2ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindView(int position) {
            binding.textView.setText(CategoriesHashMap.getInstance().getCategories().get(subSubCategories.get(position)));
//            Glide.with(binding.imageView.getContext()).load(CategoriesHashMap.getInstance().getCategoriesLink(subSubCategories.get(position))).into(binding.imageView);
            compositeDisposable.add(Observable.fromCallable(() -> {
                       FutureTarget<Bitmap> futureTarget = Glide.with(binding.imageView.getContext())
                       .asBitmap()
                       .load(CategoriesHashMap.getInstance()
                       .getCategoriesLink(subSubCategories.get(position)))
                       .submit();
                       return futureTarget.get();
                   })
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(bitmap -> {
                       binding.imageView.setImageBitmap(bitmap);
                   }, throwable -> {
                       Log.e("Exception", throwable.getMessage());
                   })
            );
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.clear();
    }
}
