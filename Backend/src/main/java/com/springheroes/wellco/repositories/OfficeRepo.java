package com.springheroes.wellco.repositories;

import com.springheroes.wellco.entities.Office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  OfficeRepo extends JpaRepository<Office,Integer> {

//    @Query("select o from Office o where o.OfficeName =: OfficeName")
//    Office findOfficeByOfficeName(@Param("OfficeName") String OfficeName);

    Office findOfficeByOfficeName(String OfficeName);
}
