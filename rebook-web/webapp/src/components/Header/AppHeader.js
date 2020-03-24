import React, {Component} from 'react';
import {Link, NavLink} from 'react-router-dom';
import './_appHeader.css';
import '../../containers/Home/_home.css';
import LoadingIndicator from "../Loading/LoadingIndicator";
import InfiniteScroll from "react-infinite-scroller";

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

class AppHeader extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: this.props.currentUser ? this.props.currentUser : null,
      isSearch: false,
      loading: false,
      items: 15
    }
  }

  toggleModalSearch = (e) => {
    e.preventDefault();
    if (typeof this.props.toggleModalSearch === 'function') {
      return this.props.toggleModalSearch()
    }
  };

  loadMore() {
    if (this.state.items === 100) {
      this.setState({ hasMoreItems: false});
    }
    else {
      setTimeout(() => {
        this.setState({ items: this.state.items + 20});
      }, 2000);
    }
  };

  showItems() {
    let items = [];
    const styleChat = {
      display: 'flex',
      alignItems: 'center',
      padding: '0',
      maxHeight:'56px'
    };

    const dot = {
      height: '8px',
      width: '8px',
      backgroundColor: '#4dbd74',
      borderRadius: '50%',
      display: 'inline-block',
      marginLeft: '300px'
    };

    for (let i = 0; i < this.state.items; i++) {
      items.push(
        <React.Fragment key={i}>
          <a className="dropdown-item">
            <div style={styleChat}>
              <div className="btn-user">
                <img src={'assets/img/avatars/' + getRandomInt(9) + '.jpg'}
                     className="rounded-circle icon-user"
                     alt="Username"/>
              </div>{' '}
              <NavLink tag={"a"} to={"/message"} style={{fontSize:'15px',color:'black'}}>User chat {i}</NavLink>
              <span className={"pull-right"} style={dot}/>
            </div>
          </a>
          <hr/>
        </React.Fragment>
      );
    }
    return items;
  };

  reloadHomePage = () => {
    if (window.location.pathname === "/home") {
      window.location.reload();
    }
    else {
      window.location.replace("/home");
    }
  };

  render() {
    const {currentUser} = this.state;

    return (
        <header className="app-header">
          {
            this.state.loading ? <LoadingIndicator/> : null
          }
          <div className="container-fluid" style={{paddingLeft: "40px"}}>
            <div className="row">
              <div className="col-md-5 app-branding">
                <a onClick={()=>this.reloadHomePage()}
                        className="app-title">
                  <img src="/assets/brand/sygnet.svg" style={{
                    width: '40px',
                    height: '40px',
                    // marginRight: '20px'
                  }}/>
                </a>
                <div className="input-group input-group-sm"
                   style={{marginLeft:'20px', width:'350px'}}>
                  <input type="text"
                         className="form-control"
                         placeholder="Tìm kiếm"
                         style={{fontSize:'16px',height:'34px'}}
                         onClick={(e)=>this.toggleModalSearch(e)}
                         aria-label="Tìm kiếm"
                         aria-describedby="basic-addon2"
                  />
                  <div className="input-group-append">
                    <span className="input-group-text"
                          style={{width:'50px'}}
                          id="basic-addon2">
                      <span className="fa fa-search" style={{color:'gray',marginLeft:'10px'}}/>
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-md-5 app-options">
                <nav className="app-nav">
                  <ul>
                    <li className="li-profile">
                      <img
                          src={currentUser && currentUser.imageUrl
                              ? currentUser.imageUrl
                              : '/icon/default.jpg'}
                          className="rounded-circle icon-profile"
                          alt="Username"/>
                      <NavLink to={{
                                  pathname: "/profile",
                                  state: {currentUser},
                                  search: currentUser ? "?userid=" + currentUser.userId + "&name=" + currentUser.name : ""
                                }}
                               style={{paddingLeft: "10px"}}>
                        {currentUser ? currentUser.name : "username"}
                      </NavLink>
                    </li>
                    <li>
                      <NavLink to="/home">Trang chủ</NavLink>
                    </li>
                    <li>
                      {/*<NavLink to="/message">Tin Nhắn</NavLink>*/}
                      <div className="dropdown message">
                        <button className="btn border-none-outline"
                                type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true"
                                style={{fontSize:'16px', fontWeight:'500',
                                  color:'white', padding:'0 5px'}}
                                aria-expanded="false">
                          <img className={"responsive"}
                               src="/icon/iconfinder_messenger.svg"
                               style={{width:'34px'}} alt={""}/>
                          {/*Tin Nhắn*/}
                        </button>
                        <div className="dropdown-menu dropdown-menu-center">
                          <h6 style={{color:'#616770', paddingLeft:'10px'}}>Bắt đầu trò chuyện</h6>
                          <hr/>
                          <div style={{height:'500px', overflow:'auto'}}>
                            <InfiniteScroll
                                loadMore={this.loadMore.bind(this)}
                                hasMore={this.state.hasMoreItems}
                                loader={<div className="loader" key={""}><strong> Loading... </strong></div>}
                                useWindow={false}>
                              {this.showItems()}{" "}
                            </InfiniteScroll>
                          </div>
                          <hr/>
                          <a className="dropdown-item">
                            <i className="far fa-flag"/> Đánh dấu đã đọc
                          </a>
                        </div>
                      </div>
                    </li>
                    {/*<li>*/}
                    {/*  <div className="dropdown message">*/}
                    {/*    <button className="btn border-none-outline"*/}
                    {/*            type="button" id="dropdownMenuButton"*/}
                    {/*            data-toggle="dropdown" aria-haspopup="true"*/}
                    {/*            style={{fontSize:'16px', fontWeight:'500',*/}
                    {/*              color:'white', padding:'0 5px'}}*/}
                    {/*            aria-expanded="false">*/}
                    {/*      <img className={"responsive"}*/}
                    {/*           src="/icon/alarm.svg"*/}
                    {/*           style={{width:'29px',height:'25px'}} alt={""}/>*/}
                    {/*    </button>*/}
                    {/*    <div className="dropdown-menu dropdown-menu-center"*/}
                    {/*         style={{width:'400px'}}*/}
                    {/*    >*/}
                    {/*      <a className="dropdown-item">*/}
                    {/*        <i className="far fa-save"/> Lưu bài viết*/}
                    {/*      </a>*/}
                    {/*      <hr/>*/}
                    {/*      <a className="dropdown-item">*/}
                    {/*        <i className="far fa-flag"/> Gửi phản hồi*/}
                    {/*      </a>*/}
                    {/*    </div>*/}
                    {/*  </div>*/}
                    {/*</li>*/}
                    <li>
                      <a href="/logout">Logout</a>
                    </li>
                  </ul>
                </nav>
              </div>
              <div className="col-md-2">
              </div>
            </div>
          </div>

        </header>
    )
  }
}

export default AppHeader;