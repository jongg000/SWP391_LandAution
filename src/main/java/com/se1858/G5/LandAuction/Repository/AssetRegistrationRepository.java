package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AssetRegistrationRepository extends JpaRepository<AssetRegistration,Integer> {
    @Query(value = "SELECT ar.document_id AS documentId, ar.approval_date AS approvalDate, ar.comments AS comments, " +
            "ar.statusid AS statusId, ar.userid AS userId, " +
            "l.land_id AS landId, l.contact AS contact, l.description AS description, l.district AS district, " +
            "l.location AS location, l.name AS name, l.path AS path, l.price AS price, l.province AS province, " +
            "l.ward AS ward, " +
            "li.image_id AS imageId, li.image_url AS imageUrl, li.name AS imageName " +
            "FROM asset_registration ar " +
            "JOIN land l ON ar.landid = l.land_id " +
            "LEFT JOIN land_image li ON li.land_land_id = l.land_id " +
            "WHERE ar.document_id = :documentId", nativeQuery = true)
    List<Object[]> findAssetInfoByDocumentId(@Param("documentId") Integer documentId);

    @Query(value = "SELECT ar.document_id AS documentId, ar.approval_date AS approvalDate, ar.comments AS comments, " +
            "ar.statusid AS statusId, ar.userid AS userId, " +
            "l.land_id AS landId, l.contact AS contact, l.description AS description, l.district AS district, " +
            "l.location AS location, l.name AS name, l.path AS path, l.price AS price, l.province AS province, " +
            "l.ward AS ward, " +
            "li.image_id AS imageId, li.image_url AS imageUrl, li.name AS imageName " +
            "FROM asset_registration ar " +
            "JOIN land l ON ar.landid = l.land_id " +
            "LEFT JOIN land_image li ON li.land_land_id = l.land_id " +
            "WHERE (:approvalDate IS NULL OR ar.approval_date = :approvalDate) " +
            "AND (:statusId IS NULL OR ar.statusid = :statusId)", nativeQuery = true)
    List<Object[]> findAssetInfoByApprovalDateAndStatus(
            @Param("approvalDate") LocalDateTime approvalDate,
            @Param("statusId") Integer statusId
    );
}
