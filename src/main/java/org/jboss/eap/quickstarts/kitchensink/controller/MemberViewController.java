package org.jboss.eap.quickstarts.kitchensink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class MemberViewController {

  @GetMapping("/home")
  public String getHome() {
    return "home.html";
  }
}
