import React, {Component, PureComponent} from 'react';
import {
  Button,
  Card,
  CardBody,
  CardImg,
  CardTitle,
  Input,
  Spinner
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
import Skeleton from 'react-loading-skeleton';
import SkeletonLoading from "../../../components/Loading/SkeletonLoading";

class ListCardItem extends PureComponent{
  constructor(props) {
    super(props);
    this.state = {
      comment: "",
      newsDetail: false,
      textOfReadMore: "Chi tiết",
      currentUser: null,
      indexNews: [0],
      allNewsItem: null,
      renderComment: false,
      likePosted: null,
      activeLike: false,
      sharePosted: null,
      activeShare: false,
      isLike: false,
      isShare: false,
      newsItem: 20,
      hasMoreItems: true,
      createNewsPost: null,
    }
  }

  componentWillMount() {
    const {currentUser, allNewsItem, createNewsPost} = this.props;
    this.setState({
      currentUser: currentUser,
      allNewsItem: allNewsItem,
      createNewsPost: createNewsPost
    })
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({
        currentUser: nextProps.currentUser,
        allNewsItem: nextProps.allNewsItem,
        createNewsPost: nextProps.createNewsPost
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

  handleRenderNewsDetail = (index) => {
    this.setState({
      indexNews: this.state.indexNews.push(index),
      newsDetail: !this.state.newsDetail
    },() => {
      let {newsDetail, indexNews} = this.state;
      if (newsDetail && indexNews.includes(index)) {
        this.setState({
          textOfReadMore : "Thu gọn"
        })
      }
      else {
        this.setState({
          textOfReadMore: "Chi tiết"
        })
      }
    })
  };

  handleRenderComment = (index) => {
    this.setState({
      indexNews: index,
      renderComment: !this.state.renderComment
    })
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
      console.log("like response: "+ JSON.stringify(res));
      this.setState({
        likePosted: res.data.result,
        indexNews: this.state.indexNews.push(newsId),
        activeLike: res.data.result.userIsLike
      })
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
      console.log("comment response: "+ JSON.stringify(res));
      this.setState({
        commentPosted: res.data.result,
        indexNews: this.state.indexNews.push(newsId),
      })
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
      console.log("share response: "+ JSON.stringify(res));
      this.setState({
        sharePosted: res.data.result,
        indexNews: this.state.indexNews.push(newsId),
        activeShare: res.data.result.userIsShare
      })
    }).catch((e)=>console.log(e))
  };

  render() {
    const {allNewsItem, newsDetail, textOfReadMore, currentUser, indexNews,
      renderComment, activeLike, activeShare, createNewsPost} = this.state;
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
                  <CardTitle>
                    <div className="row"
                         style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                      <div className="col-md-9">
                        <a className="btn-circle btn-lg">
                          <img
                              src={createNewsPost.imageUser ? createNewsPost.imageUser
                                  : '/icon/default.jpg'}
                              className="rounded-circle img-profile"
                              alt="Username"/>
                        </a>{' '}
                        <a href={createNewsPost.imageUser ? createNewsPost.imageUser
                            : '/icon/default.jpg'}
                           className="username"
                        >
                          <strong>{createNewsPost.username ? createNewsPost.username
                              : 'username'}</strong>
                        </a>

                        {/*pub Date*/}
                        <div style={{color: '#606770', margin: '0 70px'}}>
                          {createNewsPost.pubDate ? createNewsPost.pubDate : ''}
                        </div>
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

                  <div className="row"
                       style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
                    <p className={"styleTitle"}>
                      {createNewsPost.titleNews ? createNewsPost.titleNews : null}
                    </p>
                    <p className={"styleText"}>
                      <strong>Giá: </strong>{createNewsPost.price ? createNewsPost.price : null}
                    </p>
                    <p className={"styleText"}>
                      <strong>Diện tích: </strong>{createNewsPost.area ? createNewsPost.area : null}
                    </p>
                    <p className={"styleText"}>
                      <strong>Địa chỉ: </strong>{createNewsPost.address_prop ? createNewsPost.address_prop
                        : null}
                    </p>
                    <p className={"styleTitle"}>
                      {createNewsPost.summaryNews ? createNewsPost.summaryNews : null}
                    </p>

                    <a style={{
                      fontSize: '16px',
                      fontWeight: 'normal',
                      lineHeight: '1.58',
                      fontFamily: 'inherit',
                      marginBottom: '10px',
                      paddingRight: '5px',
                      color: '#20a8d8'
                    }}
                       onClick={()=>this.handleRenderNewsDetail(createNewsPost.newsId)}>
                      {textOfReadMore}
                    </a>
                    {
                      newsDetail && indexNews === createNewsPost.newsId ?
                          <p className={"styleTitle"}>
                            {createNewsPost.descriptionNews ? createNewsPost.descriptionNews : null}
                          </p> : null
                    }

                    <p className={"styleTitle"}>
                      <strong>Liên hệ: </strong>{' '}
                      {createNewsPost.contactName ? createNewsPost.contactName : null}
                      {createNewsPost.contactPhone ? createNewsPost.contactPhone : null}
                      {createNewsPost.contactEmail ? createNewsPost.contactEmail : null}
                    </p>
                    <p className={"styleTitle"}>
                      {createNewsPost.projectName ? createNewsPost.projectName : null}
                      {createNewsPost.projectOwner ? createNewsPost.projectOwner : null}
                      {createNewsPost.projectSize ? createNewsPost.projectSize : null}
                    </p>
                  </div>

                  <div style={{marginBottom: '10px'}}>
                    {
                      createNewsPost.imageUrlList && createNewsPost.imageUrlList.length ? this.handleRenderImageSlide(createNewsPost.imageUrlList) : null
                    }
                  </div>

                  {/*Luot like luot share o day*/}
                  <div style={{margin: '0 20px'}}>
                    <a className="amount-like-share" style={{color: '#606770'}}>
                      <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
                      <img className={"styleIcon"} src="/icon/heart.svg"/>
                      {createNewsPost.likeAmount ? createNewsPost.likeAmount : 0}
                    </a>
                    <a className="float-right amount-like-share"
                       style={{marginLeft: '10px',color: '#606770'}}>
                      {createNewsPost.shareList ? createNewsPost.shareList.length : 0} lượt share
                    </a>
                    <a className="float-right amount-like-share"
                       onClick={()=>this.handleRenderComment(createNewsPost.newsId)}
                       style={{color: '#606770'}}
                    >
                      {createNewsPost.commentList ? createNewsPost.commentList.length : 0} comment
                    </a>
                  </div>

                  <hr style={{margin: '5px 20px'}}/>

                  <div>
                    <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={activeLike && indexNews.includes(createNewsPost.newsId) ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleLikePost(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={renderComment && indexNews === createNewsPost.newsId ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleRenderComment(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={activeShare && indexNews === createNewsPost.newsId ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleSharePost(createNewsPost.newsId)}>
                        <img className={"styleIcon"} src="/icon/share-right.svg" alt={""}/> Chia sẻ
                      </Button>
                    </ButtonGroup>
                  </div>

                  <hr/>
                  {
                    renderComment && indexNews === createNewsPost.newsId ?
                        <React.Fragment>
                          <div className="input-comment" style={{paddingBottom:'10px'}}>
                            <a className="btn-user">
                              <img
                                  src={'/icon/icons8-checked_user_male.png'}
                                  className="rounded-circle icon-user"
                                  alt="Username"/>
                            </a>{' '}
                            <p style={{borderRadius: '30px', width:'470px', padding: '10px',
                              backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}}>
                              <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2'}}>{"Other User "}</p>
                              {this.state.commentPosted}
                            </p>
                          </div>
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
                    <button style={{border:'none', outline:'none'}}>
                      <img className={"responsive"} src={"/icon/iconfinder_picture.svg"} style={{width:'36px'}} alt={""}/>
                    </button>
                  </div>
                </Card>
                : null
          }
          {
            allNewsItem ?
                allNewsItem.map((item, index) => {
                  return (
                      <Card className="card" key={index}>
                        <CardTitle>
                          <div className="row"
                               style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
                            <div className="col-md-9">
                              <a className="btn-circle btn-lg">
                                <img
                                    src={item.imageUser ? item.imageUser
                                        : '/icon/default.jpg'}
                                    className="rounded-circle img-profile"
                                    alt="Username"/>
                              </a>{' '}
                              <a href={item.imageUser ? item.imageUser
                                  : '/icon/default.jpg'}
                                 className="username"
                              >
                                <strong>{item.username ? item.username
                                    : 'username'}</strong>
                              </a>

                              {/*pub Date*/}
                              <div style={{color: '#606770', margin: '0 70px'}}>
                                {item.pubDate ? item.pubDate : ''}
                              </div>
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

                        <div className="row"
                             style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
                          <p className={"styleTitle"}>
                            {item.titleNews ? item.titleNews : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Giá: </strong>{item.price ? item.price : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Diện tích: </strong>{item.area ? item.area : null}
                          </p>
                          <p className={"styleText"}>
                            <strong>Địa chỉ: </strong>{item.address_prop ? item.address_prop
                              : null}
                          </p>
                          <p className={"styleTitle"}>
                            {item.summaryNews ? item.summaryNews : null}
                          </p>

                          <a style={{
                            fontSize: '16px',
                            fontWeight: 'normal',
                            lineHeight: '1.58',
                            fontFamily: 'inherit',
                            marginBottom: '10px',
                            paddingRight: '5px',
                            color: '#20a8d8'
                          }}
                             onClick={()=>this.handleRenderNewsDetail(item.newsId)}>
                            {textOfReadMore}
                          </a>
                          {
                            newsDetail && indexNews === item.newsId ?
                                <p className={"styleTitle"}>
                                  {item.descriptionNews ? item.descriptionNews : null}
                                </p> : null
                          }

                          <p className={"styleTitle"}>
                            <strong>Liên hệ: </strong>{' '}
                            {item.contactName ? item.contactName : null}
                            {item.contactPhone ? item.contactPhone : null}
                            {item.contactEmail ? item.contactEmail : null}
                          </p>
                          <p className={"styleTitle"}>
                            {item.projectName ? item.projectName : null}
                            {item.projectOwner ? item.projectOwner : null}
                            {item.projectSize ? item.projectSize : null}
                          </p>
                        </div>

                        <div style={{marginBottom: '10px'}}>
                          {
                            item.imageUrlList && item.imageUrlList.length ? this.handleRenderImageSlide(item.imageUrlList) : null
                          }
                        </div>

                        {/*Luot like luot share o day*/}
                        <div style={{margin: '0 20px'}}>
                          <a className="amount-like-share" style={{color: '#606770'}}>
                            <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
                            <img className={"styleIcon"} src="/icon/heart.svg"/>
                            {item.likeAmount ? item.likeAmount : 0}
                          </a>
                          <a className="float-right amount-like-share"
                             style={{marginLeft: '10px',color: '#606770'}}>
                            {item.shareList ? item.shareList.length : 0} lượt share
                          </a>
                          <a className="float-right amount-like-share"
                             onClick={()=>this.handleRenderComment(item.newsId)}
                             style={{color: '#606770'}}
                          >
                            {item.commentList ? item.commentList.length : 0} comment
                          </a>
                        </div>

                        <hr style={{margin: '5px 20px'}}/>

                        <div>
                          <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
                            <Button
                                className="border-none-outline btn-like-share-comment"
                                style={activeLike && indexNews.includes(item.newsId) ?
                                    {backgroundColor:'#20a8d8', color:'white'} : {}}
                                onClick={() => this.handleLikePost(item.newsId)}>
                              <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
                            </Button>
                            <Button
                                className="border-none-outline btn-like-share-comment"
                                style={renderComment && indexNews === item.newsId ?
                                    {backgroundColor:'#20a8d8', color:'white'} : {}}
                                onClick={() => this.handleRenderComment(item.newsId)}>
                              <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
                            </Button>
                            <Button
                                className="border-none-outline btn-like-share-comment"
                                style={activeShare && indexNews === item.newsId ?
                                    {backgroundColor:'#20a8d8', color:'white'} : {}}
                                onClick={() => this.handleSharePost(item.newsId)}>
                              <img className={"styleIcon"} src="/icon/share-right.svg" alt={""}/> Chia sẻ
                            </Button>
                          </ButtonGroup>
                        </div>

                        <hr/>
                        {
                          renderComment && indexNews === item.newsId ?
                              <React.Fragment>
                                <div className="input-comment" style={{paddingBottom:'10px'}}>
                                  <a className="btn-user">
                                    <img
                                        src={'/icon/icons8-checked_user_male.png'}
                                        className="rounded-circle icon-user"
                                        alt="Username"/>
                                  </a>{' '}
                                  <p style={{borderRadius: '30px', width:'470px', padding: '10px',
                                    backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}}>
                                    <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2'}}>{"Other User "}</p>
                                    {this.state.commentPosted}
                                  </p>
                                </div>
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
                          <button style={{border:'none', outline:'none'}}>
                            <img className={"responsive"} src={"/icon/iconfinder_picture.svg"} style={{width:'36px'}} alt={""}/>
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