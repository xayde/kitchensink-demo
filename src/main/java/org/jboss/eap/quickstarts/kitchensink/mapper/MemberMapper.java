package org.jboss.eap.quickstarts.kitchensink.mapper;

import org.jboss.eap.quickstarts.kitchensink.service.business.MemberBO;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MemberDTO;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  MemberDTO toDto(MemberBO memberBO);

  MemberBO toBo(MemberDTO memberDTO);

  MemberBO toBo(Member member);

  Member toModel(MemberBO memberBO);
}
