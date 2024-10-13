package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
