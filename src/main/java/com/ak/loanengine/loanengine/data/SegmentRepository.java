package com.ak.loanengine.loanengine.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Segment Repository
 */
@Repository
public interface SegmentRepository extends CrudRepository<Segment, Long> {
    /**
     * Find Segment by name.
     *
     * @param name unique name of the segment
     *
     * @return Optional of Segment
     */
    Optional<Segment> findByName(String name);

}
