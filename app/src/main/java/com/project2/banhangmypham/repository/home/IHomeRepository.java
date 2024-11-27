package com.project2.banhangmypham.repository.home;

import com.project2.banhangmypham.model.HomeResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface IHomeRepository {
    Single<HomeResponse> getHomeScreenData();
}
