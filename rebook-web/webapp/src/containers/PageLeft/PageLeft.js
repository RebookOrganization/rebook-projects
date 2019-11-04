import React, {Component} from 'react';
import "../Home/_home.css";
import "../App/_app.css";
import "./_pageLeft.css";
import {
  Card,
  CardImg,
} from "reactstrap";
import {NavLink} from "react-router-dom";

class PageLeft extends Component{
  constructor(props) {
    super(props);
    this.state = {
      province: 0,
      currentUser: this.props.currentUser
    }
  }

  render() {
    const {currentUser} = this.state;

    return (
        <div className="sticky-top sticky-offset">
          <div className="list-group list-group-mine" style={{marginBottom: '15px'}}>
            <NavLink tag={"a"} className="list-group-item" to={"/profile"}>
              <img src={currentUser && currentUser.imageUrl ?
                  currentUser.imageUrl
                      : '/icon/default.jpg'}
                  className="rounded-circle icon-profile"
                  style={{marginRight:'10px'}}
                  alt="Username"/> {currentUser ? currentUser.name : "username"}
            </NavLink>
            <a className="list-group-item" href="#">
              <img src="/icon/icons8-news.png" alt={""}/> Bảng tin
              <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
            </a>
            <NavLink tag={"a"} className="list-group-item" to={"/message"}>
              {/*<NavLink to={"/message"}>*/}
                <img src="/icon/icons8-message_group.png" alt={""}/> Messenger
              {/*</NavLink>*/}
              <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
            </NavLink>
            <a className="list-group-item" href="#">
              <img src="/icon/icons8-retro_tv.png" alt={""}/> Watch
              <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
            </a>
          </div>
          <h6 style={{color:'#616770', paddingLeft:'10px'}}>Lối tắt</h6>
          <div className="list-group list-group-mine" style={{marginBottom: '15px'}}>
            <a className="list-group-item" href="#">
              <img src="/icon/icons8-group.png" alt={""}/> Nhóm
              <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
            </a>
            <a className="list-group-item" href="#">
              <img src="/icon/icons8-add_user_male.png" alt={""}/> Tạo
              <img src="/icon/menu-5.svg" style={{float:'right'}} alt={""}/>
            </a>
          </div>
          <h6 style={{color:'#616770', paddingLeft:'10px'}}>QR for App Android</h6>
          <Card style={{width:'50%', height:'50%', marginLeft:'10px'}}>
            <CardImg top
                     src="https://www.qr-code-generator.com/wp-content/themes/qr/new_structure/markets/core_market_full/generator/dist/generator/assets/images/websiteQRCode_noFrame.png"
                     alt="Card image cap"/>
          </Card>
        </div>
    )
  }
}

export default PageLeft;