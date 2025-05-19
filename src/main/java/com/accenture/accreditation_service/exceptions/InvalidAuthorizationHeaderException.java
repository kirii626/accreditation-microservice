package com.accenture.accreditation_service.exceptions;

public class InvalidAuthorizationHeaderException extends UnauthorizedException {
  public InvalidAuthorizationHeaderException() {
    super("Missing or invalid Authorization header");
  }
}
