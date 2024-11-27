package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.admin.CategoryResponse;
import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.MessageResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ICategoryRepository {
    Single<Category> addCategory(Category category);
    Single<Category> updateCategory(Category category);
    Single<MessageResponse> deleteCategory(String id);
    Single<CategoryResponse> getAllCategoryList();
}
