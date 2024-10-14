package org.jboss.eap.quickstarts.kitchensink.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.repository.MemberRepository;

@ChangeLog(order = "001")
public class DatabaseChangelog {

  @ChangeSet(order = "001", id = "insertDefaultMember", author = "Madalina Lupu")
  public void insertDefaultMember(MemberRepository memberRepository) {
    Member member = new Member();
    member.setId(UUID.randomUUID());
    member.setName("John Smith");
    member.setEmail("john.smith@mailinator.com");
    member.setPhoneNumber("2125551212");
    memberRepository.save(member);
  }
}
