package org.jboss.eap.quickstarts.kitchensink.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.exception.MemberAlreadyExistsException;
import org.jboss.eap.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.eap.quickstarts.kitchensink.mapper.MemberMapper;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.repository.MemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.business.MemberBO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;

class MemberServiceTests {

  private final MemberRepository memberRepository = mock(MemberRepository.class);
  @Spy private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
  private final MemberService memberService = new MemberServiceImpl(memberRepository, memberMapper);

  @Test
  void testCreateMember() {
    MemberBO member = new MemberBO();
    member.setName("John Doe");
    member.setEmail("john.doe@example.com");
    member.setPhoneNumber("1234567890");

    MemberBO createdMember = memberService.create(member);

    verify(memberRepository).save(any(Member.class));
    assertEquals("John Doe", createdMember.getName());
    assertEquals("john.doe@example.com", createdMember.getEmail());
    assertEquals("1234567890", createdMember.getPhoneNumber());
  }

  @Test
  void testCreateMemberEmailExists() {
    MemberBO member = new MemberBO();
    member.setEmail("john.doe@example.com");

    when(memberRepository.existsByEmail(member.getEmail())).thenReturn(true);
    assertThrowsExactly(MemberAlreadyExistsException.class, () -> memberService.create(member));
  }

  @Test
  void testFindMemberById() {
    Member member = new Member();
    member.setId(UUID.randomUUID());
    member.setName("John Doe");
    member.setEmail("john.doe@example.com");
    member.setPhoneNumber("1234567890");

    when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

    MemberBO foundMember = memberService.findById(member.getId());

    assertEquals(member.getId(), foundMember.getId());
    assertEquals("John Doe", foundMember.getName());
    assertEquals("john.doe@example.com", foundMember.getEmail());
    assertEquals("1234567890", foundMember.getPhoneNumber());
  }

  @Test
  void testFindMemberByIdMemberNotExists() {
    MemberBO member = new MemberBO();
    member.setId(UUID.randomUUID());

    when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());
    assertThrowsExactly(
        MemberNotFoundException.class, () -> memberService.findById(member.getId()));
  }

  @Test
  void testFindMemberByEmail() {
    Member member = new Member();
    member.setId(UUID.randomUUID());
    member.setName("John Doe");
    member.setEmail("john.doe@example.com");
    member.setPhoneNumber("1234567890");

    when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

    MemberBO foundMember = memberService.findByEmail(member.getEmail());

    assertEquals(member.getId(), foundMember.getId());
    assertEquals("John Doe", foundMember.getName());
    assertEquals("john.doe@example.com", foundMember.getEmail());
    assertEquals("1234567890", foundMember.getPhoneNumber());
  }

  @Test
  void testFindMemberByEmailMemberNotExists() {
    MemberBO member = new MemberBO();
    member.setEmail("john.doe@example.com");

    when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());
    assertThrowsExactly(
        MemberNotFoundException.class, () -> memberService.findByEmail(member.getEmail()));
  }
}
