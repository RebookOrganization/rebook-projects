package com.crawler.service.impl;

import static com.crawler.constant.Constant.*;
import com.crawler.bean.Response.CommonResponse;
import com.crawler.cache.CacheDataService;
import com.crawler.cache.NewsItemIndex;
import com.crawler.model.ContactOwner;
import com.crawler.model.NewsImageUrl;
import com.crawler.model.NewsItem;
import com.crawler.model.PropertyAddress;
import com.crawler.model.PropertyProject;
import com.crawler.model.User;
import com.crawler.repository.ContactOwnerRepository;
import com.crawler.repository.ImagesRepository;
import com.crawler.repository.NewsItemRepository;
import com.crawler.repository.PropertyAdressRepository;
import com.crawler.repository.PropertyProjectRepository;
import com.crawler.repository.UserRepository;
import com.crawler.service.CrawlerService;
import com.crawler.tfidf.TfIdfMain;
import com.crawler.utils.DateTimeUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

  private static Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

  @Autowired
  NewsItemRepository newsItemRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PropertyAdressRepository propertyAdressRepository;

  @Autowired
  PropertyProjectRepository propertyProjectRepository;

  @Autowired
  ContactOwnerRepository contactOwnerRepository;

  @Autowired
  ImagesRepository imagesRepository;

  @Autowired
  CacheDataService cacheDataService;

  private int returnCode = 1;
  private String returnMessage = "success";

  private static Integer currentPartition = DateTimeUtils.getPartition();

  @Override
  @Transactional
  public CommonResponse crawlerBatDongSan() {
    logger.info("Thread execute crawlerBatDongSan - {}", Thread.currentThread().getName());
    List<NewsItem> newsItemList = new ArrayList<>();
    double compareTfIdf;
    try {
      Document doc = Jsoup.connect(FOR_SALE).get();
      Elements elements = doc.select("item");
      for (Element item : elements) {
        String title = item.select("title").text();
        String url = item.select("link").text();
        String description = item.select("description").text();
        String pubDate = item.select("pubDate").text();

        String descriptMain = Jsoup.parse(description.replaceAll("<div[^>]*>", "\n")).text();

        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);

        //check trùng url tại đây.
        if (NewsItemIndex.newsItem != null && !NewsItemIndex.newsItem.getUrl().equals(url))
        {
          newsItem.setTrans_type("BatDongSan.com.vn");
          newsItem.setUrl(url);
          newsItem.setSummary(descriptMain);
          newsItem.setPostedDate(pubDate);
          newsItem.setPostedMilisec(DateTimeUtils.convertTimeStampMilisecond(pubDate, DateTimeUtils.GMT_FORMAT));

          Optional<User> user = userRepository.findByEmail("admin@gmail.com");
          user.ifPresent(newsItem::setUser);

          Document document = Jsoup.connect(url).get();
          Element productDetail = document.getElementById(NEW_DETAILS_TAG);

          if (productDetail != null) {
            if (productDetail.getElementsByClass(LOCATION_PROP) != null) {
              String khuvuc = productDetail.getElementsByClass(LOCATION_PROP).select("a").text();
              newsItem.setCity(khuvuc);
            }

            String price = productDetail.getElementsByClass(PRICE_PROP).first().select("strong").text();
            String area = productDetail.getElementsByClass(PRICE_PROP).get(1).select("strong").text();
            newsItem.setPrice(price);
            newsItem.setArea(area);

            String desc = productDetail.getElementsByClass(DESCRIPTION_PROP).text()
                .replaceAll("<br>", "\t");

            // check TfIdf tại đây.
            compareTfIdf = new TfIdfMain().compareText(NewsItemIndex.newsItem.getDescription(), desc);
            if (compareTfIdf >= 0.45) {
              newsItem.setDescription(desc);

              if (productDetail.getElementById(ROOM_NUMBER) != null) {
                String room = productDetail.getElementById(ROOM_NUMBER).getElementsByClass("right")
                    .text();
                newsItem.setRoom_number(room);
              }

              if (productDetail.getElementById(TOILET_NUMBER) != null) {
                String toilet = productDetail.getElementById(TOILET_NUMBER).getElementsByClass("right")
                    .text();
                newsItem.setToilet_number(toilet);
              }

              if (productDetail.getElementById(DIRECTOFHOUSE) != null) {
                String huongNha = productDetail.getElementById(DIRECTOFHOUSE).getElementsByClass("right")
                    .text();
                newsItem.setDirect_of_house(huongNha);
              }

              if (productDetail.getElementById(BALCONY) != null) {
                String balcony = productDetail.getElementById(BALCONY).getElementsByClass("right").text();
                newsItem.setBalcony(balcony);
              }

              if (productDetail.getElementById(FLOOR_NUMBER) != null) {
                String floorNumber = productDetail.getElementById(FLOOR_NUMBER)
                    .getElementsByClass("right").text();
                newsItem.setFloor_number(floorNumber);
              }

              if (productDetail.getElementById(WARDIN) != null) {
                String wardin = productDetail.getElementById(WARDIN).getElementsByClass("right").text();
                newsItem.setWardin(wardin);
              }

              if (productDetail.getElementById(FRONT_END) != null) {
                String frontEnd = productDetail.getElementById(FRONT_END).getElementsByClass("right")
                    .text();
                newsItem.setFrontEnd(frontEnd);
              }

              if (productDetail.getElementById(INTERIOR) != null) {
                String interior = productDetail.getElementById(INTERIOR).getElementsByClass("right")
                    .text();
                newsItem.setInterior(interior);
              }

              String startDate = productDetail.getElementsByClass("prd-more-info").first()
                  .getElementsByTag("div").get(4).text().substring(10);
              newsItem.setPubDate(startDate);

              String owner = "";
              String phone = "";
              String email = "";
              String address = "";
              ContactOwner contactOwner1 = new ContactOwner();
              if(productDetail.getElementById(OWNER_NAME) != null) {
                owner = productDetail.getElementById(OWNER_NAME).getElementsByClass("right").text();
              }
              if (productDetail.getElementById(OWNER_PHONE) != null) {
                phone = productDetail.getElementById(OWNER_PHONE).getElementsByClass("right").text();
              }
              if (productDetail.getElementById(OWNER_EMAIL) != null) {
                email = productDetail.getElementById(OWNER_EMAIL).getElementsByClass("right")
                    .select("a").text();
              }
              if (productDetail.getElementById(OWNER_ADDRESS) != null) {
                address = productDetail.getElementById(OWNER_ADDRESS).getElementsByClass("right")
                    .text();
              }

              contactOwnerRepository.insertWithPartition(currentPartition, address, owner, email, phone);
              contactOwner1 = contactOwnerRepository.findLastRow(currentPartition);
              newsItem.setContactOwnerId(contactOwner1.getId());

              String projectName = "";
              String projectOwner = "";
              String projectSize = "";
              PropertyProject propertyProject1 = new PropertyProject();
              Element project = productDetail.getElementById(PROJECT);
              if (project != null) {
                projectName = project.getElementsByClass("table-detail").first()
                    .getElementsByClass("row").first().getElementsByClass("right").text();

                if (project.getElementById(PROJECT_OWNER) != null) {
                  projectOwner = project.getElementById(PROJECT_OWNER).getElementsByClass("right")
                      .text();
                }
                if (project.getElementById(PROJECT_SIZE) != null) {
                  projectSize = project.getElementById(PROJECT_SIZE).getElementsByClass("right")
                      .text();
                }

                propertyProjectRepository.saveByPartition(currentPartition, projectName, projectOwner, projectSize);
                propertyProject1 = propertyProjectRepository.findLastRow(currentPartition);
                newsItem.setPropertyProjectId(propertyProject1.getId());

              }

              PropertyAddress propertyAddress1 = new PropertyAddress();
              Elements row = productDetail.getElementsByClass("div-hold").first()
                  .getElementsByClass("row");
              String prop_address = row.get(1).getElementsByClass("right").text();

              propertyAdressRepository.saveByPartition(currentPartition, "",
                  "", "", prop_address);
              propertyAddress1 = propertyAdressRepository.findLastRow(currentPartition);
              newsItem.setPropertyAddressId(propertyAddress1.getId());

              newsItemRepository.saveToPartition(newsItem.getArea(), newsItem.getBalcony(), newsItem.getCity(), newsItem.getDescription(),
                  newsItem.getDirect_of_house(), newsItem.getFloor_number(), newsItem.getFrontEnd(), newsItem.getInterior(),
                  newsItem.getPostedDate(), newsItem.getPostedMilisec(), newsItem.getPrice(), newsItem.getPubDate(), newsItem.getRoom_number(),
                  newsItem.getSummary(), newsItem.getTitle(), newsItem.getToilet_number(), newsItem.getTrans_type(), newsItem.getUrl(),
                  newsItem.getWardin(), contactOwner1.getId(), propertyAddress1.getId(),
                  propertyProject1.getId(), newsItem.getUser().getId(), currentPartition);

              NewsItem newsItem1 = newsItemRepository.findLastRow(currentPartition);
              newsItemList.add(newsItem1);

              if (newsItem1.getId() != null) {
                if (productDetail.getElementById("thumbs") != null) {
                  int imgsSize = productDetail.getElementById("thumbs").getElementsByTag("img").size();
                  for (int j = 0; j < imgsSize; j++) {
                    NewsImageUrl newsImageUrl = new NewsImageUrl();
                    newsImageUrl.setNewsItem(newsItem1);
                    newsImageUrl.setImageUrl(productDetail.getElementById("thumbs").getElementsByTag("img").get(j)
                        .attr("src"));
                    imagesRepository.saveByPartition(newsImageUrl.getImageSize(), newsImageUrl.getImageType(),
                        newsImageUrl.getImageUrl(), newsImageUrl.getPicByte(), newsItem1.getId(), currentPartition);
                  }
                }
              }
            }
            else {
              logger.info("BatDongSan.com.vn Tin tức trùng description - {}", desc);
            }
          }
        }
        else {
          logger.info("BatDongSan.com.vn Tin trùng lặp url - {}", url);
        }
        //cập nhật lại index mới nhất
        indexNewsItem();
      }

      return new CommonResponse<>(this.returnCode, this.returnMessage, new PageImpl<>(newsItemList));
    } catch (IOException e) {
      logger.error("crawlerBatDongSan exception: " + e);
      return new CommonResponse.Fail("crawlerBatDongSan exception.");
    }
  }

  private void indexNewsItem() {
    NewsItemIndex.newsItem = cacheDataService.findLastRow(currentPartition);
  }

  @Override
  @Transactional
  public CommonResponse crawlerDiaOcOnline() {
    logger.info("Thread execute crawlerDiaOcOnline - {}", Thread.currentThread().getName());
    List<NewsItem> newsItemList = new ArrayList<>();
    double compareTfIdf;
    try {
      Document doc = Jsoup.connect(DIAOCONLINE_DUAN_QUYHOACH).get();
      Elements entrys = doc.select("entry");
      for (Element entry : entrys) {
        String title = entry.select("title").text();
        String url = entry.select("link").attr("href");
        String pubDate = entry.select("updated").text();

        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);

        // check trùng url
        if (NewsItemIndex.newsItem != null && !NewsItemIndex.newsItem.getUrl().equals(url)) {
          newsItem.setTrans_type("DiaOcOnline.vn");
          newsItem.setUrl(url);
          newsItem.setPostedDate(pubDate);
          newsItem.setPostedMilisec(DateTimeUtils.convertTimeStampMilisecond(pubDate, DateTimeUtils.DATE_FORMAT));

          Optional<User> user = userRepository.findByEmail("admin@gmail.com");
          user.ifPresent(newsItem::setUser);

          Document document = Jsoup.connect(url).get();

          Elements elements = document
              .getElementsByClass("rounded_style_2 rounded_box margin_bottom");
          Element detail = elements.first().getElementById("detail");
          String descript = detail.getElementsByClass("body").first()
              .getElementsByTag("p").text().replaceAll("<br>", "\n");
          newsItem.setDescription(descript);

          // check TfIdf tại đây.
          compareTfIdf = new TfIdfMain().compareText(NewsItemIndex.newsItem.getDescription(), descript);
          if (compareTfIdf >= 0.45) {
            newsItem.setTitle(title);
            newsItem.setPubDate(pubDate);

            String address = elements.first().getElementsByClass("location").text().substring(7);
            propertyAdressRepository.saveByPartition(currentPartition, "", "", "", address);
            PropertyAddress propertyAddress1 = propertyAdressRepository.findLastRow(currentPartition);

            newsItem.setPropertyAddressId(propertyAddress1.getId());

            ContactOwner contactOwner = new ContactOwner();
            Elements contact = elements.first().getElementsByClass("contact_info margin_bottom");
            contactOwner.setContactName(contact.first().getElementsByTag("a").text());
            contactOwner.setPhoneNumber(contact.first().getElementsByTag("span").text());
            contactOwner.setAddress(contact.first().getElementsByTag("dd").get(1).text());
            contactOwnerRepository.insertWithPartition(currentPartition, contactOwner.getAddress(),
                contactOwner.getContactName(), contactOwner.getEmail(), contactOwner.getPhoneNumber());
            ContactOwner contactOwner1 = contactOwnerRepository.findLastRow(currentPartition);

            newsItem.setContactOwnerId(contactOwner1.getId());

            newsItem.setPrice(elements.first().getElementsByClass("money").text().substring(5));

            Elements block = elements.first().getElementsByClass("block");

            newsItem.setArea(block.first().getElementsByTag("td")
                .first().getElementsByTag("strong").text());

            newsItem.setFloor_number(block.get(1).getElementsByTag("td")
                .get(5).getElementsByTag("strong").text());

            newsItem.setToilet_number(block.get(1).getElementsByTag("td")
                .get(8).getElementsByTag("strong").text());

            newsItem.setRoom_number(block.get(1).getElementsByTag("td")
                .get(7).getElementsByTag("strong").text());

            newsItem.setDirect_of_house(block.get(1).getElementsByTag("td")
                .get(2).getElementsByTag("strong").text());

            newsItem.setWardin(block.get(1).getElementsByTag("td")
                .get(3).getElementsByTag("strong").text());

            newsItemRepository.saveToPartition(newsItem.getArea(), newsItem.getBalcony(), newsItem.getCity(), newsItem.getDescription(),
                newsItem.getDirect_of_house(), newsItem.getFloor_number(), newsItem.getFrontEnd(), newsItem.getInterior(),
                newsItem.getPostedDate(), newsItem.getPostedMilisec(), newsItem.getPrice(), newsItem.getPubDate(),
                newsItem.getRoom_number(), newsItem.getSummary(), newsItem.getTitle(), newsItem.getToilet_number(),
                newsItem.getTrans_type(), newsItem.getUrl(), newsItem.getWardin(), contactOwner1.getId(),
                propertyAddress1.getId(), null, newsItem.getUser().getId(), currentPartition);

            NewsItem newsItem1 = newsItemRepository.findLastRow(currentPartition);
            newsItemList.add(newsItem);

            assert newsItem1 != null;
            Element flexslider = document.getElementById("slider");
            if (flexslider != null) {
              Elements slideLarges = flexslider.getElementsByClass("slideLarge");
              if (slideLarges != null) {
                for (Element slideLarge : slideLarges) {
                  NewsImageUrl newsImageUrl = new NewsImageUrl();
                  newsImageUrl.setImageUrl(slideLarge.getElementsByTag("img").attr("src"));
                  newsImageUrl.setNewsItem(newsItem1);
                  imagesRepository.saveByPartition(newsImageUrl.getImageSize(), newsImageUrl.getImageType(),
                      newsImageUrl.getImageUrl(), newsImageUrl.getPicByte(), newsItem1.getId(), currentPartition);
                }
              }
              else {
                if (flexslider.getElementsByTag("img") != null) {
                  NewsImageUrl newsImageUrl = new NewsImageUrl();
                  newsImageUrl.setImageUrl(flexslider.getElementsByTag("img").attr("src"));
                  newsImageUrl.setNewsItem(newsItem1);
                  imagesRepository.saveByPartition(newsImageUrl.getImageSize(), newsImageUrl.getImageType(),
                      newsImageUrl.getImageUrl(), newsImageUrl.getPicByte(), newsItem1.getId(), currentPartition);
                }
              }
            }
          }
          else {
            logger.info("DiaOcOnline.vn Tin tức trùng description - {}", descript);
          }
        }
        else {
          logger.info("DiaOcOnline.vn Tin trùng lặp url - {}", url);
        }
        //cập nhật lại index mới nhất
        indexNewsItem();
      }

      return new CommonResponse<>(this.returnCode, this.returnMessage, new PageImpl<>(newsItemList));
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("CrawlerServiceImpl crawlerDiaOcOnline Exception: ", ex);
      return new CommonResponse.Fail("CrawlerServiceImpl crawlerDiaOcOnline Exception.");
    }
  }

}
