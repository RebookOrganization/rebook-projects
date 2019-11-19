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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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
                         src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
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