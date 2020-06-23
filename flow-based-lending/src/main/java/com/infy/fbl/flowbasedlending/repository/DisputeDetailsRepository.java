package com.infy.fbl.flowbasedlending.repository;

import com.infy.fbl.flowbasedlending.model.DisputeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeDetailsRepository extends JpaRepository<DisputeDetails,String> {
}
