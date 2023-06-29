package com.ak.loanengine.loanengine.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Find User by code.
     *
     * @param personalCode unique users code
     * @return Optional of User
     */
    Optional<User> findByCode(String personalCode);

}
