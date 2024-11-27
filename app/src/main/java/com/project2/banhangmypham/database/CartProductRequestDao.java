package com.project2.banhangmypham.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project2.banhangmypham.model.CartProductRequest;
import com.project2.banhangmypham.model.Category;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CartProductRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCateProductRequest(CartProductRequest cartProductRequest);
    @Delete
    Completable deleteCateProductRequest(CartProductRequest cartProductRequest);
}
