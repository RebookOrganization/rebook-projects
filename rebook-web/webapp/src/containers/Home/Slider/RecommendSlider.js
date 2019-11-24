import React, { Component } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import {Button, Card, CardBody, CardImg, CardText, Col, Row} from "reactstrap";
import "./_slider.css";

class RecommendSlider extends Component {
  render() {
    const settings = {
      className: "center",
      centerMode: true,
      infinite: true,
      centerPadding: "60px",
      slidesToShow: 3,
      speed: 500,
      focusOnSelect: true
    };
    return (
        <Card style={{padding: '10px'}}>
          <Row style={{marginBottom:'10px'}}>
            <Col xs={12} sm={8}>
              <h5 style={{color:"#666"}}>Suggested for you</h5>
            </Col>
            <Col xs={12} sm={4}>
              <img src="/icon/menu-5.svg" style={{float:'right', marginTop:'8px'}} alt={""}/>
            </Col>
          </Row>
          <Slider {...settings} style={{zIndex:'10', height:'255px'}}>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://icdn.dantri.com.vn/thumb_w/640/2019/10/15/295620180519133750-d-416-1571122505106.jpg"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://image.theleader.vn/upload/ngocson/2018/1/9/phoi-canh-cua-chung-cu-hinode-city.png"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://gdb.voanews.com/123D9A14-A32B-491D-A0CD-BC0DD6881C2E_cx0_cy4_cw0_w1023_r1_s.jpg"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://media.ex-cdn.com/EXP/media.nhadautu.vn/files/phandinhchinh/2018/02/06/bat-dong-1104.jpg"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://image.theleader.vn/upload/ngocson/2018/1/9/phoi-canh-cua-chung-cu-hinode-city.png"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
            <div style={{margin:'0 5px'}}>
              <Card style={{backgroundColor:'#E9EBEE'}}>
                <CardImg top width="100%"
                         src="https://img.vietnamfinance.vn/thumbs/700x0/upload/news/dinhtinh/2019/1/27/bat-dong-san-thang-ngau-reatimes-1543985111.jpg"
                         alt="Card image cap"/>
                <CardBody>
                  <CardText><strong>Recommend for you</strong></CardText>
                  <Button color={"info"} style={{width:'100%', border:'1px solid #666'}}>
                    <i className="fa fa-plus"/> Chi tiết
                  </Button>
                </CardBody>
              </Card>
            </div>
          </Slider>
        </Card>
    );
  }
}

export default RecommendSlider