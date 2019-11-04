package com.crawler.service;

import com.crawler.bean.Response.CommonResponse;
import java.io.IOException;

public interface CrawlerService {

  CommonResponse crawlerBatDongSan() throws IOException;

  CommonResponse crawlerDiaOcOnline() throws IOException;

}
