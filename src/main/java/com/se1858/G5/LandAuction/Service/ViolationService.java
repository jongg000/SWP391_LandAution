package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.Violation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ViolationService {
    public Page<Violation> getAllViolations(PageRequest pageRequest);
}
