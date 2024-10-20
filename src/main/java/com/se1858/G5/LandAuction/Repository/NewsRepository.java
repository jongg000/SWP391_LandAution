package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    News findByNewsId(int newsId);
}
