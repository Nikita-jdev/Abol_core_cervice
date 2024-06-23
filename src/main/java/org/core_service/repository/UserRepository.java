package org.core_service.repository;

import org.core_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM users
            WHERE userName = :userName
            """)
    Optional<User> findByUserName(String userName);
}
