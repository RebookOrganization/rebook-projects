import React, {Component} from "react";
import './_listCardItem.css';
import '../_home.css';
import {CardTitle} from "reactstrap";

class NewsHeader extends Component{
  constructor(props) {
    super(props);
    this.state = {}
  }

  handleHidePost = (newsId) => {
    if (typeof this.props.handleHidePost === 'function') {
      return this.props.handleHidePost(newsId)
    }
  };

  render() {
    const {newsItem} = this.props;
    return (
        <CardTitle>
          <div className="row"
               style={{display: 'flex', alignItems: 'center', marginTop: '12px'}}>
            <div className="col-md-9">
              <a className="btn-circle btn-lg">
                <img
                    src={newsItem.imageUser ? newsItem.imageUser
                        : '/icon/default.jpg'}
                    className="rounded-circle img-profile"
                    alt="Username"/>
              </a>{' '}
              <a href={newsItem.imageUser ? newsItem.imageUser
                  : '/icon/default.jpg'}
                 className="username"
              >
                <strong>{newsItem.username ? newsItem.username
                    : 'username'}</strong>
              </a>

              {/*pub Date*/}
              <div style={{color: '#606770', margin: '0 70px'}}>
                {newsItem.pubDate ? newsItem.pubDate : ''}
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
                  <a className="dropdown-item" onClick={()=>this.handleHidePost(newsItem.newsId)}>
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
    );
  }
}

export default NewsHeader;