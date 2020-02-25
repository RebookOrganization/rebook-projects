import React, {PureComponent} from 'react';
import {Button, Card, CardTitle, Input, Row} from "reactstrap";
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
import SkeletonLoading from "../../../components/Loading/SkeletonLoading";
import NewsHeader from "./NewsHeader";
import NewsDetail from "./NewsDetail";
import InteractiveInfo from "./InteractiveInfo";

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
      newItem.renderComment = false;
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
      if (item.shareNewsList && item.shareNewsList.length) {
        item.shareNewsList.map(i => {
          if (currentUser && i.userId === currentUser.userId) {
            newItem.activeShare = true;
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
        newItem.renderComment = false;
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
        if (item.shareNewsList && item.shareNewsList.length) {
          item.shareNewsList.map(i => {
            if (nextProps.currentUser && i.userId === nextProps.currentUser.userId) {
              newItem.activeShare = true;
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
    if (this.state.newsItem === 200) {
      this.setState({ hasMoreItems: false});
    }
    else {
      setTimeout(() => {
        this.setState({
          newsItem: this.state.newsItem + 20
        }, () => {
          getAllNewsItem(this.state.newsItem).then(res => {
            this.setState({
              allNewsItem: this.state.allNewsItem.concat(res.data.result)
            })
          }).catch(() => {
            Alert.warning("Không thể lấy thêm tin tức.");
          })
        });
      }, 3000);
    }
  };

  handleRenderNewsDetail = (newsId) => {
    let newsList = this.state.allNewsItem.map(item => {
      if (item.newsId === newsId) {
        item.newsDetail = !item.newsDetail;
        item.newsDetail ?
          item.textOfReadMore = "Thu gọn" :
            item.textOfReadMore = "Chi tiết";
        console.log("aaaaaaaaaaaa newsDetail: "+item.newsDetail);
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
        images.push({
          original: i.imageUrl.replace("/resize/200x200", ""),
          thumbnail: i.imageUrl,
        })
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
    const requestParams = {
      isShare: !this.state.isShare,
      userId: currentUser ? currentUser.userId : '',
      newsItemId: newsId ? newsId : '',
    };
    shareNews(requestParams).then(res => {
      let newList = this.state.allNewsItem.map(item => {
        if (item.newsId === newsId) {
          item.activeShare = res.data.result.userIsShare;
          item.shareAmount = res.data.result.shareAmount;
          item.hidden = false;
        }
        return item;
      });
      this.setState({allNewsItem: newList})
    }).catch((e)=>console.log(e))
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
    const loader = <SkeletonLoading/>;

    return (
      <InfiniteScroll pageStart={0}
                      loadMore={this.loadMoreNews}
                      hasMore={this.state.hasMoreItems}
                      loader={loader}>
        <React.Fragment>
          {
            createNewsPost ?
                <Card className="card">
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
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleLikePost(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={createNewsPost.renderComment ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleRenderComment(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={createNewsPost.activeShare ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
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
                                  <Row style={{padding:'0 20px'}}>
                                    <div className="input-comment" style={{paddingBottom:'10px'}}>
                                      <a className="btn-user">
                                        <img src={i.userImageUrl ? i.userImageUrl : '/icon/icons8-checked_user_male.png'}
                                             className="rounded-circle icon-user"
                                             alt="Username"/>
                                      </a>{' '}
                                      <p style={{borderRadius: '30px', padding: '10px',
                                        backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}} key={index}>
                                        <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2', marginBottom:'5px'}}>{i.userName ? i.userName : "UnknownUser"}</p>
                                        {i.content}
                                        <p style={{fontSize:'14px',fontWeight:'400', color:'#4267B2', marginBottom:'0'}}>{i.commentTime ? i.commentTime : "now"}</p>
                                      </p>
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
                  return (
                    item.hidden === false &&
                        <Card className="card" key={index}>
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
                                  style={item.activeLike ? {backgroundColor:'#20a8d8', color:'white', margin:'0 5px'} : {}}
                                  onClick={() => this.handleLikePost(item.newsId)}>
                                <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                              </Button>
                              <Button
                                  className="border-none-outline btn-like-share-comment"
                                  style={item.renderComment ? {backgroundColor:'#20a8d8', color:'white', margin:'0 5px'} : {}}
                                  onClick={() => this.handleRenderComment(item.newsId)}>
                                <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                              </Button>
                              <Button
                                  className="border-none-outline btn-like-share-comment"
                                  style={item.activeShare ? {backgroundColor:'#20a8d8', color:'white', margin:'0 5px'} : {}}
                                  onClick={() => this.handleSharePost(item.newsId)}>
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
                                          <Row style={{padding:'0 20px'}}>
                                            <div className="input-comment" style={{paddingBottom:'10px'}}>
                                              <a className="btn-user">
                                                <img src={i.userImageUrl ? i.userImageUrl : '/icon/icons8-checked_user_male.png'}
                                                     className="rounded-circle icon-user"
                                                     alt="Username"/>
                                              </a>{' '}
                                              <p style={{borderRadius: '30px', padding: '10px',
                                                backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}} key={index}>
                                                <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2', marginBottom:'5px'}}>{i.userName ? i.userName : "UnknownUser"}</p>
                                                {i.content}
                                                <p style={{fontSize:'14px',fontWeight:'400', color:'#4267B2', marginBottom:'0'}}>{i.commentTime ? i.commentTime : "now"}</p>
                                              </p>
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
                        </Card>
                  )
                }) : null
          }
        </React.Fragment>
      </InfiniteScroll>
    )
  }
}
export default ListCardItem;