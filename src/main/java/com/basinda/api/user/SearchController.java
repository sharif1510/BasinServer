package com.basinda.api.user;

import com.basinda.models.entity.Flat;
import com.basinda.models.entity.Comment;
import com.basinda.services.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.basinda.models.response.ResponseHeader;
import com.basinda.models.request.user.SearchRequest;
import com.basinda.models.request.user.FlatSearchRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public class FlatResponse extends ResponseHeader{
        public List<Flat> flats;
    }

    public class SearchResponse extends ResponseHeader{
        public List<Comment> reviews;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> searchReview(@RequestBody SearchRequest request){
        SearchResponse response = new SearchResponse();
        List<Comment> reviews = searchService.searchComment(request);
        response.reviews = reviews;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/flat/search")
    public ResponseEntity<FlatResponse> searchFlat(@RequestBody FlatSearchRequest request){
        FlatResponse response = new FlatResponse();
        List<Flat> flats = searchService.searchFlat(request);
        response.flats = flats;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}