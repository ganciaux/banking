package com.ganc.banking.repositories;

import com.ganc.banking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findAllByFirstname(String firstname);

    @Query("from User where firstname = :fn")
    List<User> searchByFirstname(@Param("fn") String firstname);

    @Query("from User where firstname = '%:fn%'")
    List<User> searchByFirstnameContaining(@Param("fn") String firstname);

    @Query(value="select * from _user where firstname = :firstname", nativeQuery=true)
    User searchUserNative(String firstname);
}
