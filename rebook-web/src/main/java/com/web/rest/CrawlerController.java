package com.web.rest;

import com.web.bean.Response.CommonResponse;
import com.web.service.CrawlerService;
import com.web.service.impl.NewsItemServiceImpl;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class CrawlerController {

  @Autowired
  private CrawlerService crawlerService;

  @GetMapping(value = "/crawl-batdongsan")
  public CommonResponse crawlerBatDongSan() throws IOException {
    return crawlerService.crawlerBatDongSan();
  }

  @GetMapping(value = "/crawl-diaoconline")
  public CommonResponse crawlerDiaOcOnline() throws IOException {
    return crawlerService.crawlerDiaOcOnline();
  }

}
