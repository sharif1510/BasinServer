package com.basinda.services;

import com.basinda.models.entity.Comment;

public interface CommentService {
    String createComment(Comment request);
}