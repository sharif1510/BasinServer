package com.basinda.services;

import com.basinda.models.entity.Profession;
import com.basinda.models.request.ProfessionRequest;

import java.util.List;

public interface ProfessionService {
    List<Profession> getAllProfession();
    Profession createProfession(ProfessionRequest request);
}