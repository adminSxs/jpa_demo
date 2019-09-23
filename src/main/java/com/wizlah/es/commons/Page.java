package com.wizlah.es.commons;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Page {
  private int pageIndex; // current page index
  private int pageSize; // page size
  private long totalItem; // total page?

  public Page(int pageIndex, int pageSize) {
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  public int getOffset() {
    return (this.pageIndex - 1) * this.pageSize;
  }
    }
