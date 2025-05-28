package com.webapp.player.service.imp;

import com.webapp.player.dto.OllamaPrompt;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class OllamaService {

  private final WebClient webClient = WebClient.create("http://localhost:11434");

  public List<String> getRecommendations(OllamaPrompt preferences) {
    String fullPrompt = buildPrompt(preferences);

    Map<String, Object> request = new HashMap<>();
    request.put("model", "mistral");
    request.put("prompt", fullPrompt);
    request.put("stream", false);

    OllamaResponse response = webClient.post()
            .uri("api/generate")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(OllamaResponse.class)
            .block();

    assert response != null;
    return parseRecommendations(response.getResponse());
  }

  private String buildPrompt(OllamaPrompt preferences) {
    return String.format(
            "Recommend %s songs about: %s. Provide only song names separated by commas.",
            preferences.getGenre(),
            preferences.getPrompt()
    );
  }

  private List<String> parseRecommendations(String response) {
    if (response == null) {
      return new ArrayList<>();
    }
    return Arrays.asList(response.split("\n"));
  }

  @Data
  private static class OllamaResponse {
    private String response;
    private boolean done;
  }
}