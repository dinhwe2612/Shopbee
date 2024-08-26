package com.example.shopbee.ui.shop.tree;

import java.util.ArrayList;
import java.util.List;

public class CategoryNode {
    public CategoryNode(String id) {
        this.id = id;
        children = new ArrayList<>();
    }
    public CategoryNode() {
        this.id = "";
        children = new ArrayList<>();
    }
    private CategoryNode parent;
    private String id;
    private List<CategoryNode> children;
    public void setParent(CategoryNode parent) {
        this.parent = parent;
    }
    public void addChild(CategoryNode child) {
        children.add(child);
        child.setParent(this);
    }
    public  CategoryNode getParent() {
        return parent;
    }
    public String getId() {
        return id;
    }
    public List<CategoryNode> getChildren() {
        return children;
    }
}
