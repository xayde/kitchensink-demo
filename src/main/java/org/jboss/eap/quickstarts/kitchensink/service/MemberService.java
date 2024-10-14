package org.jboss.eap.quickstarts.kitchensink.service;
import java.util.List;
import java.util.UUID;

import org.jboss.eap.quickstarts.kitchensink.service.business.MemberBO;

public interface MemberService {
    MemberBO findByEmail(String email);
    List<MemberBO> findAllByOrderByNameAsc();
    MemberBO findById(UUID id);
    MemberBO create(MemberBO member);

}
