package com.basinda.services;

import com.basinda.models.entity.Flat;
import com.basinda.models.entity.Comment;
import com.basinda.models.request.user.SearchRequest;
import com.basinda.models.request.user.FlatSearchRequest;

import java.util.List;

public interface SearchService {
    List<Comment> searchComment(SearchRequest request);

    List<Flat> searchFlat(FlatSearchRequest request);
}