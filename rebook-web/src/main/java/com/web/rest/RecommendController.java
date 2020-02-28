package com.web.rest;

import com.web.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

  @Autowired
  private RecommendService recommendService;

  @GetMapping("/list-news")
  public ResponseEntity<?> getListRecommend(@RequestParam String userId) {
    return new ResponseEntity<>(recommendService.listRecommend(userId), HttpStatus.OK);
  }

}
