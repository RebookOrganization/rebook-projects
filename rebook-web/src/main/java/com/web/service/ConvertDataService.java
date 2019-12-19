package com.web.service;

import com.web.model.PropertyAddress;
import com.web.repository.PropertyAdressRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertDataService {

  private static final Logger logger = LoggerFactory.getLogger(ConvertDataService.class);

  @Autowired
  private PropertyAdressRepository propertyAdressRepository;

  public double convertPriceNum(String priceIn, float area) {
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

  public String updateAddressData()  {
    final String HCM = "Hồ Chí Minh";
    final String HN = "Hà Nội";
    final String VT = "Vũng Tàu";
    final String BD = "Bình Dương";
    final String LA = "Long An";
    final String VL = "Vĩnh Long";
    final String DN = "Đồng Nai";
    final String DaNang = "Đà Nẵng";
    final String KH = "Khánh Hòa";
    try {
      List<PropertyAddress> listAll = propertyAdressRepository.findAll();
      for (PropertyAddress item : listAll) {
        if (item.getSummary().contains(" Tân Phú")) {
          item.setDistrict("Tân Phú");
          item.setProvince(HCM);
          propertyAdressRepository.save(item);
        }
        else if (item.getSummary().contains(" Hà Nội")) {
          item.setDistrict(HN);
          item.setProvince(HN);
          propertyAdressRepository.save(item);
        }
//        if (item.getSummary().contains(" Quận 1")) {
//          item.setDistrict("Quận 1");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 2")) {
//          item.setDistrict("Quận 2");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 3")) {
//          item.setDistrict("Quận 3");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 4")) {
//          item.setDistrict("Quận 4");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 5")) {
//          item.setDistrict("Quận 5");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 6")) {
//          item.setDistrict("Quận 6");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 7")) {
//          item.setDistrict("Quận 7");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 8")) {
//          item.setDistrict("Quận 8");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 9")) {
//          item.setDistrict("Quận 9");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 10")) {
//          item.setDistrict("Quận 10");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 11")) {
//          item.setDistrict("Quận 11");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Quận 12")) {
//          item.setDistrict("Quận 12");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Bình Thạnh")) {
//          item.setDistrict("Bình Thạnh");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Tân Bình")) {
//          item.setDistrict("Tân Bình");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Phú Nhuận")) {
//          item.setDistrict("Phú Nhuận");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Bình Tân")) {
//          item.setDistrict("Bình Tân");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Bình Chánh")) {
//          item.setDistrict("Bình Chánh");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Gò Vấp")) {
//          item.setDistrict("Gò Vấp");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(" Củ Chi")) {
//          item.setDistrict("Củ Chi");
//          item.setProvince(HCM);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(HN)) {
//          item.setProvince(HN);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(VT)) {
//          item.setProvince(VT);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(LA)) {
//          item.setProvince(LA);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(VL)) {
//          item.setProvince(VL);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(BD)) {
//          item.setProvince(BD);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(DN)) {
//          item.setProvince(DN);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(DaNang)) {
//          item.setProvince(DaNang);
//          propertyAdressRepository.save(item);
//        }
//        else if (item.getSummary().contains(KH)) {
//          item.setProvince(KH);
//          propertyAdressRepository.save(item);
//        }
      }
      return "OK";
    }
    catch (Exception ex) {
      return "Not OK";
    }
  }

}
