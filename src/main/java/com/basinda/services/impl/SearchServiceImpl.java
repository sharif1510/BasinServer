package com.basinda.services.impl;

import com.basinda.exceptions.ResourceNotFoundException;
import com.basinda.models.entity.*;
import com.basinda.models.request.user.FlatSearchRequest;
import com.basinda.repositories.*;
import com.basinda.services.SearchService;
import org.springframework.stereotype.Service;
import com.basinda.models.request.user.SearchRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final UpozilaRepository upozilaRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final PourosovaRepository pourosovaRepository;

    public SearchServiceImpl(UserRepository userRepository, CommentRepository commentRepository, UpozilaRepository upozilaRepository, DivisionRepository divisionRepository, DistrictRepository districtRepository, PourosovaRepository pourosovaRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.upozilaRepository = upozilaRepository;
        this.divisionRepository = divisionRepository;
        this.districtRepository = districtRepository;
        this.pourosovaRepository = pourosovaRepository;
    }

    @Override
    public List<Comment> searchComment(SearchRequest request) {
        return commentRepository.findByOwnerId(request.getSearchedField());
    }

    @Override
    public List<Flat> searchFlat(FlatSearchRequest request) {
        List<Flat> flats = new ArrayList<>();
        if(request.getPourosovaId() != -1){
            Pourosova pourosova = pourosovaRepository.findById(request.getPourosovaId())
                    .orElseThrow(()->new ResourceNotFoundException());
            if (pourosova != null){
                List<User> users = userRepository.findByPourosova(pourosova.getName());
                users.forEach((user -> {
                    flats.addAll(user.getFlats());
                }));
                return flats;
            }
        }
        else if (request.getUpozillaId() != -1){
            Upozila upozila = upozilaRepository.findById(request.getUpozillaId())
                    .orElseThrow(()->new ResourceNotFoundException());
            if (upozila != null){
                List<User> users = userRepository.findByUpozilla(upozila.getName());
                users.forEach((user -> {
                    flats.addAll(user.getFlats());
                }));
                return flats;
            }
        }
        else if (request.getDistrictId() != -1){
            District district = districtRepository.findById(request.getDistrictId())
                    .orElseThrow(()->new ResourceNotFoundException());
            if (district != null){
                List<User> users = userRepository.findByDistrict(district.getName());
                users.forEach((user -> {
                    flats.addAll(user.getFlats());
                }));
                return flats;
            }
        }
        else if (request.getDivisionId() != -1){
            Division division = divisionRepository.findById(request.getDistrictId())
                    .orElseThrow(()->new ResourceNotFoundException());
            if (division != null){
                List<User> users = userRepository.findByDivision(division.getName());
                users.forEach((user -> {
                    flats.addAll(user.getFlats());
                }));
                return flats;
            }
        }
        return flats;
    }
}