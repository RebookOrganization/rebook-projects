package com.crawler.utils;

public class ConvertData {

  public static double convertPriceNum(String priceIn, float area) {
    float output = 0;
    if (priceIn.contains("triệu") && priceIn.contains("tỷ")) {
      int indexOfBili = priceIn.indexOf("tỷ");
      int indexOfMili = priceIn.indexOf("triệu");
      output = Integer.parseInt(priceIn.substring(0, indexOfBili).trim())*1000
          + Integer.parseInt(priceIn.substring(indexOfBili + "tỷ".length(), indexOfMili).trim());
    }
    else {
      if (priceIn.contains("triệu/m")) {
        if (priceIn.contains("triệu/m²")) {
          output = Float.parseFloat(priceIn.replaceAll(" triệu/m²", "")) * area;
        }
        else {
          output = Float.parseFloat(priceIn.replaceAll(" triệu/m2", "")) * area;
        }
      }
      else {
        if (priceIn.contains("ngàn/m")) {
          int indexOfMili = priceIn.indexOf("triệu");
          int indexOfThousand = 0;
          if (priceIn.contains("ngàn/m²")) {
            indexOfThousand = priceIn.indexOf("ngàn/m²");
          }
          else {
            indexOfThousand = priceIn.indexOf("ngàn/m2");
          }
          output = Float.parseFloat(priceIn.substring(0, indexOfMili).trim()) * 1000
              + Float.parseFloat(priceIn.substring(indexOfMili + "triệu".length(), indexOfThousand).trim()) * 1000
              * area;
        }
        else {
          if (priceIn.contains("triệu") && !priceIn.contains("tỷ")) {
            int indexOfMili = priceIn.indexOf("triệu");
            String thousand = priceIn.replaceAll("[^0-9^.]", "")
                .substring(indexOfMili + "triệu".length());
            if (thousand.length() > 0) {
              output = Float.parseFloat(priceIn.substring(0, indexOfMili).trim()) * 1000 + Float.parseFloat(thousand);
            }
            else {
              output = Float.parseFloat(priceIn.substring(0, indexOfMili).trim()) * 1000;
            }
          }
          else {
            if (!priceIn.contains("triệu") && priceIn.contains("tỷ")) {
              output = Float.parseFloat(priceIn.replaceAll(" tỷ", "").trim()) * 1000000;
            }
            else {
              return 0;
            }
          }
        }
      }
    }
    return output;
  }
}
