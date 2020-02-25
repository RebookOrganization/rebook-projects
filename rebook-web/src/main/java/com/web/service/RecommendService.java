package com.web.service;

import com.web.bean.Response.NewsResponseDTO;
import java.util.List;

public interface RecommendService {

  List<NewsResponseDTO> listRecommend(String prefix, String id, String include);

}
