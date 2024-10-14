package org.jboss.eap.quickstarts.kitchensink;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class QuickstartKitchensinkApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuickstartKitchensinkApplication.class, args);
  }
}
