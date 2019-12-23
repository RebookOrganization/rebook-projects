package com.crawler.utils;

public class ConvertData {

  public static double convertPriceNum(String priceIn, float area) {
    float output = 0;
    if (priceIn.contains("triệu") && priceIn.contains("tỷ")) {
      int indexOfBili = priceIn.indexOf("tỷ");
      int indexOfMili = priceIn.indexOf("triệu");
      output = Integer.parseInt(priceIn.substring(0, indexOfBili).trim())*1000000
          + Integer.parseInt(priceIn.substring(indexOfBili + "tỷ".length(), indexOfMili).trim())*1000;
    }
    else {
      if (priceIn.contains("triệu/m")) {
        if (priceIn.contains("triệu/m²")) {
          if (area != 0.0)
            output = Float.parseFloat(priceIn.replaceAll(" triệu/m²", "")) * area*1000;
          else
            output = Float.parseFloat(priceIn.replaceAll(" triệu/m²", ""))*1000;
        }
        else {
          if (area != 0.0)
            output = Float.parseFloat(priceIn.replaceAll(" triệu/m2", "")) * area*1000;
          else
            output = Float.parseFloat(priceIn.replaceAll(" triệu/m2", ""))*1000;
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

          if (area != 0) {
            output =( (Float.parseFloat(priceIn.substring(0, indexOfMili).trim())*1000 +
                (Float.parseFloat(priceIn.substring(indexOfMili + "triệu".length(), indexOfThousand).trim()) )))
                * area;
          }
          else {
            output = (Float.parseFloat(priceIn.substring(0, indexOfMili).trim())*1000
                + (Float.parseFloat(priceIn.substring(indexOfMili + "triệu".length(), indexOfThousand).trim())));
          }
        }
        else {
          if (priceIn.contains("triệu") && !priceIn.contains("tỷ")) {
            int indexOfMili = priceIn.indexOf("triệu");
            output = Float.parseFloat(priceIn.substring(0, indexOfMili).trim())*1000;
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
    return output/1000;
  }
}
