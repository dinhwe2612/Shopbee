package com.example.shopbee.ui.shop.tree;

import android.content.Context;
import android.util.Log;

import com.example.shopbee.R;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Singleton;

public class CategoriesTree {
    private static CategoriesTree instance;
    public static CategoriesTree getInstance(Context context) throws IOException {
        if (instance == null) {
            instance = new CategoriesTree(context);
        }
        return instance;
    }
    private CategoryNode head;
    public CategoryNode getHead() {
        return head;
    }
    private CategoryNode findNode(String id, CategoryNode current) {
        if (current.getId().equals(id)) {
            return current;
        }
        for (int i = 0; i < current.getChildren().size(); i++) {
            CategoryNode node = findNode(id, current.getChildren().get(i));
            if (node != null) return node;
        }
        return null;
    }
    public CategoryNode findNode(String id) {
        CategoryNode current = head;
        return findNode(id, current);
    }
    private CategoriesTree(Context context) throws IOException {
        ArrayList<CategoryNode> parents = new ArrayList<>();
        int currentIndex = 0;

        head = new CategoryNode();
        parents.add(head);
        CategoryNode current = parents.get(currentIndex);

        InputStream inputStream = context.getResources().openRawResource(R.raw.tree);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            // Split the line into an array of strings
            String[] elements = line.trim().split("\\s+");

            // Convert the array to an ArrayList
            ArrayList<String> lineData = new ArrayList<>(Arrays.asList(elements));

            // Process the ArrayList (example: print each element)
            if (current.getParent() != null && CategoriesHashMap.getInstance().getCategories().containsKey(current.getParent().getId())) {
                Log.d("CategoriesTree", "Parent: " + CategoriesHashMap.getInstance().getCategories().get(current.getParent().getId()));
            }


            Log.d("CategoriesTree", "Parent: " + CategoriesHashMap.getInstance().getCategories().get(current.getId()));
            for (String element : lineData) {
                CategoryNode newNode = new CategoryNode(element);
                current.addChild(newNode);
                parents.add(newNode);
                Log.d("CategoriesTree", "Adding child: " + CategoriesHashMap.getInstance().getCategories().get(element));
            }
            parents.remove(0);
            current = parents.get(0);
            Log.d("CategoriesTree", "\n");
        }
    }

}
