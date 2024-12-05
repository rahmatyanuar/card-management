package com.rahmat.card.services;

import java.sql.SQLException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rahmat.card.enums.BusinessError;
import com.rahmat.card.enums.SystemError;
import com.rahmat.card.model.Card;
import com.rahmat.card.model.CardList;
import com.rahmat.card.model.GeneralResponse;
import com.rahmat.card.repo.CardRepository;
import com.rahmat.card.utility.CardValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CardServices {
	@Autowired
	private CardRepository cardRepo;
	
	@Autowired
	private CardValidator validator;

	public GeneralResponse saveCard(Card req) throws SQLException {
		GeneralResponse res = new GeneralResponse();
		Card card = new Card();
		card.setCardNumber(req.getCardNumber());
		String resCode = null;
		String resDesc = null;
		
		res = validator.validateCardReq(req);

		if((res.getStatusCode()==null)) {
			res = validator.validateCardDuplicate(req);
			if((res.getStatusCode()==null)) {
				card.setCreatedDT(new Date());
				card.setStatus(req.getStatus());	
				try {
					cardRepo.save(card);
					resCode = BusinessError.SUCCESS.getCode();
					resDesc = BusinessError.SUCCESS.getDescription();
				} catch (Exception e) {
					log.error(e.toString());
					resCode = SystemError.ERROR.getCode();		
					resDesc = SystemError.ERROR.getDescription();
				}
				res.setStatusCode(resCode);
				res.setStatusMessage(resDesc);
			}
		}
		
		res.setData(card);
		
		return res;
	}

	public GeneralResponse getCardList(String sortBy, int from, int to) {
		GeneralResponse res = new GeneralResponse();
		Page<Card> cardList = null;
		CardList card = new CardList();
		String resCode = null;
		String resDesc = null;
		try {
			Pageable paging = PageRequest.of(from, to, Sort.by(sortBy));
			cardList = cardRepo.findAll(paging);
			card.setCard(cardList.getContent());
			card.setPageNo(cardList.getPageable().getPageNumber());
			card.setTotalData(cardList.getPageable().getPageSize());
			resCode = BusinessError.SUCCESS.getCode();
			resDesc = BusinessError.SUCCESS.getDescription();
		} catch (Exception e) {
			log.error(e.toString());
			resCode = SystemError.ERROR.getCode();		
			resDesc = SystemError.ERROR.getDescription();
		}
		res.setData(card);
		res.setStatusCode(resCode);
		res.setStatusMessage(resDesc);
		return res;
	}

	public GeneralResponse getCard(@Valid String req) {
		GeneralResponse res = new GeneralResponse();
		Card card = new Card();
		String resCode = null;
		String resDesc = null;
		try {
			card = cardRepo.findByCardNumber(req);
			if(card == null) {
				resCode = BusinessError.CARD_NOT_FOUND.getCode();
				resDesc = BusinessError.CARD_NOT_FOUND.getDescription();
			}else {
				resCode = BusinessError.SUCCESS.getCode();
				resDesc = BusinessError.SUCCESS.getDescription();
			}
		} catch (Exception e) {
			log.error(e.toString());
			resCode = SystemError.ERROR.getCode();		
			resDesc = SystemError.ERROR.getDescription();
		}
		res.setData(card);
		res.setStatusCode(resCode);
		res.setStatusMessage(resDesc);
		return res;
	}

	public GeneralResponse deleteCard(String req) {
		GeneralResponse res = new GeneralResponse();
		String resCode = null;
		String resDesc = null;
		try {
			Long deleteResponse = cardRepo.deleteByCardNumber(req);
			if(deleteResponse == 1) {
				resCode = BusinessError.SUCCESS.getCode();
				resDesc = BusinessError.SUCCESS.getDescription();
			} else {
				resCode = BusinessError.CARD_NOT_FOUND.getCode();
				resDesc = BusinessError.CARD_NOT_FOUND.getDescription();
			}
		} catch (Exception e) {
			log.error(e.toString());
			resCode = SystemError.ERROR.getCode();		
			resDesc = SystemError.ERROR.getDescription();
		}
		res.setStatusCode(resCode);
		res.setStatusMessage(resDesc);
		return res;
	}

	public GeneralResponse updateCard(@Valid Card req) {
		GeneralResponse res = new GeneralResponse();
		Card card = new Card();
		String resCode = null;
		String resDesc = null;
		try {
			card = cardRepo.findByCardNumber(req.getCardNumber());
			if(card != null) {
				req.setUpdateDT(new Date());
				card.setStatus(req.getStatus());
				card.setUpdateDT(req.getUpdateDT());
				int updateStatus = cardRepo.updateCardStatus(req.getCardNumber(),req.getStatus(), req.getUpdateDT());
				if(updateStatus == 1) {
					resCode = BusinessError.SUCCESS.getCode();
					resDesc = BusinessError.SUCCESS.getDescription();
				}else {
					resCode = BusinessError.CARD_STATUS_UPDATE_FAILED.getCode();
					resDesc = BusinessError.CARD_STATUS_UPDATE_FAILED.getDescription();
				}
			}else {
				resCode = BusinessError.CARD_NOT_FOUND.getCode();
				resDesc = BusinessError.CARD_NOT_FOUND.getDescription();
			}
			
		} catch (Exception e) {
			log.error(e.toString());
			resCode = SystemError.ERROR.getCode();		
			resDesc = SystemError.ERROR.getDescription();
		}
		res.setData(card);
		res.setStatusCode(resCode);
		res.setStatusMessage(resDesc);
		return res;
	}
	
}
