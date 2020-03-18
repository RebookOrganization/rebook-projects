package com.web.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {

  private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

  public static String getDefaultEmail(String userName) {
    return userName.toLowerCase() + "@gmail.com";
  }

  public static String removeTabAndNewLine(String str) {
    if (str != null) {
      return str.trim().replaceAll("\r", "").replaceAll("\n", " ").replaceAll("\t", " ");
    } else {
      return "";
    }
  }

  public static String truncateToLength(String str, int maxLength) {
    if (str == null) {
      return "";
    }
    if (str.length() <= maxLength) {
      return str;
    }
    return str.substring(0, maxLength);
  }

  public static boolean isEmpty(String pStr) {
    if (pStr == null || pStr.length() == 0) {
      return true;
    }
    return false;
  }

  public static String decodeUTF8(String input) {
    try {
      return java.net.URLDecoder.decode(input, "UTF-8");
    } catch (UnsupportedEncodingException ex) {
      logger.error("decodeUTF8 exception " + ex.getMessage(), ex);
      logger.error("decodeUTF8 input " + input);
      return input;
    }
  }

  public static String formatcomma(long value) {
    DecimalFormat myFormatter = new DecimalFormat("#,###");
    String output = myFormatter.format(value);
    return output;
  }

  public static String formatcomma(double value) {
    DecimalFormat myFormatter = new DecimalFormat("#,###");
    String output = myFormatter.format(value);
    return output;
  }

  public static String formatcomma(String value) {
    String output = value;
    if (value != null && !value.trim().equals("")) {
      try {
        long lvalue = new Long(value);
        DecimalFormat myFormatter = new DecimalFormat("#,###");
        output = myFormatter.format(lvalue);
      } catch (Exception e) {

      }
    }
    return output;
  }

  public static String formatdot(long value) {
    try {
      DecimalFormat myFormatter = new DecimalFormat("#,###");
      String output = myFormatter.format(value);
      if (output != null) {
        output = output.replace(",", ".");
      }
      return output;
    } catch (Exception ex) {
      return String.valueOf(value);
    }
  }

  // @formatter:off
  static final byte[] HEX_CHAR_TABLE = {(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
      (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
      (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'};
  // @formatter:on

  /**
   * Convert a byte array to a hexadecimal string
   *
   * @param raw A raw byte array
   * @return Hexadecimal string
   */
  public static String byteArrayToHexString(byte[] raw) {
    byte[] hex = new byte[2 * raw.length];
    int index = 0;

    for (byte b : raw) {
      int v = b & 0xFF;
      hex[index++] = HEX_CHAR_TABLE[v >>> 4];
      hex[index++] = HEX_CHAR_TABLE[v & 0xF];
    }
    return new String(hex);
  }

  /**
   * Convert a hexadecimal string to a byte array
   *
   * @param hex raw A hexadecimal string
   * @return The byte array
   */
  public static byte[] hexStringToByteArray(String hex) {
    String hexstandard = hex.toLowerCase(Locale.ENGLISH);
    int sz = hexstandard.length() / 2;
    byte[] bytesResult = new byte[sz];

    int idx = 0;
    for (int i = 0; i < sz; i++) {
      bytesResult[i] = (byte) (hexstandard.charAt(idx));
      ++idx;
      byte tmp = (byte) (hexstandard.charAt(idx));
      ++idx;

      if (bytesResult[i] > HEX_CHAR_TABLE[9]) {
        bytesResult[i] -= ((byte) ('a') - 10);
      } else {
        bytesResult[i] -= (byte) ('0');
      }
      if (tmp > HEX_CHAR_TABLE[9]) {
        tmp -= ((byte) ('a') - 10);
      } else {
        tmp -= (byte) ('0');
      }

      bytesResult[i] = (byte) (bytesResult[i] * 16 + tmp);
    }
    return bytesResult;
  }

  public static String replaceDateToString(String strs, String ymd) {
    if (strs == null || strs.length() == 0) {
      return strs;
    }
    Pattern p = Pattern.compile("\\{\\{[^\\}]*\\}\\}");
    Matcher m = p.matcher(strs);

    // Tim tat ca cac chuoi thay the (theo regex) -> set
    Set<String> replaceStrs = new HashSet<>();
    while (m.find()) {
      replaceStrs.add(m.group());
    }

    String strResult = strs; // ket qua cuoi cung
    Iterator<String> itReplace = replaceStrs.iterator();
    while (itReplace.hasNext()) // chay tren tung chuoi
    {
      // chuoi thay the
      String strTmp = itReplace.next();
      // System.out.println("strTmp: " + strTmp);

      // bo '{{' va '}}'
      String ymdTmp = strTmp.substring(2, strTmp.length() - 2);
      // System.out.println("ymdTmp: " + ymdTmp);

      int index = ymdTmp.indexOf("[[");
      // System.out.println("index: " + index);
      String newFormat = ymdTmp;
      int delDay = 0;
      if (index > 0) {
        newFormat = ymdTmp.substring(0, index);

        delDay = Integer.parseInt(ymdTmp.substring(index + 2, ymdTmp.length() - 2));
      }
      // System.out.println("newFormat: " + newFormat);
      // System.out.println("delDay: " + delDay);

      Date newDate =
          DateTimeUtils.add(DateTimeUtils.convertDate(ymd, "yyyyMMdd"), 0, 0, delDay, 0, 0, 0);
      // System.out.println("newDate:" + newDate);

      String resultReplace = DateTimeUtils.convertString(newDate, newFormat);
      // System.out.println("resultReplace: " + resultReplace);

      // Thay the chuoi ket qua vao chuoi tong
      // System.out.println("strTmp:" + strTmp);
      // System.out.println("resultReplace:" + resultReplace);
      strResult = strResult.replace(strTmp, resultReplace);
    }

    return strResult;
  }


  public static final String SHA1 = "SHA1";
  public static final String SHA256 = "SHA-256";
  public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  public static String decodeUTF8(byte[] bytes) {
    return new String(bytes, UTF8_CHARSET);
  }

  public static byte[] encodeUTF8(String string) {
    return string.getBytes(UTF8_CHARSET);
  }

  public static String removeAllNonNumericCharacters(String input) {
    return input.replaceAll("[^0-9]", "");
  }

  public static String noiseVoucher(String voucher) {
    if (!org.apache.commons.lang3.StringUtils.isEmpty(voucher)) {
      String noiseVoucher = voucher.length() >= 4 ? voucher.substring(0, 4) : voucher;
      return noiseVoucher + "xxx";
    }
    return "";
  }

  public static String removeAccent(String s) {

    String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("");
  }

  public static boolean like(String str, String expr) {
    expr = expr.toLowerCase(); // ignoring locale for now
    expr = expr.replace(".", "\\."); // "\\" is escaped to "\" (thanks, Alan M)
    // ... escape any other potentially problematic characters here
    expr = expr.replace("?", ".");
    expr = expr.replace("%", ".*");
    str = str.toLowerCase();
    return str.matches(expr);
  }
}
