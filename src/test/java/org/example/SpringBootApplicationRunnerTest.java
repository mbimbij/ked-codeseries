package org.example;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = SpringBootApplicationRunner.class, webEnvironment = RANDOM_PORT)
class SpringBootApplicationRunnerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void name() {
    // WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/", String.class);

    // THEN
    SoftAssertions.assertSoftly(softAssertions -> {
      softAssertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
      softAssertions.assertThat(responseEntity.getBody()).startsWith(ApplicationRestController.RESPONSE_PREFIX);
    });
  }
}