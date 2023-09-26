package com.basinda.services;

import com.basinda.models.entity.Upozila;
import com.basinda.models.entity.District;
import com.basinda.models.entity.Division;
import com.basinda.models.entity.Pourosova;
import com.basinda.models.request.admin.UpozilaRequest;
import com.basinda.models.request.admin.DistrictRequest;
import com.basinda.models.request.admin.DivisionRequest;
import com.basinda.models.request.admin.PourosovaRequest;

import java.util.List;

public interface LocationService {
    String createDivision(DivisionRequest request);
    String createDistrict(DistrictRequest request);
    String createUpozila(UpozilaRequest request);
    String createPourosova(PourosovaRequest request);
    List<Division> readDivision();
    List<District> readDistrictForDivision(Long divisionId);
    List<Upozila> readUpozilaForDistrict(Long districtId);
    List<Pourosova> readPourosovaForUpozila(Long upozilaId);
    List<Division> getAllDivisions();
    List<District> getAllDistricts();
    List<Upozila> getAllUpozilas();
    List<Pourosova> getAllPourosovas();
    Division editDivision(Long id, DivisionRequest model);
    District editDistrict(Long id, DistrictRequest model);
    Upozila editUpozila(Long id, UpozilaRequest model);
    Pourosova editPourosova(Long id, PourosovaRequest model);
    Division deleteDivision(Long id);
    District deleteDistrict(Long id);
    Upozila deleteUpozilla(Long id);
    Pourosova deletePourosova(Long id);
}