package com.basinda.services;

import com.basinda.models.entity.Flat;
import com.basinda.models.request.user.ApproveFlatRequest;
import com.basinda.models.request.user.RentFlatRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlatService {
    List<Flat> read();
    List<Flat> searchFlat(String searchField);
    Flat createFlat(Flat flat);

    void uploadFlatImage(Long flatId, MultipartFile image);

    byte[] downloadFlatImage(Long flatId);

    List<Flat> getFlatForUsers(Long id);

    void rentFlat(Long id, RentFlatRequest request);
    void approveFlat(Long id, ApproveFlatRequest request);
}