package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface USerRepository extends JpaRepository <User, Integer> {

    @Query(value = "Select u.employee_id from user u inner join credential c " +
            "on u.employee_id=c.employee_id where (u.name =:userName or u.email =:userName) and c.role!='ROLE_ADMIN' ", nativeQuery = true)
//    "or email LIKE ?1 ")
    List<?> searchByUserName(String userName);

    @Query(value = "Select employee_id from user where (name =:userName or email =:userName) and employee_status='Active'", nativeQuery = true)
    List<?> searchActiveByUserName(String userName);

    @Query(value="SELECT * FROM user WHERE email=:email",nativeQuery = true)
    User findByEmail(String email);
}
