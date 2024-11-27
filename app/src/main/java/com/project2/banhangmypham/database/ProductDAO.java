package com.project2.banhangmypham.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.project2.banhangmypham.model.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertProduct(List<Product> product);
    @Delete
    Completable deleteProduct(Product product);

    @Query("SELECT * FROM Product")
    Single<List<Product>> getAllProductList();

}
