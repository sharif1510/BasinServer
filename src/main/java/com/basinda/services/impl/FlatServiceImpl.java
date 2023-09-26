package com.basinda.services.impl;

import com.basinda.exceptions.UserRequestPendingAlreadyException;
import com.basinda.file.S3Bucket;
import com.basinda.file.S3Service;
import com.basinda.models.eFlatType;
import com.basinda.models.entity.Flat;
import com.basinda.models.entity.User;
import com.basinda.models.request.user.ApproveFlatRequest;
import com.basinda.models.request.user.RentFlatRequest;
import com.basinda.repositories.UserRepository;
import com.basinda.services.FlatService;
import com.basinda.models.entity.FlatImage;
import com.basinda.services.FlatImageService;
import org.springframework.stereotype.Service;
import com.basinda.repositories.FlatRepository;
import com.basinda.repositories.FlatImageRepository;
import org.springframework.web.multipart.MultipartFile;
import com.basinda.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FlatServiceImpl implements FlatService {
    private final S3Bucket s3Bucket;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final FlatRepository flatRepository;
    private final FlatImageService flatImageService;
    private final FlatImageRepository flatImageRepository;

    public FlatServiceImpl(S3Bucket s3Bucket, S3Service s3Service, UserRepository userRepository, FlatRepository flatRepository, FlatImageService flatImageService, FlatImageRepository flatImageRepository) {
        this.s3Bucket = s3Bucket;
        this.s3Service = s3Service;
        this.userRepository = userRepository;
        this.flatRepository = flatRepository;
        this.flatImageService = flatImageService;
        this.flatImageRepository = flatImageRepository;
    }

    @Override
    public List<Flat> read() {
        //return flatRepository.findByFlatTypeNot(eFlatType.eBooked);
        return flatRepository.findAll();
    }

    @Override
    public List<Flat> searchFlat(String searchField) {
        return null;
    }

    @Override
    public Flat createFlat(Flat flat) {
        userRepository.findById(flat.getUserId()).orElseThrow(()->new ResourceNotFoundException());
        flat.setFlatType(eFlatType.eActive);
        return flatRepository.save(flat);
    }

    @Override
    public void uploadFlatImage(Long flatId, MultipartFile image) {
        flatRepository.findById(flatId)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Flat not found [%s]".formatted(flatId)));
        String flatImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Bucket.getBasinda(),
                    "flat-images/%s/%s".formatted(flatId, flatImageId),
                    image.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /** TODO: storage image file here. */
        FlatImage flatImage = new FlatImage();
        flatImage.setFlatId(flatId);
        flatImage.setImageUrl(flatImageId);
        flatImageService.flatImageSave(flatImage);
    }

    @Override
    public byte[] downloadFlatImage(Long flatId) {
        Flat flat = flatRepository.findById(flatId)
                .orElseThrow(()->new ResourceNotFoundException("Flat not found [%s]".formatted(flatId)));
        /** TODO : check if flat image found or not*/

        List<FlatImage> flatImages = flatImageRepository.findByFlatId(flatId);
        var flatImageId = flatImages.get(0).getImageUrl();
        return s3Service.getObject(
                s3Bucket.getBasinda(),
                "flat-images/%s/%s".formatted(flatId,flatImageId)
        );
    }

    @Override
    public List<Flat> getFlatForUsers(Long id) {
        return flatRepository.findByUserId(id);
    }

    @Override
    public void rentFlat(Long id, RentFlatRequest request) {
        Flat flat = flatRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Flat not found that you want rent."));
        User user = userRepository.findById(request.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found that you want rent."));

        flat.getRequestUser().forEach((requestUser) -> {
            if(requestUser.getId() == user.getId()){
                throw new UserRequestPendingAlreadyException("Your request already please wait.");
            }
        });

        if (flat.getFlatType() == eFlatType.eActive || flat.getFlatType() == eFlatType.eRequest){
            flat.setFlatType(eFlatType.eRequest);
            flat.getRequestUser().add(user);
            flatRepository.save(flat);
        }
        else{
            throw new ResourceNotFoundException("Flat is not rent at this time.");
        }
    }

    @Override
    public void approveFlat(Long id, ApproveFlatRequest request) {
        Flat flat = flatRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Flat not found that you want approved."));
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found that you want approved."));
        if (flat.getFlatType() == eFlatType.eRequest){
            flat.setFlatType(eFlatType.eBooked);
            flat.getRequestUser().clear();
            flat.setApprovedUser(user);
            flatRepository.save(flat);
        }
        else{
            throw new ResourceNotFoundException("Flat is not approved at this time.");
        }
    }
}