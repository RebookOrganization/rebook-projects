package com.crawler.tfidf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TfIdfMain {
  private static Logger logger = LoggerFactory.getLogger(TfIdfMain.class);

  public double compareText(String text1, String text2) {
    DocumentParser doc = new DocumentParser();
    doc.parseTwoText(text1, text2);
    doc.tfIdfCalculator();
    return doc.getCosineSimilarity();
  }

}
