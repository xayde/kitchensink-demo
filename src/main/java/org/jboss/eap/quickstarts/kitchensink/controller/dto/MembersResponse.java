package org.jboss.eap.quickstarts.kitchensink.controller.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MembersResponse {
  private final List<MemberDTO> members;
}
