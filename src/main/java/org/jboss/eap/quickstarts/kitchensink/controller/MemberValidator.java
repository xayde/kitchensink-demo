package org.jboss.eap.quickstarts.kitchensink.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {

  private final Validator validator;

  @Autowired
  public MemberValidator(Validator validator) {
    this.validator = validator;
  }

  public void validate(MemberDTO memberDTO) {
    var violations = validator.validate(memberDTO);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
