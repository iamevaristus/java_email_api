package com.api.emailapi.repositories;

import com.api.emailapi.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("select t from Token t where t.token = ?1 and t.email = ?2")
    Optional<Token> checkIfEmailAndTokenMatches(@NonNull String token, @NonNull String email);
}