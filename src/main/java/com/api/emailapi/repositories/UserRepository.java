package com.api.emailapi.repositories;

import com.api.emailapi.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    @Query("select u from UserEntity u where upper(u.email) like upper(concat('%', ?1, '%'))")
    Optional<UserEntity> findEmail(@NonNull String email);
//
    @Query("""
            select (count(u) > 0) from UserEntity u
            where upper(u.email) like upper(concat('%', ?1, '%')) and u.password like concat('%', ?2, '%')""")
    boolean checkIfEmailAndPasswordMatches(@NonNull String email, @NonNull String password);

}
