package com.web.service;

import com.web.bean.Response.CommonResponse;

import com.web.dto.RequestFilterSearchDto;
import java.io.IOException;

public interface NewsItemService {

    CommonResponse getAllNewsItem(int offset) throws IOException;

    CommonResponse getAllNewsByUser(Long userID) throws IOException;

    CommonResponse esSearchNewsApi (RequestFilterSearchDto request) throws IOException;
}
