package com.web.service;

import com.web.enumeration.District;
import com.web.enumeration.ProvinceCity;
import com.web.model.PropertyAddress;
import com.web.repository.PropertyAdressRepository;
import java.util.List;
import java.util.Objects;
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

  public float convertAreaNum (String area)  {
    float output = 0f;
    if (area.contains("m2")) {
      output = Float.parseFloat(area.replace("m2", "").trim());
    }
    else if (area.contains("m²")) {
      output = Float.parseFloat(area.replace("m²", "").trim());
    }
    return output;
  }

  public String updateAddressData()  {
    try {
      List<PropertyAddress> listAll = propertyAdressRepository.findAll();
      for (PropertyAddress item : listAll) {
        if (ProvinceCity.fromDisplayValue(item.getSummary()) != null) {
          item.setProvince(Objects.requireNonNull(ProvinceCity.fromDisplayValue(item.getSummary())).getDisplayValue());
        }
        if (District.fromDisplayValue(item.getSummary()) != null) {
          item.setDistrict(Objects.requireNonNull(District.fromDisplayValue(item.getSummary())).getDisplayValue());
        }
      }
      return "OK";
    }
    catch (Exception ex) {
      return "Not OK";
    }
  }

}
