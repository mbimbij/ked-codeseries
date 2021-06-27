package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ApplicationRestController {
  public static final String RESPONSE_PREFIX = "Hello World !";
  public static final String uuid = UUID.randomUUID().toString();

  @GetMapping("/")
  public String helloWorld(){
    return String.join(" :: ", RESPONSE_PREFIX, uuid);
  }
}
