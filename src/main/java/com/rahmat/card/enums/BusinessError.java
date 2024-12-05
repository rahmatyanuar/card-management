package com.rahmat.card.enums;

public enum BusinessError {
  SUCCESS("200","Success"),
  DUPLICATE_CARD("1", "This card number already exists."),
  CARD_LENGTH("2", "Min Card Length = 1 & Max. Card Length = 12"),
  STATUS_LENGTH("3","Max. Status Length = 1"),
  CARD_NOT_FOUND("4","Card Not Found"),
  CARD_STATUS_UPDATE_FAILED("5","Card Status is Not Updated");

  private final String code;
  private final String description;

  private BusinessError(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
     return description;
  }

  public String getCode() {
     return code;
  }

  @Override
  public String toString() {
    return code + ": " + description;
  }
}
