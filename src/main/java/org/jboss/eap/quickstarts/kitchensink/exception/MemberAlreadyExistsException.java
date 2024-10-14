package org.jboss.eap.quickstarts.kitchensink.exception;

public class MemberAlreadyExistsException extends RuntimeException {
  public MemberAlreadyExistsException() {
    super("Member email taken");
  }
}
