package com.web.service.impl;

import com.web.bean.Request.CommentRequest;
import com.web.bean.Request.LikeRequest;
import com.web.bean.Request.PostNewsRequest;
import com.web.bean.Request.ShareRequest;
import com.web.bean.Request.UpdateUserProfileRequest;
import com.web.bean.Response.CommonResponse;
import com.web.bean.Response.CommonResponse.Fail;
import com.web.bean.Response.LikeResponse;
import com.web.bean.Response.NewsResponseDTO;
import com.web.bean.Response.ShareResponse;
import com.web.bean.Response.UploadFileResponse;
import com.web.dto.CommentNewsDTO;
import com.web.dto.NewPostDto;
import com.web.enumeration.District;
import com.web.enumeration.ProvinceCity;
import com.web.model.Comment;
import com.web.model.ContactOwner;
import com.web.model.LikeNews;
import com.web.model.NewsImageUrl;
import com.web.model.NewsItem;
import com.web.model.PropertyAddress;
import com.web.model.PropertyProject;
import com.web.model.ShareNews;
import com.web.model.User;
import com.web.repository.CommentRepository;
import com.web.repository.ContactOwnerRepository;
import com.web.repository.ImagesRepository;
import com.web.repository.LikeRepository;
import com.web.repository.NewsItemRepository;
import com.web.repository.PropertyAdressRepository;
import com.web.repository.PropertyProjectRepository;
import com.web.repository.ShareRepository;
import com.web.repository.UserRepository;
import com.web.service.ConvertDataService;
import com.web.service.FileStorageService;
import com.web.service.ObjectMapperService;
import com.web.service.UserService;
import com.web.utils.DateTimeUtils;
import com.web.utils.GsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UserServiceImpl implements UserService {

  private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  UserRepository userRepository;

  @Autowired
  NewsItemRepository newsItemRepository;

  @Autowired
  ContactOwnerRepository contactOwnerRepository;

  @Autowired
  PropertyAdressRepository propertyAdressRepository;

  @Autowired
  PropertyProjectRepository propertyProjectRepository;

  @Autowired
  ImagesRepository imagesRepository;

  @Autowired
  LikeRepository likeRepository;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  ShareRepository shareRepository;

  @Autowired
  FileStorageService fileStorageService;

  @Autowired
  ConvertDataService convertDataService;

  private int returnCode = 1;
  private String returnMessage = "success";
  private static Integer currentPartition = DateTimeUtils.getPartition();

  private ObjectMapperService objectMapperService;

  @Autowired
  public UserServiceImpl(ObjectMapperService objectMapperService) {
    this.objectMapperService = objectMapperService;
  }

  @Override
  @Transactional
  public CommonResponse createNewsPost(PostNewsRequest request)
      throws IOException {
    logger.info("UserServiceImpl createNewsPost request: {}", GsonUtils.toJsonString(request));
    try {
      String summary = "";
      try {
        if (request.getDesc().length() > 100) {
          summary = request.getDesc().substring(0, 100);
        }
        else {
          summary = request.getDesc().substring(0, 50);
        }
      } catch (Exception e) {
        logger.error("Can't set summary for news.");
      }

      int partition = DateTimeUtils.getPartition();
      long userId = request.getUser_id();
      long postedMilisec = DateTimeUtils.convertTimeStampMilisecond(request.getPub_date(), DateTimeUtils.DATE_FORMAT);
      String pubDate = request.getPub_date();
      String price = request.getPrice();
      String area = request.getArea();
      String description = request.getDesc();
      String room_number = request.getRoom_number();
      String directHouse = request.getDirect_house();
      String floorNum = request.getFloor_number();
      String toiletNum = request.getToilet_number();
      String interior = request.getInterior();
      float area_num = convertDataService.convertAreaNum(request.getArea());
      double price_num = convertDataService.convertPriceNum(request.getPrice(), area_num);

      newsItemRepository.saveToPartition(area, "", description, directHouse, floorNum, "", interior,
          pubDate, postedMilisec, price, request.getPub_date(), room_number, summary, request.getTitle(), toiletNum, "", "",
          "", 0L, 0L, 0L, userId, area_num, price_num, partition);

      NewsItem newsItem = newsItemRepository.findLastRow(partition);

      String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/news-item/").path(summary.substring(0, 50).replace(" ", "-") + "/")
          .path(newsItem.getId().toString()).toUriString();
      newsItem.setUrl(url);

      Set<NewsImageUrl> imageUrlSet = new HashSet<>();
      List<UploadFileResponse> listUpload = request.getListUpload();
      for (UploadFileResponse upload : listUpload) {
        imagesRepository.saveByPartition(upload.getSize(), upload.getFileType(), upload.getFileAsResourceUri(), null, newsItem.getId(), partition);
        NewsImageUrl newsImageUrl = imagesRepository.findLastRow(partition);
        imageUrlSet.add(newsImageUrl);
      }
      newsItem.setImages(imageUrlSet);

      contactOwnerRepository.insertWithPartition(partition, request.getOwnerAddress(), request.getOwnerName(), "", request.getOwnerPhone());
      ContactOwner contactOwner = contactOwnerRepository.findLastRow(partition);
      long contactOwnerId = contactOwner.getId();
      newsItem.setContactOwnerId(contactOwnerId);

      String summaryAddress = request.getProp_address();
      String province = "";
      String district = "";
      if (ProvinceCity.fromDisplayValue(request.getProp_address()) != null) {
        province = Objects.requireNonNull(ProvinceCity.fromDisplayValue(request.getProp_address())).getDisplayValue();
      }
      if (District.fromDisplayValue(request.getProp_address()) != null) {
        district = Objects.requireNonNull(District.fromDisplayValue(request.getProp_address())).getDisplayValue();
      }
      propertyAdressRepository.saveByPartition(partition, district, province, "", summaryAddress);
      PropertyAddress propertyAddress = propertyAdressRepository.findLastRow(partition);
      long propertyAddressId = propertyAddress.getId();
      newsItem.setPropertyAddressId(propertyAddressId);

      propertyProjectRepository.saveByPartition(partition, request.getProject_name(), request.getProject_owner(), request.getProject_size());
      PropertyProject propertyProject = propertyProjectRepository.findLastRow(partition);
      long propertyProjectId = propertyProject.getId();
      newsItem.setPropertyProjectId(propertyProjectId);

      newsItemRepository.updateNewsPartition(partition, newsItem.getId(), propertyAddressId, contactOwnerId, propertyProjectId, url);

      NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
//      logger.info("UserServiceImpl createNewsPost response: {}", GsonUtils.toJsonString(newsResponseDTO));

      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTO);
    } catch (Exception ex) {
      logger.error("Service createNewsPost exception: ", ex);
      return new CommonResponse.Fail("Service createNewsPost exception.");
    }
  }

  @Override
  @Transactional
  public CommonResponse likeNewsFeed(LikeRequest request) throws IOException {
    try {
      LikeNews likeNews = likeRepository.findByNewsItemIdAndUserId(request.getUserId(), request.getNewsItemId());
      if (likeNews != null) {
        likeNews.setLike(!likeNews.isLike());
        likeRepository.save(likeNews);

        List<LikeNews> listLike = likeRepository.findLikeNewsByNewsItemId(request.getNewsItemId());
        Integer likeAmount = listLike.size();

        return new CommonResponse<>(this.returnCode, this.returnMessage,
            new LikeResponse(listLike, likeAmount, likeNews.isLike()));
      }
      else {
        LikeNews like = new LikeNews();
        like.setLike(true);
        like.setNewsItemId(request.getNewsItemId());
        like.setUserId(request.getUserId());
        likeRepository.save(like);

        List<LikeNews> listLike = likeRepository.findLikeNewsByNewsItemId(request.getNewsItemId());
        Integer likeAmount = listLike.size();

        return new CommonResponse<>(this.returnCode, this.returnMessage,
            new LikeResponse(listLike, likeAmount, like.isLike()));
      }

    } catch (Exception e) {
      logger.error("Service likeNewsFeed exception - {}", e.getMessage());
      return new CommonResponse.Fail("Service likeNewsFeed exception.");
    }
  }

  @Override
  @Transactional
  public CommonResponse commentNewsFeed(CommentRequest request) throws IOException {
    try {
      Comment comment = new Comment();
      comment.setUserId(request.getUserId());
      comment.setNewItemId(request.getNewsItemId());
      comment.setContent(request.getComment());

      commentRepository.save(comment);

      List<CommentNewsDTO> commentNewsDTOList = new ArrayList<>();
      List<Comment> listComment = commentRepository.findByNewItemId(request.getNewsItemId());
      for (Comment comment1 : listComment) {
        CommentNewsDTO commentNewsDTO = new CommentNewsDTO();
        commentNewsDTO.setId(comment1.getId());
        commentNewsDTO.setUserId(comment1.getUserId());
        commentNewsDTO.setNewItemId(comment1.getNewItemId());
        commentNewsDTO.setContent(comment1.getContent());
        commentNewsDTO.setCommentTime(comment1.getTimeComment());
        Optional<User> user = userRepository.findById(comment1.getUserId());
        if (user.isPresent()) {
          commentNewsDTO.setUserName(user.get().getName());
          commentNewsDTO.setUserImageUrl(user.get().getImageUrl());
        }
        commentNewsDTOList.add(commentNewsDTO);
      }

      return new CommonResponse<>(this.returnCode, this.returnMessage, commentNewsDTOList);
    } catch (Exception e) {
      logger.error("Service commentNewsFeed exception - {}", e.getMessage());
      return new CommonResponse.Fail("Service commentNewsFeed exception.");
    }
  }

  @Override
  @Transactional
  public CommonResponse shareNewsFeed(ShareRequest request) throws IOException {
    try {
      ShareNews shareNewsItem = shareRepository.findByNewItemIdAndUserId(request.getNewsItemId(), request.getUserId());
      if (shareNewsItem != null) {
          shareNewsItem.setShare(!shareNewsItem.isShare());
          shareRepository.save(shareNewsItem);

        List<ShareNews> listShareNews = shareRepository.findByNewItemId(request.getNewsItemId());
        int shareAmount = listShareNews.size();

        return new CommonResponse<>(this.returnCode, this.returnMessage,
            new ShareResponse(listShareNews, shareAmount, shareNewsItem.isShare()));
      }
      else {
        ShareNews shareNews = new ShareNews();
        shareNews.setUserId(request.getUserId());
        shareNews.setNewItemId(request.getNewsItemId());
        shareNews.setShare(request.isShare());
        shareRepository.save(shareNews);

        List<ShareNews> listShareNews = shareRepository.findByNewItemId(request.getNewsItemId());
        int shareAmount = listShareNews.size();

        return new CommonResponse<>(this.returnCode, this.returnMessage,
            new ShareResponse(listShareNews, shareAmount, shareNews.isShare()));
      }

    } catch (Exception ex) {
      logger.error("Service shareNewsFeed exception: "+ ex);
      return new CommonResponse.Fail("Service shareNewsFeed exception.");
    }
  }

  @Override
  public CommonResponse searchNewsByUser(String username) throws IOException {
    try {
      List<User> users = userRepository.findByNameLike(username);
      List<NewsItem> listNewsItemSearch = new ArrayList<>();

      if (!users.isEmpty()) {
        for (User user : users) {
          List<NewsItem> listNewsItem = newsItemRepository.findAllByUser(user);
          listNewsItemSearch.addAll(listNewsItem);
        }

        return new CommonResponse<>(this.returnCode, this.returnMessage, listNewsItemSearch);
      }

      return new Fail("User không tồn tại");
    } catch (Exception ex) {
      logger.error("System error when search with user: " + ex, ex);
      return new Fail("System error when search with user");
    }
  }

  @Override
  public CommonResponse searchNewsByAddress(String address) throws IOException {
    try {
      List<PropertyAddress> listAddress = propertyAdressRepository.findAllBySummary(address);
      List<NewsResponseDTO> listNewsResponseDTO = new ArrayList<>();

      if (!listAddress.isEmpty()) {
        NewsItem newsItem;
        for (PropertyAddress addressIndex : listAddress) {
          newsItem = newsItemRepository.findByPropertyAddress(addressIndex.getId());

          if(newsItem != null) {
            NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
            newsResponseDTO.setNewsId(newsItem.getId());
            newsResponseDTO.setUsername(newsItem.getUser().getName());
            newsResponseDTO.setTitleNews(newsItem.getTitle());
            newsResponseDTO.setImageUser(newsItem.getUser().getImageUrl());
            newsResponseDTO.setSummaryNews(newsItem.getSummary());
            newsResponseDTO.setPubDate(newsItem.getPostedDate());
            newsResponseDTO.setPrice(newsItem.getPrice());
            newsResponseDTO.setArea(newsItem.getArea());
            newsResponseDTO.setAddress_prop(addressIndex.getSummary());

            if (newsItem.getDescription() != null) {
              newsResponseDTO.setDescriptionNews(newsItem.getDescription());
            }

            if (newsItem.getContactOwnerId() != null) {
              Optional<ContactOwner> contactOwner = contactOwnerRepository.findById(currentPartition,
                  newsItem.getContactOwnerId());
              assert contactOwner.isPresent();
              if (contactOwner.get().getEmail() != null) {
                newsResponseDTO.setContactEmail(contactOwner.get().getEmail());
              }

              newsResponseDTO.setContactName(contactOwner.get().getContactName());
              newsResponseDTO.setContactPhone(contactOwner.get().getPhoneNumber());
            }

            if (newsItem.getPropertyProjectId() != null) {
              Optional<PropertyProject> propertyProject = propertyProjectRepository.findById(currentPartition,
                  newsItem.getPropertyProjectId());
              assert propertyProject.isPresent();
              newsResponseDTO.setProjectOwner(propertyProject.get().getProjectOwner());
              newsResponseDTO.setProjectSize(propertyProject.get().getProjectSize());
              newsResponseDTO.setProjectName(propertyProject.get().getProjectName());
            }

            newsResponseDTO.setImageUrlList(newsItem.getImages());
            newsResponseDTO.setUserId(newsItem.getUser().getId());

//            List<Comment> commentList = commentRepository.findByNewItemId(newsItem.getId());
//            newsResponseDTO.setCommentList(commentList);
//
//            List<LikeNews> likeNewsList = likeRepository.findLikeNewsByNewsItemId(newsItem.getId());
//            newsResponseDTO.setLikeNewsList(likeNewsList);
//
//            List<ShareNews> shareNewsList = shareRepository.findByNewItemId(newsItem.getId());
//            newsResponseDTO.setShareNewsList(shareNewsList);

            listNewsResponseDTO.add(newsResponseDTO);
          }
        }
        return new CommonResponse<>(this.returnCode, this.returnMessage, listNewsResponseDTO);
      }

      return new CommonResponse<>(this.returnCode, this.returnMessage, listNewsResponseDTO);
    } catch (Exception ex) {
      logger.error("System error when search with address: " + ex, ex);
      return new Fail("System error when search with address");
    }
  }

  @Override
  public CommonResponse getAllNewsByUser(Long userID) throws Exception {
    try {
      List<NewsItem> newsItemList = new ArrayList<>();
      Optional<User> user = userRepository.findById(userID);
      if (user.isPresent()) {
        newsItemList = newsItemRepository.findAllByUser(user.get());
      }
      return new CommonResponse<>(this.returnCode, this.returnMessage, newsItemList);
    }
    catch (Exception ex) {
      return new Fail("Lấy thông tin bài viết của user thất bại.");
    }
  }

  @Override
  public CommonResponse updateUserProfile(UpdateUserProfileRequest request) throws Exception {
    try {
      String name = request.getName();
      String imageUrl = request.getImageUrl();
      if (!name.isEmpty() && !imageUrl.isEmpty()) {
        userRepository.updateUserProfile(request.getUserId(), name, imageUrl, request.getPhoneNumber(),
            request.getBirthDate(), request.getGender());
        return new CommonResponse<>(1, "Cập nhật thông tin thành công.", null);
      }

      return new CommonResponse.Fail("Cập nhật thông tin tài khoản không thành công.");
    }
    catch (Exception ex) {
      logger.error("UserServiceImpl updateUserProfile exception: ", ex);
      return new CommonResponse.Fail("Cập nhật thông tin tài khoản không thành công.");
    }
  }
}
