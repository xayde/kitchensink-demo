package org.jboss.eap.quickstarts.kitchensink.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, UUID> {
  Optional<Member> findByEmail(String email);

  boolean existsByEmail(String email);

  List<Member> findAllByOrderByNameAsc();
}
