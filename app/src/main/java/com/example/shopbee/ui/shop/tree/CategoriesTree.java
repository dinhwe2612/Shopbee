package com.example.shopbee.ui.shop.tree;

import android.content.Context;

import com.example.shopbee.R;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
    private CategoryNode findNode(String name, CategoryNode current) {
        if (CategoriesHashMap
                .getInstance()
                .getCategories()
                .get(current.getId())
                .equals(name)) {
            return current;
        }
        for (int i = 0; i < current.getChildren().size(); i++) {
            CategoryNode node = findNode(name, current.getChildren().get(i));
            if (node != null) return node;
        }
        return null;
    }
    public CategoryNode findNode(String name) {
        CategoryNode current = head;
        return findNode(name, current);
    }
    private CategoriesTree(Context context) throws IOException {
        head = new CategoryNode();
        CategoryNode current = head;
        int currentIndex = 0;
        InputStream inputStream = context.getResources().openRawResource(R.raw.tree);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            // Split the line into an array of strings
            String[] elements = line.trim().split("\\s+");

            // Convert the array to an ArrayList
            ArrayList<String> lineData = new ArrayList<>(Arrays.asList(elements));

            // Process the ArrayList (example: print each element)
            for (String element : lineData) {
                CategoryNode newNode = new CategoryNode(element);
                current.addChild(newNode);
            }
            if (current.getParent() == null ||
                    (current.getParent() != null
                            && current.getParent().getChildren().size() <= currentIndex + 1)) {
                current = current.getChildren().get(0);
                currentIndex = 0;
            }
            else {
                current = current.getParent().getChildren().get(currentIndex + 1);
                currentIndex += 1;
            }
        }
    }

}
