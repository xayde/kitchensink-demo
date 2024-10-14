package org.jboss.eap.quickstarts.kitchensink.service.business;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberBO {
  private UUID id;
  private String name;
  private String email;
  private String phoneNumber;
}
