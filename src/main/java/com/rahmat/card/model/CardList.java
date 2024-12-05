package com.rahmat.card.model;

import java.util.List;

import lombok.Data;

@Data
public class CardList {
	private List<Card> card;
	private int pageNo;
	private int totalData;
}
