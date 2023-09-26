package com.basinda.services.impl;

import com.basinda.models.entity.Upozila;
import com.basinda.models.entity.District;
import com.basinda.models.entity.Division;
import com.basinda.models.entity.Pourosova;
import com.basinda.services.LocationService;
import org.springframework.stereotype.Service;
import com.basinda.repositories.UpozilaRepository;
import com.basinda.repositories.DistrictRepository;
import com.basinda.repositories.DivisionRepository;
import com.basinda.repositories.PourosovaRepository;
import com.basinda.models.request.admin.UpozilaRequest;
import com.basinda.models.request.admin.DistrictRequest;
import com.basinda.exceptions.ResourceNotFoundException;
import com.basinda.models.request.admin.DivisionRequest;
import com.basinda.models.request.admin.PourosovaRequest;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final UpozilaRepository upozilaRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final PourosovaRepository pourosovaRepository;

    public LocationServiceImpl(UpozilaRepository upozilaRepository, DivisionRepository divisionRepository, DistrictRepository districtRepository, PourosovaRepository pourosovaRepository) {
        this.upozilaRepository = upozilaRepository;
        this.divisionRepository = divisionRepository;
        this.districtRepository = districtRepository;
        this.pourosovaRepository = pourosovaRepository;
    }

    @Override
    public String createDivision(DivisionRequest request) {
        Division division = new Division();
        division.setName(request.getName());
        Division createdDivision = divisionRepository.save(division);
        if (createdDivision != null) {
            return "Created";
        }
        else{
            return "Not Created";
        }
    }

    @Override
    public String createDistrict(DistrictRequest request) {
        divisionRepository.findById(request.getDivisionId()).orElseThrow(()->new ResourceNotFoundException());
        District district = new District();
        district.setName(request.getName());
        district.setDivisionId(request.getDivisionId());
        District createdDistrict = districtRepository.save(district);
        if (createdDistrict != null) {
            return "Created";
        }
        else{
            return "Not Created";
        }

    }

    @Override
    public String createUpozila(UpozilaRequest request) {
        districtRepository.findById(request.getDistrictId()).orElseThrow(()->new ResourceNotFoundException());
        Upozila upozila = new Upozila();
        upozila.setName(request.getName());
        upozila.setDistrictId(request.getDistrictId());
        Upozila createUpozila = upozilaRepository.save(upozila);
        if (createUpozila != null) {
            return "Created";
        }
        else{
            return "Not Created";
        }
    }

    @Override
    public String createPourosova(PourosovaRequest request) {
        upozilaRepository.findById(request.getUpozilaId()).orElseThrow(()->new ResourceNotFoundException());
        Pourosova pourosova = new Pourosova();
        pourosova.setName(request.getName());
        pourosova.setUpozilaId(request.getUpozilaId());
        Pourosova createdPourosova = pourosovaRepository.save(pourosova);
        if (createdPourosova != null) {
            return "Created";
        }
        else{
            return "Not Created";
        }
    }

    @Override
    public List<Division> readDivision() {
        return divisionRepository.findAll();
    }

    @Override
    public List<District> readDistrictForDivision(Long divisionId) {
        return districtRepository.findByDivisionId(divisionId);
    }

    @Override
    public List<Upozila> readUpozilaForDistrict(Long districtId) {
        return upozilaRepository.findByDistrictId(districtId);
    }

    @Override
    public List<Pourosova> readPourosovaForUpozila(Long upozilaId) {
        return pourosovaRepository.findByUpozilaId(upozilaId);
    }

    @Override
    public List<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }

    @Override
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @Override
    public List<Upozila> getAllUpozilas() {
        return upozilaRepository.findAll();
    }

    @Override
    public List<Pourosova> getAllPourosovas() {
        return pourosovaRepository.findAll();
    }

    @Override
    public Division editDivision(Long id, DivisionRequest model) {
        Division division = divisionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        division.setName(model.getName());
        divisionRepository.save(division);
        return division;
    }

    @Override
    public District editDistrict(Long id, DistrictRequest model) {
        District district = districtRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        district.setName(model.getName());
        district.setDivisionId(model.getDivisionId());
        districtRepository.save(district);
        return district;
    }

    @Override
    public Upozila editUpozila(Long id, UpozilaRequest model) {
        Upozila upozila = upozilaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        upozila.setName(model.getName());
        upozila.setDistrictId(model.getDistrictId());
        upozilaRepository.save(upozila);
        return upozila;
    }

    @Override
    public Pourosova editPourosova(Long id, PourosovaRequest model) {
        Pourosova pourosova = pourosovaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        pourosova.setName(model.getName());
        pourosova.setUpozilaId(model.getUpozilaId());
        pourosovaRepository.save(pourosova);
        return pourosova;
    }

    @Override
    public Division deleteDivision(Long id) {
        Division division = divisionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        divisionRepository.delete(division);
        return division;
    }

    @Override
    public District deleteDistrict(Long id) {
        District district = districtRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        districtRepository.delete(district);
        return district;
    }

    @Override
    public Upozila deleteUpozilla(Long id) {
        Upozila upozila = upozilaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        upozilaRepository.delete(upozila);
        return upozila;
    }

    @Override
    public Pourosova deletePourosova(Long id) {
        Pourosova pourosova = pourosovaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException());
        pourosovaRepository.delete(pourosova);
        return pourosova;
    }
}