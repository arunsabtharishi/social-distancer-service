package com.socialdistancer.repo;

import com.socialdistancer.model.LocationDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDetailsRepository extends CrudRepository<LocationDetails, String> {}
