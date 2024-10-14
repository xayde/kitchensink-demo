package org.jboss.eap.quickstarts.kitchensink.controller.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
  private UUID id;

  @NotNull
  @Size(min = 1, max = 25)
  @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
  private String name;

  @NotBlank @Email private String email;

  @NotNull
  @Size(min = 10, max = 12)
  @Digits(fraction = 0, integer = 12)
  private String phoneNumber;
}
