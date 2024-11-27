package com.project2.banhangmypham.model;

import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeResponse {
    private List<Product> productFamousList = new ArrayList<>();
    private List<Product> productDiscountList = new ArrayList<>();
    private List<Product> productsList = new ArrayList<>();
    private HashMap<String, List<Product>> productMap = new HashMap();
    private List<Category> categoryList = new ArrayList<>();

    public HomeResponse(List<Product> productFamousList, List<Product> productDiscountList, HashMap<String, List<Product>> productMap,
                        List<Category> categoryList, List<Product> productList) {
        this.productFamousList = productFamousList;
        this.productDiscountList = productDiscountList;
        this.productMap = productMap;
        this.categoryList = categoryList;
        this.productsList = productList;
    }

    public List<Product> getProductFamousList() {
        return productFamousList;
    }

    public List<Product> getProductDiscountList() {
        return productDiscountList;
    }

    public HashMap<String, List<Product>> getProductMap() {
        return productMap;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Product> getProductsList() {
        return productsList;
    }
}
