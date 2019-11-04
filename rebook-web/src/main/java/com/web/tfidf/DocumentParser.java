package com.web.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentParser {

  private static Logger logger = LoggerFactory.getLogger(DocumentParser.class);

  private List<String[]> termsDocsArray = new ArrayList<>(); //List doc<tất cả các từ> trong 1 document (1 file), một doc = String[].
  private List<String> allTerms = new ArrayList<>(); //tất cả các <từ> trong nhiều document (nhiều file).
  private List<double[]> tfidfDocsVector = new ArrayList<>();

  /**
   * Method to read files and store in array.
   */
  public void parseFiles(String filePath) throws FileNotFoundException, IOException {
    File[] allfiles = new File(filePath).listFiles();
    BufferedReader in = null;
    assert allfiles != null;
    for (File f : allfiles) {
      if (f.getName().endsWith(".txt")) {
        in = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = in.readLine()) != null) {
          sb.append(s);
        }
        String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
        for (String term : tokenizedTerms) {
          if (!allTerms.contains(term)) {  //avoid duplicate entry
            allTerms.add(term);
          }
        }
        termsDocsArray.add(tokenizedTerms);
      }
    }

  }

  /**
   * Method to read two text.
   */
  public void parseTwoText(String text1, String text2) {
    String[] tokenizedTerms1 = text1.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
    String[] tokenizedTerms2 = text2.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");

    for (String term1 : tokenizedTerms1) {
      if (!allTerms.contains(term1)) {  //avoid duplicate entry
        allTerms.add(term1);
      }
    }

    for (String term2 : tokenizedTerms2) {
      if (!allTerms.contains(term2)) {  //avoid duplicate entry
        allTerms.add(term2);
      }
    }

//    logger.info("DocumentParser parseTwoText allTerms: {}", allTerms);

    termsDocsArray.add(tokenizedTerms1);
    termsDocsArray.add(tokenizedTerms2);

//    logger.info("DocumentParser parseTwoText termsDocsArray: {}", termsDocsArray);
  }

  /**
   * Method to create termVector according to its tfidf score.
   */
  public void tfIdfCalculator() {
    double tf; //term frequency (tần số của một từ trong một đoạn văn bản).
    double idf; //inverse document frequency (độ quan trọng của một từ).
    double tfidf; //đơn vị (từ) : tần số * độ quan trọng.
    for (String[] docTermsArray : termsDocsArray) {
      double[] tfidfvectors = new double[allTerms.size()];
      int count = 0;
      for (String terms : allTerms) {
        tf = new TfIdf().tfCalculator(docTermsArray, terms);
        idf = new TfIdf().idfCalculator(termsDocsArray, terms);
        tfidf = tf * idf;
        tfidfvectors[count] = tfidf;
        count++;
      }
      tfidfDocsVector.add(tfidfvectors);  //storing document vectors;
    }
//    logger.info("DocumentParser tfIdfCalculator tfidfDocsVector: {}", tfidfDocsVector);
  }

  /**
   * Method to calculate cosine similarity between all the documents.
   */
  public double getCosineSimilarity() {
    double result = 0.0;
    for (int i = 0; i < tfidfDocsVector.size(); i++) {
      for (double[] doubles : tfidfDocsVector) {
        result = new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(i),
            doubles);
      }
    }
    return result;
  }

}
