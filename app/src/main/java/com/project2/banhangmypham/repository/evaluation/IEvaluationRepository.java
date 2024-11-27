package com.project2.banhangmypham.repository.evaluation;

import com.project2.banhangmypham.model.EvaluationRequestModel;
import com.project2.banhangmypham.model.EvaluationResponse;
import com.project2.banhangmypham.model.MessageResponse;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface IEvaluationRepository {
    Single<MessageResponse> addEvaluationRepository(EvaluationRequestModel evaluationRequestModel);
    Completable getAllEvaluationRepository();
    Single<EvaluationResponse> getEvaluationProductById(String idProduct);
}
