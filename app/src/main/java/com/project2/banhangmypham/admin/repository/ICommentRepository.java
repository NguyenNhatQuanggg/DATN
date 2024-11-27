package com.project2.banhangmypham.admin.repository;

import com.project2.banhangmypham.model.CommentResponse;
import com.project2.banhangmypham.model.MessageResponse;

import io.reactivex.rxjava3.core.Single;

public interface ICommentRepository {
    Single<MessageResponse> updateStateComment(String _id, int status);
    Single<CommentResponse> getALlCommentsList();
}
