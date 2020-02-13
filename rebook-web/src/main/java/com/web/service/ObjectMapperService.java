package com.web.service;

import com.web.bean.Response.NewsResponseDTO;
import com.web.dto.CommentNewsDTO;
import com.web.dto.LikeNewsDTO;
import com.web.dto.ShareNewsDTO;
import com.web.model.Comment;
import com.web.model.ContactOwner;
import com.web.model.LikeNews;
import com.web.model.NewsItem;
import com.web.model.PropertyAddress;
import com.web.model.PropertyProject;
import com.web.model.ShareNews;
import com.web.model.User;
import com.web.repository.CommentRepository;
import com.web.repository.ContactOwnerRepository;
import com.web.repository.LikeRepository;
import com.web.repository.PropertyAdressRepository;
import com.web.repository.PropertyProjectRepository;
import com.web.repository.ShareRepository;
import com.web.repository.UserRepository;
import com.web.utils.DateTimeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperService {

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ShareRepository shareRepository;

  @Autowired
  private PropertyAdressRepository propertyAdressRepository;

  @Autowired
  private ContactOwnerRepository contactOwnerRepository;

  @Autowired
  private PropertyProjectRepository propertyProjectRepository;

  @Autowired
  private UserRepository userRepository;

  private static Integer currentPartition = DateTimeUtils.getPartition();

  public NewsResponseDTO mapNewsToNewsResponseDTO(NewsItem newsItem) {
    NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
    newsResponseDTO.setImageUser(newsItem.getUser().getImageUrl());
    newsResponseDTO.setUsername(newsItem.getUser().getName());
    newsResponseDTO.setTitleNews(newsItem.getTitle());

    if (newsItem.getSummary() != null) {
      newsResponseDTO.setSummaryNews(newsItem.getSummary());
    }

    if (newsItem.getDescription() != null) {
      newsResponseDTO.setDescriptionNews(newsItem.getDescription());
    }

    newsResponseDTO.setPubDate(newsItem.getPostedDate());
    newsResponseDTO.setPrice(newsItem.getPrice());
    newsResponseDTO.setArea(newsItem.getArea());

    if (newsItem.getPropertyAddressId() != null) {
      Optional<PropertyAddress> propertyAddress = propertyAdressRepository.findById(currentPartition,
          newsItem.getPropertyAddressId());
      propertyAddress.ifPresent(address -> newsResponseDTO.setAddress_prop(address.getSummary()));
    }

    newsResponseDTO.setImageUrlList(newsItem.getImages());
    newsResponseDTO.setNewsId(newsItem.getId());
    newsResponseDTO.setUserId(newsItem.getUser().getId());

    if (newsItem.getContactOwnerId() != null) {
      Optional<ContactOwner> contactOwner = contactOwnerRepository.findById(currentPartition,
          newsItem.getContactOwnerId());
      contactOwner.ifPresent(contact -> {
        newsResponseDTO.setContactEmail(contact.getEmail());
        newsResponseDTO.setContactName(contact.getContactName());
        newsResponseDTO.setContactPhone(contact.getPhoneNumber());
      });

    }

    if (newsItem.getPropertyProjectId() != null) {
      Optional<PropertyProject> propertyProject = propertyProjectRepository.findById(currentPartition,
          newsItem.getPropertyProjectId());
      propertyProject.ifPresent(project -> {
        newsResponseDTO.setProjectOwner(project.getProjectOwner());
        newsResponseDTO.setProjectSize(project.getProjectSize());
        newsResponseDTO.setProjectName(project.getProjectName());
      });
    }

    List<LikeNewsDTO> likeNewsDTOList = new ArrayList<>();
    List<LikeNews> likeNewsList = likeRepository.findLikeNewsByNewsItemId(newsItem.getId());
    for (LikeNews likeNews : likeNewsList) {
      LikeNewsDTO likeNewsDTO = new LikeNewsDTO();
      likeNewsDTO.setId(likeNews.getId());
      likeNewsDTO.setLike(likeNews.isLike());
      likeNewsDTO.setNewsItemId(likeNews.getNewsItemId());
      likeNewsDTO.setUserId(likeNews.getUserId());
      Optional<User> userLike = userRepository.findById(likeNews.getUserId());
      if (userLike.isPresent()) {
        likeNewsDTO.setUserName(userLike.get().getName());
        likeNewsDTO.setUserImageUrl(userLike.get().getImageUrl());
      }
      likeNewsDTOList.add(likeNewsDTO);
    }
    newsResponseDTO.setLikeNewsList(likeNewsDTOList);

    List<CommentNewsDTO> commentNewsDTOList = new ArrayList<>();
    List<Comment> commentList = commentRepository.findByNewItemId(newsItem.getId());
    for (Comment comment : commentList) {
      CommentNewsDTO commentNewsDTO = new CommentNewsDTO();
      commentNewsDTO.setId(comment.getId());
      commentNewsDTO.setContent(comment.getContent());
      commentNewsDTO.setNewItemId(comment.getNewItemId());
      commentNewsDTO.setUserId(comment.getUserId());
      commentNewsDTO.setCommentTime(comment.getTimeComment());
      Optional<User> userComment = userRepository.findById(comment.getUserId());
      if (userComment.isPresent()) {
        commentNewsDTO.setUserName(userComment.get().getName());
        commentNewsDTO.setUserImageUrl(userComment.get().getImageUrl());
      }
      commentNewsDTOList.add(commentNewsDTO);
    }
    newsResponseDTO.setCommentList(commentNewsDTOList);

    List<ShareNewsDTO> shareNewsDTOList = new ArrayList<>();
    List<ShareNews> shareNewsList = shareRepository.findByNewItemId(newsItem.getId());
    for (ShareNews shareNews: shareNewsList) {
      ShareNewsDTO shareNewsDTO = new ShareNewsDTO();
      shareNewsDTO.setId(shareNews.getId());
      shareNewsDTO.setNewItemId(shareNews.getNewItemId());
      shareNewsDTO.setShare(shareNews.isShare());
      shareNewsDTO.setUserId(shareNews.getUserId());
      shareNewsDTO.setShareTime(shareNews.getShareTime());
      Optional<User> userShare = userRepository.findById(shareNews.getUserId());
      if (userShare.isPresent()) {
        shareNewsDTO.setUserName(userShare.get().getName());
        shareNewsDTO.setUserImageUrl(userShare.get().getImageUrl());
      }
      shareNewsDTOList.add(shareNewsDTO);
    }
    newsResponseDTO.setShareNewsList(shareNewsDTOList);

    return newsResponseDTO;
  }
}
