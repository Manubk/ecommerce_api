package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mstore.model.Address;
import com.mstore.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
	
  public List<Address> findByUser(User user);
  
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM Address a WHERE a.id=:id AND a.user=:user")
   void deleteAddressOfUser(@Param("id")Long addressId,@Param("user") User user);
}
