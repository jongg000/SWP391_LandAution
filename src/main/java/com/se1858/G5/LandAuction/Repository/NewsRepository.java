package com.se1858.G5.LandAuction.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
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
    Page<News> getNewsByUser(User user, Pageable pageable);
    Page<News> findAll(Pageable pageable);
}
