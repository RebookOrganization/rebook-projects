package com.web.bean.Request;

import java.io.Serializable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetLimitRequest implements Pageable, Serializable {
  private static final long serialVersionUID = -4541509938956089562L;

  private int limit;
  private int offset;
  private Sort sort;

  public OffsetLimitRequest(int limit, int offset, Sort sort) {
    this.limit = limit;
    this.offset = offset;
    this.sort = sort;
  }

  @Override
  public int getPageNumber() { return 0; }

  @Override
  public int getPageSize() { return 0; }

  @Override
  public long getOffset() { return 0; }

  @Override
  public Sort getSort() { return null; }

  @Override
  public Pageable next() { return null; }

  @Override
  public Pageable previousOrFirst() { return null; }

  @Override
  public Pageable first() { return null; }

  @Override
  public boolean hasPrevious() { return false; }

  public int getLimit() { return limit; }

  public void setLimit(int limit) { this.limit = limit; }

  public void setOffset(int offset) { this.offset = offset; }

  public void setSort(Sort sort) { this.sort = sort; }
}
