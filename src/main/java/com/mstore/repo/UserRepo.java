package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mstore.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	public User findByEmail(String email);
	
	public User findByEmailAndPassword(@Param("email")String email , @Param("password") String password);
	
//	@Query(value = "UPDATE User u set u.activated= CASE WHEN u.activated=true THEN false ELSE true END WHERE id =:userId")
//	public User deactivateAccount(@Param("userId")Long userId);
}
