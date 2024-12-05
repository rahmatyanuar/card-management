package com.rahmat.card.repo;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rahmat.card.model.Card;

@Repository
@Transactional
public interface CardRepository extends JpaRepository<Card, Long>{

	Card findByCardNumber(String string);
	
	@Query("SELECT c FROM Card c WHERE c.cardNumber = ?1 AND c.status = ?2")
	Card findByCardAndStatus(String cardNo, String status);

	Boolean existsByCardNumber(String cardNumber);

	Long deleteByCardNumber(String cardno);

	@Modifying
	@Query("UPDATE Card c SET c.status = ?2, c.updateDT = ?3 WHERE c.cardNumber = ?1")
	int updateCardStatus(@Valid String cardNo, String status, Date updateDate);
}
