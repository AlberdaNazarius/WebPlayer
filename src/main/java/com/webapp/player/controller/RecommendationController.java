package com.webapp.player.controller;

import com.webapp.player.dto.OllamaPrompt;
import com.webapp.player.service.imp.OllamaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
  private final OllamaService ollamaService;

  @PostMapping
  public List<String> getRecommendations(@RequestBody OllamaPrompt preferences) {
    log.info("Received get recommendations request");
    return ollamaService.getRecommendations(preferences);
  }
}