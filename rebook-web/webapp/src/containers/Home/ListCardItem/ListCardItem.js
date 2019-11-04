import React, {Component} from 'react';
import {Button, Card, CardImg, CardTitle, Input} from "reactstrap";
import ButtonGroup from "reactstrap/es/ButtonGroup";
import shallowCompare from 'react-addons-shallow-compare';
import ImageGallery from 'react-image-gallery';
import "react-image-gallery/styles/scss/image-gallery.scss";
import "react-image-gallery/styles/css/image-gallery.css";
import {commentNews, likeNews, shareNews} from "../../../api/userCallApi";
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';

class ListCardItem extends Component{
  constructor(props) {
    super(props);
    this.state = {
      comment: "",
      newsDetail: false,
      textOfReadMore: "Chi tiết",
      currentUser: null,
      indexNews: 0,
      allNewsItem: null,
      renderComment: false,
      likePosted: null,
      activeLike: false,
      sharePosted: null,
      activeShare: false,
      isLike: false,
      isShare: false,
    }
  }

  componentWillMount() {
    const {currentUser, allNewsItem} = this.props;
    this.setState({
      currentUser: currentUser,
      allNewsItem: allNewsItem
    })
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({
        currentUser: nextProps.currentUser,
        allNewsItem: nextProps.allNewsItem
      })
    }
  }

  handleRenderNewsDetail = (index) => {
    this.setState({
      indexNews: index,
      newsDetail: !this.state.newsDetail
    },() => {
      let {newsDetail} = this.state;
      if (newsDetail === true && this.state.indexNews === index) {
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
          original: i.imageUrl,
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
      isLike: !this.state.isLike,
      userId: currentUser ? currentUser.userId : '',
      newsItemId: newsId ? newsId : '',
    };
    //Api Like Post
    likeNews(requestParams).then(res => {
      this.setState({
        likePosted: res.result,
        indexNews: newsId,
        activeLike: !this.state.activeLike
      }, ()=> {
        //todo:
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
    //Api Comment Post
    commentNews(requestParams).then(res => {
      this.setState({
        commentPosted: res.result
      }, ()=> {
        this.setState({
          comment: ""
        })
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
    //Api Share Post
    shareNews(requestParams).then(res => {
      this.setState({
        sharePosted: res.result,
        indexNews: newsId,
        activeShare: !this.state.activeShare
      })
    }).catch(
        (e)=>console.log(e))
  };

  render() {
    const {allNewsItem, newsDetail, textOfReadMore, currentUser, indexNews,
      renderComment, activeLike, activeShare} = this.state;

    const styleText = {
      fontSize: '16px',
      fontWeight: 'normal',
      lineHeight: '1.58',
      fontFamily: 'inherit',
      marginBottom: '10px',
      paddingRight: '5px',
    };

    const styleTitle = {
      fontSize: '16px',
      fontWeight: 'normal',
      lineHeight: '1.58',
      fontFamily: 'inherit',
      marginBottom: '10px'
    };

    const styleIcon = {
      width: '21px',
      height: '21px',
      marginRight: "2px"
    };

    return (
      <React.Fragment>
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
                    <p style={styleTitle}>
                      {item.titleNews ? item.titleNews : null}
                    </p>
                    <p style={styleText}>
                      <strong>Giá: </strong>{item.price ? item.price : null}
                    </p>
                    <p style={styleText}>
                      <strong>Diện tích: </strong>{item.area ? item.area : null}
                    </p>
                    <p style={styleText}>
                      <strong>Địa chỉ: </strong>{item.address_prop ? item.address_prop
                        : null}
                    </p>
                    <p style={styleTitle}>
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
                          <p style={styleTitle}>
                            {item.descriptionNews ? item.descriptionNews : null}
                          </p> : null
                    }

                    <p style={styleTitle}>
                      <strong>Liên hệ: </strong>{' '}
                      {item.contactName ? item.contactName : null}
                      {item.contactPhone ? item.contactPhone : null}
                      {item.contactEmail ? item.contactEmail : null}
                    </p>
                    <p style={styleTitle}>
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
                      <img style={styleIcon} src="/icon/thumb-up.svg"/>
                      <img style={styleIcon} src="/icon/heart.svg"/>
                      {item.likeNewsList ? item.likeNewsList.length : 0}
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
                      {/*<div className="dropup">*/}
                      {/*  /!*<button className="dropbtn border-none-outline">Dropup</button>*!/*/}
                      {/*  <Button*/}
                      {/*      className="dropbtn border-none-outline"*/}
                      {/*      style={activeLike && indexNews === item.newsId ?*/}
                      {/*          {backgroundColor:'#20a8d8', color:'white'} : {}}*/}
                      {/*      onClick={() => this.handleLikePost(item.newsId)}>*/}
                      {/*    <img style={styleIcon} src="/icon/thumb-up.svg" alt={""}/> Thích*/}
                      {/*  </Button>*/}
                      {/*  <div className="dropup-content">*/}
                      {/*    <div style={{display:'flex', padding:'5px'}}>*/}
                      {/*      <a href={"#"}>*/}
                      {/*        <img style={styleIcon} src="/icon/thumb-up.svg" alt={""}/>*/}
                      {/*      </a>*/}
                      {/*      <a href={"#"}>*/}
                      {/*        <img style={styleIcon} src="/icon/heart.svg" alt={""}/>*/}
                      {/*      </a>*/}
                      {/*      <a href={"#"}>*/}
                      {/*        <img style={styleIcon} src="/icon/a-chat.svg" alt={""}/>*/}
                      {/*      </a>*/}
                      {/*    </div>*/}
                      {/*  </div>*/}
                      {/*</div>*/}
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={activeLike && indexNews === item.newsId ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleLikePost(item.newsId)}>
                        <img style={styleIcon} src="/icon/thumb-up.svg" alt={""}/> Thích
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={renderComment && indexNews === item.newsId ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleRenderComment(item.newsId)}>
                        <img style={styleIcon} src="/icon/a-chat.svg" alt={""}/> Bình luận
                      </Button>
                      <Button
                          className="border-none-outline btn-like-share-comment"
                          style={activeShare && indexNews === item.newsId ?
                              {backgroundColor:'#20a8d8', color:'white'} : {}}
                          onClick={() => this.handleSharePost(item.newsId)}>
                        <img style={styleIcon} src="/icon/share-right.svg" alt={""}/> Chia sẻ
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
                              {"Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"
                              + "Bai viet rat hay!!!Bai viet rat hay!!!"
                              + "Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"}
                            </p>
                          </div>
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
                              {"Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!Bai viet rat hay!!!"}
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
                    {/*<button style={{border:'none', outline:'none'}}>*/}
                    {/*  <img className={"responsive"} src={"/icon/iconfinder_face.svg"} style={{width:'34px'}} alt={""}/>*/}
                    {/*</button>*/}
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
    )
  }
}
export default ListCardItem;