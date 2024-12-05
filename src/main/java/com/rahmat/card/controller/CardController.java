package com.rahmat.card.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rahmat.card.model.Card;
import com.rahmat.card.model.GeneralResponse;
import com.rahmat.card.services.CardServices;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
	@Autowired
	private CardServices cardServices;
	
	@RequestMapping(value = "/saveCard", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class),
			@ApiResponse(code = 402, message = "SQL Error", response = GeneralResponse.class),
			@ApiResponse(code = 1, message = "Card Duplicate", response = GeneralResponse.class),
			@ApiResponse(code = 2, message = "Min Card Length = 1 & Max. Card Length = 12", response = GeneralResponse.class),
			@ApiResponse(code = 3, message = "Max. Status Length = 1", response = GeneralResponse.class)
})
	public GeneralResponse saveCard(@Valid @RequestBody Card req) throws Exception {
		return cardServices.saveCard(req);
	}
	
	@GetMapping(value="/cardList", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class),
			@ApiResponse(code = 402, message = "SQL Error", response = GeneralResponse.class),
			@ApiResponse(code = 1, message = "Card Duplicate", response = GeneralResponse.class)
	})
	public GeneralResponse getCardList(@RequestParam("sortBy") String sortBy,@RequestParam("from") int from, @RequestParam("to") int to) throws Exception {
		return cardServices.getCardList(sortBy,from,to);
	}	
	
	@GetMapping(value="/getCard", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class),
			@ApiResponse(code = 402, message = "SQL Error", response = GeneralResponse.class),
			@ApiResponse(code = 4, message = "Card Not Found", response = GeneralResponse.class)
	})
	public GeneralResponse getCard(@RequestParam("cardNo") String cardno) throws Exception {
		return cardServices.getCard(cardno);
	}	
	
	@DeleteMapping(value="/deleteCard", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class),
			@ApiResponse(code = 402, message = "SQL Error", response = GeneralResponse.class),
			@ApiResponse(code = 4, message = "Card Not Found", response = GeneralResponse.class)
	})
	public GeneralResponse deletCard(@RequestParam("cardNo") String cardNo) throws Exception {
		return cardServices.deleteCard(cardNo);
	}
	
	@PutMapping(value = "/updateCard", consumes = "application/json", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class),
			@ApiResponse(code = 402, message = "SQL Error", response = GeneralResponse.class),
			@ApiResponse(code = 4, message = "Card Not Found", response = GeneralResponse.class),
			@ApiResponse(code = 5, message = "Card Status is Not Updated", response = GeneralResponse.class)
	})
	public GeneralResponse updateCard(@Valid @RequestBody Card req) throws Exception {
		return cardServices.updateCard(req);
	}

}
