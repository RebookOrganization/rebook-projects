package com.web.service.impl;

import com.web.bean.Request.CommentRequest;
import com.web.bean.Request.LikeRequest;
import com.web.bean.Request.PostNewsRequest;
import com.web.bean.Request.ShareRequest;
import com.web.bean.Response.CommonResponse;
import com.web.bean.Response.CommonResponse.Fail;
import com.web.bean.Response.LikeResponse;
import com.web.bean.Response.NewsResponseDTO;
import com.web.bean.Response.ShareResponse;
import com.web.bean.Response.UploadFileResponse;
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
import com.web.service.FileStorageService;
import com.web.service.UserService;
import com.web.utils.DateTimeUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.omg.CORBA.DATA_CONVERSION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

  private int returnCode = 1;
  private String returnMessage = "success";

  private static Integer currentPartition = DateTimeUtils.getPartition();

  @Override
  @Transactional
  public CommonResponse createNewsPost(PostNewsRequest request)
      throws IOException {
    try {
      NewsItem newsItem = new NewsItem();
      newsItem.setTitle(request.getTitle());
      newsItem.setPostedDate(request.getPub_date());
      newsItem.setUser(userRepository.findById(request.getUser_id()).get());
      newsItem.setUrl(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
      try {
        newsItem.setSummary(request.getDesc().substring(0, 100));
      } catch (Exception e) {
        logger.error("Can't set summary for news.");
      }

      newsItemRepository.save(newsItem);

      Set<NewsImageUrl> setImgUrl = new HashSet<>();
      List<UploadFileResponse> listUpload = request.getListUpload();
      for (UploadFileResponse upload : listUpload) {
        NewsImageUrl newsImageUrl = new NewsImageUrl();
        newsImageUrl.setImageUrl(upload.getFileDownloadUri());
        newsImageUrl.setImageType(upload.getFileType());
        newsImageUrl.setImageSize(upload.getSize());
        newsImageUrl.setNewsItem(newsItem);
        setImgUrl.add(newsImageUrl);
      }
      imagesRepository.saveAll(setImgUrl);

      newsItem.setPubDate(request.getPub_date());
      newsItem.setPrice(request.getPrice());
      newsItem.setArea(request.getArea());
      newsItem.setDescription(request.getDesc());
      newsItem.setRoom_number(request.getRoom_number());
      newsItem.setDirect_of_house(request.getDirect_house());
      newsItem.setFloor_number(request.getFloor_number());
      newsItem.setToilet_number(request.getToilet_number());
      newsItem.setInterior(request.getInterior());

      ContactOwner contactOwner = new ContactOwner();
      contactOwner.setContactName(request.getOwnerName());
      contactOwner.setPhoneNumber(request.getOwnerPhone());
      contactOwner.setAddress(request.getOwnerAddress());
      contactOwnerRepository.save(contactOwner);

      newsItem.setContactOwnerId(contactOwner.getId());

      PropertyAddress propAddress = new PropertyAddress();
      propAddress.setSummary(request.getProp_address());
      propertyAdressRepository.save(propAddress);

      newsItem.setPropertyAddressId(propAddress.getId());

      PropertyProject propertyProject = new PropertyProject();
      propertyProject.setProjectName(request.getProject_name());
      propertyProject.setProjectSize(request.getProject_size());
      propertyProject.setProjectOwner(request.getProject_owner());
      propertyProjectRepository.save(propertyProject);

      newsItem.setPropertyProjectId(propertyProject.getId());

      newsItemRepository.save(newsItem);

      return new CommonResponse<>(this.returnCode, this.returnMessage, newsItem);
    } catch (Exception ex) {
      logger.error("Service createNewsPost exception: ", ex);
      return new CommonResponse.Fail("Service createNewsPost exception.");
    }
  }

  @Override
  @Transactional
  public CommonResponse likeNewsFeed(LikeRequest request) throws IOException {
    try {
      LikeNews like = new LikeNews();
      like.setLike(true);
      like.setNewsItemId(request.getNewsItemId());
      like.setUserId(request.getUserId());

      likeRepository.save(like);

      List<LikeNews> listLike = likeRepository.findByNewsItemId(request.getNewsItemId());
      Integer likeAmount = listLike.size();

      return new CommonResponse<>(this.returnCode, this.returnMessage,
          new LikeResponse(listLike, likeAmount));
    } catch (Exception e) {
      logger.error("Service likeNewsFeed exception - {}", e);
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

      List<Comment> listComment = commentRepository.findByNewItemId(request.getNewsItemId());

      return new CommonResponse<>(this.returnCode, this.returnMessage, listComment);
    } catch (Exception e) {
      logger.error("Service commentNewsFeed exception - {}", e);
      return new CommonResponse.Fail("Service commentNewsFeed exception.");
    }
  }

  @Override
  @Transactional
  public CommonResponse shareNewsFeed(ShareRequest request) throws IOException {
    try {
      ShareNews shareNews = new ShareNews();
      shareNews.setUserId(request.getUserId());
      shareNews.setNewItemId(request.getNewsItemId());
      shareNews.setShare(request.isShare());

      shareRepository.save(shareNews);

      List<ShareNews> listShareNews = shareRepository.findByNewItemId(request.getNewsItemId());
      int shareAmount = listShareNews.size();

      return new CommonResponse<>(this.returnCode, this.returnMessage,
          new ShareResponse(listShareNews, shareAmount));
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

            List<Comment> commentList = commentRepository.findByNewItemId(newsItem.getId());
            newsResponseDTO.setCommentList(commentList);

            List<LikeNews> likeNewsList = likeRepository.findByNewsItemId(newsItem.getId());
            newsResponseDTO.setLikeNewsList(likeNewsList);

            List<ShareNews> shareNewsList = shareRepository.findByNewItemId(newsItem.getId());
            newsResponseDTO.setShareNewsList(shareNewsList);

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

  private UploadFileResponse uploadImage(MultipartFile file) {
    String fileName = fileStorageService.storeFile(file);

    String imageURI = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/downloadFile/")
        .path(fileName)
        .toUriString();

    return new UploadFileResponse(fileName, imageURI, file.getContentType(), file.getSize());
  }

  private List<UploadFileResponse> uploadMultiImages(MultipartFile[] files) {
    return Arrays.stream(files)
        .map(this::uploadImage)
        .collect(Collectors.toList());
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
}