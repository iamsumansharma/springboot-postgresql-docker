package com.infy.fbl.flowbasedlending.repository;

import com.infy.fbl.flowbasedlending.model.ComplaintDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintDetailsRepository extends JpaRepository<ComplaintDetails,String> {
}
