import React, {PureComponent} from 'react';
import {
  Badge,
  Button,
  Card, CardBody,
  CardText,
  CardTitle,
  Input,
  Modal,
  Row
} from "reactstrap";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import shallowCompare from 'react-addons-shallow-compare';
import ImageGallery from 'react-image-gallery';
import "react-image-gallery/styles/scss/image-gallery.scss";
import "react-image-gallery/styles/css/image-gallery.css";
import {
  commentNews,
  getAllNewsItem,
  likeNews,
  shareNews
} from "../../../api/userCallApi";
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import './_listCardItem.css';
import InfiniteScroll from "react-infinite-scroller";
import NewsHeader from "./NewsHeader";
import NewsDetail from "./NewsDetail";
import InteractiveInfo from "./InteractiveInfo";
import Skeleton from "react-loading-skeleton";
import ModalNewsDetail from "./ModalNewsDetail";
import ModalShareNews from "./ModalShareNews";

class ListCardItem extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      comment: "",
      currentUser: null,
      allNewsItem: null,
      renderComment: false,
      likePosted: null,
      sharePosted: null,
      isLike: false,
      isShare: false,
      newsItem: 20,
      hasMoreItems: true,
      createNewsPost: null,
      modalShare: false,
      itemShare: null,
      loadingShare: false,
    }
  }

  componentWillMount() {
    const {currentUser, allNewsItem, createNewsPost} = this.props;
    let newItem = null;
    let allNewsList = allNewsItem ? allNewsItem.map(item => {
      newItem = item;
      newItem.hidden = false;
      newItem.newsDetail = false;
      newItem.textOfReadMore = "Chi tiết";
      newItem.renderComment = true;
      newItem.activeLike = false;
      if (item.likeNewsList && item.likeNewsList.length) {
        item.likeNewsList.map(i => {
          if (currentUser && i.userId === currentUser.userId) {
            newItem.activeLike = true;
          }
        })
      }
      newItem.likeAmount = item.likeNewsList ? item.likeNewsList.length : 0;
      newItem.commentAmount = item.commentList ? item.commentList.length : 0;
      newItem.activeShare = false;
      newItem.renderShare = false;
      newItem.userNameShare = "";
      newItem.userImgShare = "";
      if (item.shareNewsList && item.shareNewsList.length) {
        item.shareNewsList.map(i => {
          if (currentUser && i.userId === currentUser.userId) {
            newItem.activeShare = true;
          }
          if (newItem.newsId && newItem.newsId === i.newItemId && i.share) {
            newItem.renderShare = true;
            newItem.userNameShare = i.userName;
            newItem.userImgShare = i.userImageUrl;
          }
        })
      }
      newItem.shareAmount = item.shareNewsList ? item.shareNewsList.length : 0;
      return newItem;
    }) : null;

    let NewPost = null;
    NewPost = createNewsPost;
    if (NewPost) {
      NewPost.hidden = false;
      NewPost.newsDetail = false;
      NewPost.textOfReadMore = "Chi tiết";
      NewPost.renderComment = false;
      NewPost.activeLike = false;
      if (NewPost.likeNewsList && NewPost.likeNewsList.length) {
        NewPost.likeNewsList.map(i => {
          if (currentUser && i.userId === currentUser.userId) {
            NewPost.activeLike = true;
          }
        })
      }
      NewPost.likeAmount = NewPost.likeNewsList ? NewPost.likeNewsList.length : 0;
      NewPost.commentAmount = NewPost.commentList ? NewPost.commentList.length : 0;
      NewPost.activeShare = false;
      newItem.renderShare = false;
      newItem.userNameShare = "";
      newItem.userImgShare = "";
      if (NewPost.shareNewsList && NewPost.shareNewsList.length) {
        NewPost.shareNewsList.map(i => {
          if (currentUser && i.userId === currentUser.userId) {
            NewPost.activeShare = true;
          }
        })
      }
      NewPost.shareAmount = NewPost.shareNewsList ? NewPost.shareNewsList.length : 0;
    }

    this.setState({
      currentUser: currentUser,
      allNewsItem: allNewsList,
      createNewsPost: NewPost
    })
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      let newItem = null;
      let allNewsList = nextProps.allNewsItem ? nextProps.allNewsItem.map(item => {
        newItem = item;
        newItem.hidden = false;
        newItem.newsDetail = false;
        newItem.textOfReadMore = "Chi tiết";
        newItem.renderComment = true;
        newItem.activeLike = false;
        if (item.likeNewsList && item.likeNewsList.length) {
          item.likeNewsList.map(i => {
            if (nextProps.currentUser && i.userId === nextProps.currentUser.userId) {
              newItem.activeLike = true;
            }
          })
        }
        newItem.likeAmount = item.likeNewsList ? item.likeNewsList.length : 0;
        newItem.commentAmount = item.commentList ? item.commentList.length : 0;
        newItem.activeShare = false;
        newItem.renderShare = false;
        newItem.userNameShare = "";
        newItem.userImgShare = "";
        if (item.shareNewsList && item.shareNewsList.length) {
          item.shareNewsList.map(i => {
            if (nextProps.currentUser && i.userId === nextProps.currentUser.userId) {
              newItem.activeShare = true;
            }
            if (newItem.newsId && newItem.newsId === i.newItemId && i.share) {
              newItem.renderShare = true;
              newItem.userNameShare = i.userName;
              newItem.userImgShare = i.userImageUrl;
            }
          })
        }
        newItem.shareAmount = item.shareNewsList ? item.shareNewsList.length : 0;
        return newItem;
      }) : null;

      let NewPost = null;
      NewPost = nextProps.createNewsPost;
      if (NewPost) {
        NewPost.hidden = false;
        NewPost.newsDetail = false;
        NewPost.textOfReadMore = "Chi tiết";
        NewPost.renderComment = false;
        NewPost.activeLike = false;
        if (NewPost.likeNewsList && NewPost.likeNewsList.length) {
          NewPost.likeNewsList.map(i => {
            if (nextProps.currentUser && i.userId === nextProps.currentUser.userId) {
              NewPost.activeLike = true;
            }
          })
        }
        NewPost.likeAmount = NewPost.likeNewsList ? NewPost.likeNewsList.length : 0;
        NewPost.commentAmount = NewPost.commentList ? NewPost.commentList.length : 0;
        NewPost.activeShare = false;
        NewPost.renderShare = false;
        NewPost.userNameShare = "";
        NewPost.userImgShare = "";
        if (NewPost.shareNewsList && NewPost.shareNewsList.length) {
          NewPost.shareNewsList.map(i => {
            if (nextProps.currentUser && i.userId === nextProps.currentUser.userId) {
              NewPost.activeShare = true;
            }
          })
        }
        NewPost.shareAmount = NewPost.shareNewsList ? NewPost.shareNewsList.length : 0;
      }

      this.setState({
        currentUser: nextProps.currentUser,
        allNewsItem: allNewsList,
        createNewsPost: NewPost
      })
    }
  }

  loadMoreNews = () => {
    if (this.state.newsItem === 400) {
      this.setState({ hasMoreItems: false});
    }
    else {
      setTimeout(() => {
        this.setState({
          newsItem: this.state.newsItem + 20
        }, () => {
          getAllNewsItem(this.state.newsItem).then(res => {
            const {allNewsItem} = this.state;
            let newList = res.data.result ? res.data.result.map(item => {
              item.hidden = false;
              item.renderComment = true;
              return item;
            }) : [];
            this.setState({
              allNewsItem: allNewsItem.concat(newList)
            },()=>console.log("allNewsItem length: ", this.state.allNewsItem.length))
          }).catch(() => {
            Alert.warning("Không thể lấy thêm tin tức.");
          })
        });
      }, 10000);
    }
  };

  handleRenderNewsDetail = (newsId) => {
    let newsList = this.state.allNewsItem.map(item => {
      if (item.newsId === newsId) {
        item.newsDetail = !item.newsDetail;
        item.newsDetail ?
          item.textOfReadMore = "Thu gọn" :
            item.textOfReadMore = "Chi tiết";
      }
      return item;
    });
    this.setState({allNewsItem: newsList});
  };

  handleRenderComment = (newsId) => {
    let newList = this.state.allNewsItem.map(item => {
      if (item.newsId === newsId) {
        item.renderComment = !item.renderComment;
        item.hidden = false;
      }
      return item;
    });
    this.setState({allNewsItem: newList});
  };

  handleRenderImageSlide = (imageList) => {
    if (imageList) {
      let images = [];
      imageList.map(i => {
        if (i.imageUrl) {
          images.push({
            original: i.imageUrl.replace("/resize/200x200", ""),
            thumbnail: i.imageUrl,
          })
        }
      });
      return (
          <ImageGallery items={images}/>
      )
    }
  };

  handleLikePost = (newsId) => {
    const {currentUser} = this.state;
    const requestParams = {
      isLike: this.state.isLike,
      userId: currentUser ? currentUser.userId : '',
      newsItemId: newsId ? newsId : '',
    };
    likeNews(requestParams).then(res => {
      let newList = this.state.allNewsItem.map(item => {
        if (item.newsId === newsId) {
          item.activeLike = res.data.result.userIsLike;
          item.likeAmount = res.data.result.likeAmount;
          item.hidden = false;
        }
        return item;
      });
      this.setState({allNewsItem: newList})
    }).catch((e)=>console.log(e))

  };

  handleCommentPost = (newsId) => {
    const {comment, currentUser} = this.state;
    const requestParams = {
      comment: comment ? comment : Alert.warning("Chưa viết nội dung..."),
      userId: currentUser ? currentUser.userId : '',
      newsItemId: newsId ? newsId : '',
    };
    commentNews(requestParams).then(res => {
      let newList = this.state.allNewsItem.map(item => {
        if (item.newsId === newsId) {
          item.renderComment = true;
          item.commentAmount = res.data.result ? res.data.result.length : 0;
          item.commentList = res.data.result;
          item.hidden = false;
        }
        return item;
      });
      this.setState({
        allNewsItem: newList
      },()=>this.setState({comment: ""}))
    })
  };

  handleSharePost = (newsId) => {
    const {currentUser} = this.state;
    this.setState({loadingShare: true});
    const requestParams = {
      share: 1,
      userId: currentUser ? currentUser.userId : '',
      newsItemId: newsId ? newsId : '',
    };
    console.log("requestParams share: ", requestParams);
    shareNews(requestParams).then(res => {
      let newList = this.state.allNewsItem.map(item => {
        if (item.newsId === newsId) {
          item.activeShare = res.data.result.userIsShare;
          item.shareAmount = res.data.result.shareAmount;
          item.hidden = false;
          if (res.data.result.userIsShare) {
            Alert.success("Chia sẻ thành công.")
          }
        }
        return item;
      });
      this.setState({
        allNewsItem: newList,
      })
    }).catch((e)=>console.log(e))
    .finally(()=>this.setState({
      loadingShare: false,
      modalShare: false
    }))
  };

  toggleModalShare = (item) => {
    this.setState({
      modalShare: !this.state.modalShare,
    }, ()=> {
      if (this.state.modalShare) {
        this.setState({itemShare: item})
      }
    });
  };

  handleHidePost = (newsId) => {
    let newList = this.state.allNewsItem.map(item => {
      if (item.newsId === newsId) {
        item.hidden = true
      }
      return item;
    });
    this.setState({allNewsItem: newList});
  };

  render() {
    const {allNewsItem, currentUser, createNewsPost} = this.state;

    return (
      <div>
        <InfiniteScroll pageStart={0}
                        loadMore={this.loadMoreNews}
                        hasMore={this.state.hasMoreItems}
                        loader={
                          <div key={0}>
                            <Card style={{padding:'15px'}}>
                              <div style={{marginBottom: '15px'}}>
                                <Skeleton circle={true} height={50} width={50} />
                              </div>
                              <Skeleton count={5}/>
                            </Card>
                          </div>
                        }>
          {
            createNewsPost ?
                <Card className="card" key={createNewsPost.newsId}>
                  <NewsHeader handleHidePost={this.handleHidePost} newsItem={createNewsPost}/>
                  <NewsDetail handleRenderNewsDetail={this.handleRenderNewsDetail}
                              newsItem={createNewsPost}/>

                  <div style={{marginBottom: '10px'}}>
                    {
                      createNewsPost.imageUrlList && createNewsPost.imageUrlList.length ?
                          this.handleRenderImageSlide(createNewsPost.imageUrlList) : null
                    }
                  </div>

                  <InteractiveInfo newsItem={createNewsPost} handleRenderComment={this.handleRenderComment}/>

                  <hr style={{margin: '5px 20px'}}/>

                  <div>
                    <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={createNewsPost.activeLike ?
                              {color:'#20a8d8', margin:'0 5px'} : {}}
                          onClick={() => this.handleLikePost(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={createNewsPost.renderComment ?
                              {color:'#20a8d8', margin:'0 5px'} : {}}
                          onClick={() => this.handleRenderComment(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={createNewsPost.activeShare ?
                              {color:'#20a8d8', margin:'0 5px'} : {}}
                          onClick={() => this.handleSharePost(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/share-right.svg" alt={""}/> Chia sẻ
                      </Button>
                    </ButtonGroup>
                  </div>

                  <hr/>
                  {
                    createNewsPost.renderComment ?
                        <React.Fragment>
                          {
                            createNewsPost.commentList ? createNewsPost.commentList.map((i, index) => {
                              return (
                                  <Row style={{padding:'0 20px'}} key={index}>
                                    <div className="input-comment" style={{paddingBottom:'10px'}}>
                                      <a className="btn-user">
                                        <img src={i.userImageUrl ? i.userImageUrl : '/icon/icons8-checked_user_male.png'}
                                             className="rounded-circle icon-user"
                                             alt="Username"/>
                                      </a>{' '}
                                      <CardText style={{padding: '10px', color: 'black', textAlign: 'left',
                                        backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}} key={index}>
                                        <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2', marginBottom:'10px'}}>{i.userName ? i.userName : "UnknownUser"}</p>
                                        {i.content}
                                        <p style={{fontSize:'14px',fontWeight:'400', color:'#4267B2', marginBottom:'0', marginTop: '10px'}}>
                                          {i.commentTime ? i.commentTime : "now"}
                                        </p>
                                      </CardText>
                                    </div>
                                  </Row>
                              )
                            }) : null
                          }
                        </React.Fragment>
                        : null
                  }

                  <div className="input-comment">
                    <a className="btn-user">
                      <img
                          src={currentUser && currentUser.imageUrl
                              ? currentUser.imageUrl
                              : '/icon/default.jpg'}
                          className="rounded-circle icon-user"
                          alt="Username"/>
                    </a>{' '}
                    <Input style={{borderRadius: '36px', height: '40px',
                      border:'1px solid #bbc0c4',
                      backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px'}}
                           placeholder="Viết bình luận..."
                           value={this.state.comment}
                           onChange={(e) => this.setState(
                               {comment: e.target.value})}
                           onKeyDown={(e)=>{if (e.key === "Enter") {
                             this.handleCommentPost(createNewsPost.newsId)
                           }}}
                    />
                    <button style={{border:'none', outline:'none'}}>
                      <img className={"responsive"} src={"/icon/iconfinder_ins.svg"} style={{width:'34px'}} alt={""}/>
                    </button>
                  </div>
                </Card>
                : null
          }
          {
            allNewsItem ?
                allNewsItem.map((item, index) => {
                  console.log("item.renderShare: ", item.renderShare);
                  return (
                      item.hidden === false &&
                      <Card className="card" key={index}>
                        {
                          item.renderShare ?
                              <React.Fragment>
                                <CardTitle>
                                  <div className="row"
                                       style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                                    <div className="col-md-9">
                                      <a className="btn-circle btn-lg">
                                        <img
                                            src={item.userImgShare ? item.userImgShare
                                                : '/icon/default.jpg'}
                                            className="rounded-circle img-profile"
                                            alt="Username"/>
                                      </a>{' '}
                                      <a href={item.userImgShare ? item.userImgShare
                                          : '/icon/default.jpg'}
                                         className="username"
                                      >
                                        <strong>{item.userNameShare ? item.userNameShare
                                            : 'username'}</strong>
                                      </a>
                                    </div>
                                    <div className="col-md-3">
                                      <div className="dropdown float-right">
                                        <button className="btn border-none-outline"
                                                type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false">
                                          <img src="/icon/menu-5.svg" style={{width:'23px',height:'23px'}}/>
                                        </button>
                                        <div className="dropdown-menu"
                                             aria-labelledby="dropdownMenuButton">
                                          <a className="dropdown-item">
                                            <i className="far fa-eye-slash"/> Ẩn bài viết
                                          </a>
                                          <a className="dropdown-item">
                                            <i className="far fa-save"/> Lưu bài viết
                                          </a>
                                          <a className="dropdown-item">
                                            <i className="far fa-flag"/> Gửi phản hồi
                                          </a>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </CardTitle>
                                <Card style={{margin:'20px'}}>
                                  <CardBody style={{padding:'10px'}}>
                                    <NewsHeader handleHidePost={this.handleHidePost} newsItem={item}/>
                                    <NewsDetail handleRenderNewsDetail={this.handleRenderNewsDetail}
                                                newsItem={item}/>

                                    <div style={{marginBottom: '10px'}}>
                                      {item.imageUrlList && item.imageUrlList.length ?
                                          this.handleRenderImageSlide(item.imageUrlList) : null}
                                    </div>
                                  </CardBody>
                                </Card>
                              </React.Fragment> :
                              <React.Fragment>
                                <NewsHeader handleHidePost={this.handleHidePost} newsItem={item}/>
                                <NewsDetail handleRenderNewsDetail={this.handleRenderNewsDetail}
                                            newsItem={item}/>

                                <div style={{marginBottom: '10px'}}>
                                  {item.imageUrlList && item.imageUrlList.length ?
                                      this.handleRenderImageSlide(item.imageUrlList) : null}
                                </div>

                                <InteractiveInfo newsItem={item} handleRenderComment={this.handleRenderComment}/>

                                <hr style={{margin: '5px 20px'}}/>
                                <div>
                                  <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={item.activeLike ? {color:'#20a8d8', margin:'0 5px'} : {}}
                                        onClick={() => this.handleLikePost(item.newsId)}>
                                      <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                                    </Button>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={item.renderComment ? {color:'#20a8d8', margin:'0 5px'} : {}}
                                        onClick={() => this.handleRenderComment(item.newsId)}>
                                      <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                                    </Button>
                                    <Button
                                        className="border-none-outline btn-like-share-comment"
                                        style={item.activeShare ? {color:'#20a8d8', margin:'0 5px'} : {}}
                                        // onClick={() => this.handleSharePost(item.newsId, item)}
                                        onClick={()=>this.toggleModalShare(item)}
                                    >
                                      <img className={"styleIcon"} src="/icon/share-right.svg" alt={""}/> Chia sẻ
                                    </Button>
                                  </ButtonGroup>
                                </div>
                                <hr/>
                                {
                                  item.renderComment ?
                                      <React.Fragment>
                                        {
                                          item.commentList ? item.commentList.map((i, index) => {
                                            return (
                                                <Row style={{padding:'0 20px'}} key={index}>
                                                  <div className="input-comment" style={{paddingBottom:'10px'}}>
                                                    <a className="btn-user">
                                                      <img src={i.userImageUrl ? i.userImageUrl : '/icon/icons8-checked_user_male.png'}
                                                           className="rounded-circle icon-user"
                                                           alt="Username"/>
                                                    </a>{' '}
                                                    <Badge style={{padding: '10px', color: 'black', textAlign: 'left',
                                                      backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}} key={index}>
                                                      <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2', marginBottom:'10px'}}>{i.userName ? i.userName : "UnknownUser"}</p>
                                                      {i.content}
                                                      <p style={{fontSize:'14px',fontWeight:'400', color:'#4267B2', marginBottom:'0', marginTop:'10px'}}>
                                                        {i.commentTime ? i.commentTime : "now"}
                                                      </p>
                                                    </Badge>
                                                  </div>
                                                </Row>
                                            )
                                          }) : null
                                        }
                                      </React.Fragment>
                                      : null
                                }

                                <div className="input-comment">
                                  <a className="btn-user">
                                    <img
                                        src={currentUser && currentUser.imageUrl
                                            ? currentUser.imageUrl
                                            : '/icon/default.jpg'}
                                        className="rounded-circle icon-user"
                                        alt="Username"/>
                                  </a>{' '}
                                  <Input style={{borderRadius: '36px', height: '40px',
                                    border:'1px solid #bbc0c4',
                                    backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px'}}
                                         placeholder="Viết bình luận..."
                                         value={this.state.comment}
                                         onChange={(e) => this.setState(
                                             {comment: e.target.value})}
                                         onKeyDown={(e)=>{if (e.key === "Enter") {
                                           this.handleCommentPost(item.newsId)
                                         }}}
                                  />
                                  <button style={{border:'none', outline:'none'}}>
                                    <img className={"responsive"} src={"/icon/iconfinder_ins.svg"} style={{width:'34px'}} alt={""}/>
                                  </button>
                                </div>

                                <ModalNewsDetail modalNewsDetail={item.newsDetail}
                                                 newsItem={item}/>
                              </React.Fragment>
                        }
                      </Card>
                  )
                }) : null
          }
        </InfiniteScroll>

        <ModalShareNews modalShare={this.state.modalShare}
                        newsItem={this.state.itemShare}
                        toggleModalShare={this.toggleModalShare}
                        handleSharePost={this.handleSharePost}
                        loadingShare={this.state.loadingShare}
                        currentUser={currentUser}/>
      </div>
    )
  }
}
export default ListCardItem;