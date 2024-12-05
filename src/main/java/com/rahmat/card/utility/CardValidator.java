package com.rahmat.card.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahmat.card.enums.BusinessError;
import com.rahmat.card.model.Card;
import com.rahmat.card.model.GeneralResponse;
import com.rahmat.card.repo.CardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardValidator {
	@Autowired
	private CardRepository cardRepo;
	
	public GeneralResponse validateCardReq(Card req) {
		GeneralResponse res = new GeneralResponse();
		try {
			if(req.getCardNumber().length()>12 || req.getCardNumber().length() == 0 || req.getCardNumber() == null) {
				res.setStatusCode(BusinessError.CARD_LENGTH.getCode());
				res.setStatusMessage(BusinessError.CARD_LENGTH.getDescription());
				if(req.getStatus().length()>1) {
					res.setStatusCode(BusinessError.STATUS_LENGTH.getCode());
					res.setStatusMessage(BusinessError.STATUS_LENGTH.getDescription());
				}
			}
		}catch (Exception e) {
			log.error(e.toString());
		}
		return res;
	}
	
	public GeneralResponse validateCardDuplicate(Card req) {
		GeneralResponse res = new GeneralResponse();
		try {
			if(cardRepo.existsByCardNumber(req.getCardNumber())) {
				res.setStatusCode(BusinessError.DUPLICATE_CARD.getCode());
				res.setStatusMessage(BusinessError.DUPLICATE_CARD.getDescription());
			}
		}catch (Exception e) {
			log.error(e.toString());
		}
		return res;
	}
}
