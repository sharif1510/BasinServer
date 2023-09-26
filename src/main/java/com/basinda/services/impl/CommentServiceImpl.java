package com.basinda.services.impl;

import com.basinda.models.entity.User;
import com.basinda.models.entity.Comment;
import com.basinda.services.CommentService;
import org.springframework.stereotype.Service;
import com.basinda.repositories.UserRepository;
import com.basinda.repositories.CommentRepository;
import com.basinda.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public String createComment(Comment request) throws ResourceNotFoundException{
        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(
                () ->  new ResourceNotFoundException("User not found.")
        );
        if (user != null) {
            User owner = userRepository.findByUserId(request.getOwnerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Owner not found.")
            );
            if (owner != null) {
                Comment comment = commentRepository.save(request);
                if (comment != null) {
                    return "Created";
                } else {
                    return "Something went wrong.";
                }
            }
        }
        return "Something went wrong.";
    }
}