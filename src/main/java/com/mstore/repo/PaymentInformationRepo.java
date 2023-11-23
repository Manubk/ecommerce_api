package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mstore.model.PayMentInformation;
import com.mstore.model.User;

@Repository
public interface PaymentInformationRepo extends JpaRepository<PayMentInformation, Long>{

	public List<PayMentInformation> findByUser(User user);
}
