// import React from 'react';
// import ButtonGroup from "reactstrap/es/ButtonGroup";
// import "react-image-gallery/styles/scss/image-gallery.scss";
// import "react-image-gallery/styles/css/image-gallery.css";
// import './_listCardItem.css';
//
// class NewsItemPost extends React.PureComponent {
//   constructor(props) {
//     super(props);
//
//     this.state = {
//       createNewsPost: null,
//       currentUser: null
//     }
//   }
//
//   render() {
//     const { createNewsPost, currentUser } = this.state;
//     return (
//         <Card className="card">
//           <CardTitle>
//             <div className="row"
//                  style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
//               <div className="col-md-9">
//                 <a className="btn-circle btn-lg">
//                   <img
//                       src={createNewsPost.imageUser ? createNewsPost.imageUser
//                           : '/icon/default.jpg'}
//                       className="rounded-circle img-profile"
//                       alt="Username"/>
//                 </a>{' '}
//                 <a href={createNewsPost.imageUser ? createNewsPost.imageUser
//                     : '/icon/default.jpg'}
//                    className="username"
//                 >
//                   <strong>{createNewsPost.username ? createNewsPost.username
//                       : 'username'}</strong>
//                 </a>
//
//                 {/*pub Date*/}
//                 <div style={{color: '#606770', margin: '0 70px'}}>
//                   {createNewsPost.pubDate ? createNewsPost.pubDate : ''}
//                 </div>
//               </div>
//               <div className="col-md-3">
//                 <div className="dropdown float-right">
//                   <button className="btn border-none-outline"
//                           type="button" id="dropdownMenuButton"
//                           data-toggle="dropdown" aria-haspopup="true"
//                           aria-expanded="false">
//                     <img src="/icon/menu-5.svg" style={{width:'23px',height:'23px'}}/>
//                   </button>
//                   <div className="dropdown-menu"
//                        aria-labelledby="dropdownMenuButton">
//                     <a className="dropdown-item">
//                       <i className="far fa-eye-slash"/> Ẩn bài viết
//                     </a>
//                     <a className="dropdown-item">
//                       <i className="far fa-save"/> Lưu bài viết
//                     </a>
//                     <a className="dropdown-item">
//                       <i className="far fa-flag"/> Gửi phản hồi
//                     </a>
//                   </div>
//                 </div>
//               </div>
//             </div>
//           </CardTitle>
//
//           <div className="row"
//                style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
//             <p className={"styleTitle"}>
//               {createNewsPost.titleNews ? createNewsPost.titleNews : null}
//             </p>
//             <p className={"styleText"}>
//               <strong>Giá: </strong>{createNewsPost.price ? createNewsPost.price : null}
//             </p>
//             <p className={"styleText"}>
//               <strong>Diện tích: </strong>{createNewsPost.area ? createNewsPost.area : null}
//             </p>
//             <p className={"styleText"}>
//               <strong>Địa chỉ: </strong>{createNewsPost.address_prop ? createNewsPost.address_prop
//                 : null}
//             </p>
//             <p className={"styleTitle"}>
//               {createNewsPost.summaryNews ? createNewsPost.summaryNews : null}
//             </p>
//
//             <a style={{
//               fontSize: '16px',
//               fontWeight: 'normal',
//               lineHeight: '1.58',
//               fontFamily: 'inherit',
//               marginBottom: '10px',
//               paddingRight: '5px',
//               color: '#20a8d8'
//             }}
//                onClick={()=>this.handleRenderNewsDetail(createNewsPost.newsId)}>
//               {textOfReadMore}
//             </a>
//             {
//               newsDetail && indexNews === createNewsPost.newsId ?
//                   <p className={"styleTitle"}>
//                     {createNewsPost.descriptionNews ? createNewsPost.descriptionNews : null}
//                   </p> : null
//             }
//
//             <p className={"styleTitle"}>
//               <strong>Liên hệ: </strong>{' '}
//               {createNewsPost.contactName ? createNewsPost.contactName : null}
//               {createNewsPost.contactPhone ? createNewsPost.contactPhone : null}
//               {createNewsPost.contactEmail ? createNewsPost.contactEmail : null}
//             </p>
//             <p className={"styleTitle"}>
//               {createNewsPost.projectName ? createNewsPost.projectName : null}
//               {createNewsPost.projectOwner ? createNewsPost.projectOwner : null}
//               {createNewsPost.projectSize ? createNewsPost.projectSize : null}
//             </p>
//           </div>
//
//           <div style={{marginBottom: '10px'}}>
//             {
//               createNewsPost.imageUrlList && createNewsPost.imageUrlList.length ? this.handleRenderImageSlide(createNewsPost.imageUrlList) : null
//             }
//           </div>
//
//           {/*Luot like luot share o day*/}
//           <div style={{margin: '0 20px'}}>
//             <a className="amount-like-share" style={{color: '#606770'}}>
//               <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
//               <img className={"styleIcon"} src="/icon/heart.svg"/>
//               {createNewsPost.likeAmount ? createNewsPost.likeAmount : 0}
//             </a>
//             <a className="float-right amount-like-share"
//                style={{marginLeft: '10px',color: '#606770'}}>
//               {createNewsPost.shareList ? createNewsPost.shareList.length : 0} lượt share
//             </a>
//             <a className="float-right amount-like-share"
//                onClick={()=>this.handleRenderComment(createNewsPost.newsId)}
//                style={{color: '#606770'}}
//             >
//               {createNewsPost.commentList ? createNewsPost.commentList.length : 0} comment
//             </a>
//           </div>
//
//           <hr style={{margin: '5px 20px'}}/>
//
//           <div>
//             <ButtonGroup style={{width: '100%', padding: '0 20px'}}>
//               <Button
//                   className="border-none-outline btn-like-share-comment"
//                   style={activeLike && indexNews.includes(createNewsPost.newsId) ?
//                       {backgroundColor:'#20a8d8', color:'white'} : {}}
//                   onClick={() => this.handleLikePost(createNewsPost.newsId)}>
//                 <img className={"styleIcon"} src="/icon/thumb-up.svg" alt={""}/> Thích
//               </Button>
//               <Button
//                   className="border-none-outline btn-like-share-comment"
//                   style={renderComment && indexNews === createNewsPost.newsId ?
//                       {backgroundColor:'#20a8d8', color:'white'} : {}}
//                   onClick={() => this.handleRenderComment(createNewsPost.newsId)}>
//                 <img className={"styleIcon"} src="/icon/a-chat.svg" alt={""}/> Bình luận
//               </Button>
//               <Button
//                   className="border-none-outline btn-like-share-comment"
//                   style={activeShare && indexNews === createNewsPost.newsId ?
//                       {backgroundColor:'#20a8d8', color:'white'} : {}}
//                   onClick={() => this.handleSharePost(createNewsPost.newsId)}>
//                 <img className={"styleIcon"} src="/icon/share-right.svg" alt={""}/> Chia sẻ
//               </Button>
//             </ButtonGroup>
//           </div>
//
//           <hr/>
//           {
//             renderComment && indexNews === createNewsPost.newsId ?
//                 <React.Fragment>
//                   <div className="input-comment" style={{paddingBottom:'10px'}}>
//                     <a className="btn-user">
//                       <img
//                           src={'/icon/icons8-checked_user_male.png'}
//                           className="rounded-circle icon-user"
//                           alt="Username"/>
//                     </a>{' '}
//                     <p style={{borderRadius: '30px', width:'470px', padding: '10px',
//                       backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px',marginBottom:'0'}}>
//                       <p style={{fontSize:'16px',fontWeight:'500', color:'#4267B2'}}>{"Other User "}</p>
//                       {this.state.commentPosted}
//                     </p>
//                   </div>
//                 </React.Fragment>
//                 : null
//           }
//
//           <div className="input-comment">
//             <a className="btn-user">
//               <img
//                   src={currentUser && currentUser.imageUrl
//                       ? currentUser.imageUrl
//                       : '/icon/default.jpg'}
//                   className="rounded-circle icon-user"
//                   alt="Username"/>
//             </a>{' '}
//             <Input style={{borderRadius: '36px', height: '40px',
//               border:'1px solid #bbc0c4',
//               backgroundColor: '#f2f3f5',textIdent:'32px',fontSize:'16px'}}
//                    placeholder="Viết bình luận..."
//                    value={this.state.comment}
//                    onChange={(e) => this.setState(
//                        {comment: e.target.value})}
//                    onKeyDown={(e)=>{if (e.key === "Enter") {
//                      this.handleCommentPost(createNewsPost.newsId)
//                    }}}
//             />
//             <button style={{border:'none', outline:'none'}}>
//               <img className={"responsive"} src={"/icon/iconfinder_ins.svg"} style={{width:'34px'}} alt={""}/>
//             </button>
//             <button style={{border:'none', outline:'none'}}>
//               <img className={"responsive"} src={"/icon/iconfinder_picture.svg"} style={{width:'36px'}} alt={""}/>
//             </button>
//           </div>
//         </Card>
//     );
//   }
//
// }
//
// export default NewsItemPost;