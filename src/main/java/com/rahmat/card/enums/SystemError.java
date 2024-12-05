package com.rahmat.card.enums;

public enum SystemError {
  ERROR("402","SQL Error");

  private final String code;
  private final String description;

  private SystemError(String code, String description) {
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
