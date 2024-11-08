package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.Violation;
import com.se1858.G5.LandAuction.Repository.ViolationRepository;
import com.se1858.G5.LandAuction.Service.ViolationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationServiceImpl implements ViolationService {


    @Autowired
    ViolationRepository violationRepository;

    public Page<Violation> getAllViolations(PageRequest pageRequest) {
        return violationRepository.findAll(pageRequest);
    }

    @Override
    public void saveViolation(Violation violation) {
        violationRepository.save(violation);
    }


}
