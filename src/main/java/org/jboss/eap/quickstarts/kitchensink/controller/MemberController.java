package org.jboss.eap.quickstarts.kitchensink.controller;

import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MemberDTO;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MembersResponse;
import org.jboss.eap.quickstarts.kitchensink.mapper.MemberMapper;
import org.jboss.eap.quickstarts.kitchensink.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
  private final MemberService memberService;
  private final MemberMapper memberMapper;
  private final MemberValidator memberValidator;

  @Autowired
  public MemberController(
      MemberService memberService, MemberMapper memberMapper, MemberValidator memberValidator) {
    this.memberService = memberService;
    this.memberMapper = memberMapper;
    this.memberValidator = memberValidator;
  }

  @PostMapping
  public MemberDTO create(@RequestBody MemberDTO memberDTO) {
    memberValidator.validate(memberDTO);
    var createdMember = memberService.create(memberMapper.toBo(memberDTO));
    return memberMapper.toDto(createdMember);
  }

  @GetMapping("/{id}")
  public MemberDTO findById(@PathVariable UUID id) {
    return memberMapper.toDto(memberService.findById(id));
  }

  @GetMapping
  public MembersResponse findAll() {
    var members =
        memberService.findAllByOrderByNameAsc().stream().map(memberMapper::toDto).toList();
    return new MembersResponse(members);
  }
}
