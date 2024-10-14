package org.jboss.eap.quickstarts.kitchensink.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Member {
  @Id private UUID id;
  private String name;
  private String email;
  private String phoneNumber;
}
