package com.web.service;

import com.web.bean.Response.CommonResponse;
import java.io.IOException;

public interface CrawlerService {

  CommonResponse crawlerBatDongSan() throws IOException;

  CommonResponse crawlerDiaOcOnline() throws IOException;

}
