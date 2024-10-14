package org.jboss.eap.quickstarts.kitchensink.exception;

public class MemberNotFoundException extends RuntimeException {
  public MemberNotFoundException() {
    super("Member not found");
  }
}
