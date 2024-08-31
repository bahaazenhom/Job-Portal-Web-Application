package com.project.jobportal.service;

import com.project.jobportal.entity.UserType;
import com.project.jobportal.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll(){
        return userTypeRepository.findAll();
    }
}
