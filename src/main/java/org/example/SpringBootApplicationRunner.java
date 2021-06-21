package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class SpringBootApplicationRunner {

  public static final String uuid = UUID.randomUUID().toString();

  public static void main(String[] args) {
    SpringApplication.run(SpringBootApplicationRunner.class, args);
  }

  @GetMapping("/")
  public String helloWorld(){
    return "Hello World 2 ! :: "+uuid;
  }
}
