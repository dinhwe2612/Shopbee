package com.example.shopbee.ui.profile.myorder.typeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.OrderProductItem;
import com.example.shopbee.ui.profile.adapter.OrderProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProcessingFragment extends Fragment implements OrderProductAdapter.Listener{
    List<OrderProductItem> orderProductItemList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.processing_fragment,container, false);
        setUpOrderProductItemList();

        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(orderProductItemList, this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProcessing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderProductAdapter);

        return view;
    }
    void setUpOrderProductItemList(){
        orderProductItemList = new ArrayList<>();
        orderProductItemList.add(new OrderProductItem("No1947034", "05-12-2019", "IW3475453455", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947035", "05-12-2019", "IW3475453456", 2, "120$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947036", "05-12-2019", "IW3475453457", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947037", "05-12-2019", "IW3475453458", 2, "110$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947038", "05-12-2019", "IW3475453459", 2, "110$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947039", "05-12-2019", "IW3475453460", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947134", "05-12-2019", "IW3475453461", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947234", "05-12-2019", "IW3475453462", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947334", "05-12-2019", "IW3475453463", 2, "100$", "Processing"));
        orderProductItemList.add(new OrderProductItem("No1947434", "05-12-2019", "IW3475453464", 2, "100$", "Processing"));
    }

    @Override
    public void onItemClicked(int position) {

    }
}