package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    News findByNewsId(int newsId);
    @Query("SELECT n FROM News n WHERE n.title LIKE %:search%")
    List<News> findByTitleLike(@Param("search") String search);
}
