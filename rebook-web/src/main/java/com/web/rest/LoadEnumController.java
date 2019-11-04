package com.web.rest;

import com.web.service.LoadEnumService;
import com.web.service.impl.LoadEnumServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/enumeration")
public class LoadEnumController {

  @Autowired
  private LoadEnumService loadEnumService;

  @GetMapping(value = "/province-city")
  public ResponseEntity<?> getProvinceEnum() {
    return new ResponseEntity<>(loadEnumService.loadProvinceEnum(), HttpStatus.OK);
  }

  @GetMapping(value = "/district")
  public ResponseEntity<?> getDistrictEnum() {
    return new ResponseEntity<>(loadEnumService.loadDistrict(), HttpStatus.OK);
  }

  @GetMapping(value = "/rent-type")
  public ResponseEntity<?> getForRentType() {
   return new ResponseEntity<>(loadEnumService.loadRentType(), HttpStatus.OK);
  }

  @GetMapping(value = "/sale-type")
  public ResponseEntity<?> getForSaleType() {
    return new ResponseEntity<>(loadEnumService.loadSaleType(), HttpStatus.OK);
  }

  @GetMapping(value = "/price-options")
  public ResponseEntity<?> getPriceEnum() {
    return new ResponseEntity<>(loadEnumService.loadPrice(), HttpStatus.OK);
  }
  @GetMapping(value = "/area-options")
  public ResponseEntity<?> getAreaEnum() {
    return new ResponseEntity<>(loadEnumService.loadArea(), HttpStatus.OK);
  }
  @GetMapping(value = "/direct-house")
  public ResponseEntity<?> getDirectHouseEnum() {
    return new ResponseEntity<>(loadEnumService.loadDirectHouse(), HttpStatus.OK);
  }
}
