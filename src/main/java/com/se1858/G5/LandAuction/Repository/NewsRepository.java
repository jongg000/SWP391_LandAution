package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findTop4ByOrderByTimeDesc();
    @Query("SELECT n FROM News n WHERE n.title LIKE %:title%")
    List<News> findByTitle(@Param("title") String title);
}
