package com.project2.banhangmypham.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project2.banhangmypham.model.Category;
import com.project2.banhangmypham.model.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCategory(List<Category> categoryList);
    @Delete
    Completable deleteCategory(Category product);
    @Query("SELECT * FROM Category")
    Single<List<Category>> getAllCategoryList();
}
