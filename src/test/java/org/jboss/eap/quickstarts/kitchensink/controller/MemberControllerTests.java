package org.jboss.eap.quickstarts.kitchensink.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MemberDTO;
import org.jboss.eap.quickstarts.kitchensink.controller.dto.MembersResponse;
import org.jboss.eap.quickstarts.kitchensink.model.Member;
import org.jboss.eap.quickstarts.kitchensink.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
class MemberControllerTests {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private MemberRepository memberRepository;

  @Test
  void testCreateMember() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("john.doe@example.com");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    when(memberRepository.existsByEmail(memberDTO.getEmail())).thenReturn(false);
    when(memberRepository.save(any(Member.class)))
        .thenAnswer(
            invocation -> {
              Member member = invocation.getArgument(0);
              member.setId(memberDTO.getId());
              return member;
            });

    mockMvc
        .perform(
            post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDTO)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(memberDTO)));
  }

  @Test
  void testCreateMemberEmailExists() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("john.doe@example.com");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    when(memberRepository.existsByEmail(memberDTO.getEmail())).thenReturn(true);

    mockMvc
        .perform(
            post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDTO)))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testCreateMemberValidationFails() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    mockMvc
        .perform(
            post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDTO)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMemberById() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("john.doe@example.com");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    when(memberRepository.findById(memberDTO.getId()))
        .thenReturn(Optional.of(createMember(memberDTO)));

    mockMvc
        .perform(get("/members/{id}", memberDTO.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(memberDTO)));
  }

  @Test
  void testGetMemberByIdNotFound() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("john.doe@example.com");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    when(memberRepository.findById(memberDTO.getId())).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/members/{id}", memberDTO.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMembers() throws Exception {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setName("John Doe");
    memberDTO.setEmail("john.doe@example.com");
    memberDTO.setPhoneNumber("1234567890");
    memberDTO.setId(UUID.randomUUID());

    when(memberRepository.findAllByOrderByNameAsc()).thenReturn(List.of(createMember(memberDTO)));

    mockMvc
        .perform(get("/members").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            content()
                .json(objectMapper.writeValueAsString(new MembersResponse(List.of(memberDTO)))));
  }

  private Member createMember(MemberDTO memberDTO) {
    Member member = new Member();
    member.setName(memberDTO.getName());
    member.setEmail(memberDTO.getEmail());
    member.setPhoneNumber(memberDTO.getPhoneNumber());
    member.setId(memberDTO.getId());
    return member;
  }
}
