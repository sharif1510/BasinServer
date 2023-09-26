package com.basinda.services.impl;

import com.basinda.models.entity.FlatImage;
import com.basinda.services.FlatImageService;
import org.springframework.stereotype.Service;
import com.basinda.repositories.FlatImageRepository;

@Service
public class FlatImageServiceImpl implements FlatImageService {
    private final FlatImageRepository flatImageRepository;

    public FlatImageServiceImpl(FlatImageRepository flatImageRepository) {
        this.flatImageRepository = flatImageRepository;
    }

    @Override
    public void flatImageSave(FlatImage flatImage) {
        flatImageRepository.save(flatImage);
    }
}