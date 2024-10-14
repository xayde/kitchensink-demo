package org.jboss.eap.quickstarts.kitchensink.service;

import java.util.List;
import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.exception.MemberAlreadyExistsException;
import org.jboss.eap.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.jboss.eap.quickstarts.kitchensink.mapper.MemberMapper;
import org.jboss.eap.quickstarts.kitchensink.repository.MemberRepository;
import org.jboss.eap.quickstarts.kitchensink.service.business.MemberBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final MemberMapper memberMapper;

  @Autowired
  public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
    this.memberRepository = memberRepository;
    this.memberMapper = memberMapper;
  }

  @Transactional(readOnly = true)
  @Override
  public MemberBO findByEmail(String email) {
    return memberRepository
        .findByEmail(email)
        .map(memberMapper::toBo)
        .orElseThrow(MemberNotFoundException::new);
  }

  @Transactional(readOnly = true)
  @Override
  public List<MemberBO> findAllByOrderByNameAsc() {
    return memberRepository.findAllByOrderByNameAsc().stream().map(memberMapper::toBo).toList();
  }

  @Transactional(readOnly = true)
  @Override
  public MemberBO findById(UUID id) {
    return memberRepository
        .findById(id)
        .map(memberMapper::toBo)
        .orElseThrow(MemberNotFoundException::new);
  }

  @Transactional
  @Override
  public MemberBO create(MemberBO memberBO) {
    var memberExists = memberRepository.existsByEmail(memberBO.getEmail());
    if (memberExists) {
      throw new MemberAlreadyExistsException();
    }
    var member = memberMapper.toModel(memberBO);
    member.setId(UUID.randomUUID());
    memberRepository.save(member);
    return memberMapper.toBo(member);
  }
}
