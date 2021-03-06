package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.Mesoregion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesoregionRepository extends JpaRepository<Mesoregion, Long> {

    Mesoregion findByNameIgnoreCase(String name);

}
