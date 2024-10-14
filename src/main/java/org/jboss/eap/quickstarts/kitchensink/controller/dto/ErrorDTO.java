package org.jboss.eap.quickstarts.kitchensink.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDTO {
  private final String message;
}
