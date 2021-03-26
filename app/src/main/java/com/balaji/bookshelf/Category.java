package com.balaji.bookshelf;

public class Category {
    public String categoryName;
    public int categoryCount;
    Category(){}
    Category(String name, int count){
        this.categoryName = name;
        this.categoryCount = count;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }
}
