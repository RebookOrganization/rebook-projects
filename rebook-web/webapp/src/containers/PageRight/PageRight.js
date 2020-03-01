import React, {Component} from 'react';
import '../Home/_home.css';
import { Card, CardBody, CardImg, CardText } from "reactstrap";
import 'ladda/dist/ladda-themeless.min.css';
import "./_pageRight.css";


class PageRight extends Component {
  constructor(props) {
    super(props);

    this.state = {
      inputSearch: '',
      inputSearchType: 1,
      loading: false,
      resultSearchAddress: null,
      resultSearchUser: null,
      allNewsItem: null,

      collapseSearch: false,
      listRecommend: []
    };

  }

  componentWillMount() {
    this.setState({
      listRecommend: this.props.listRecommend
    })
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      listRecommend: nextProps.listRecommend
    })
  }

  componentWillUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  }

  componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
  }

  handleScroll = () => {
    let pageRight = document.getElementById("page-right");
    let copyright = document.getElementById("Copyright");
    if (window.pageYOffset > copyright.offsetTop) {
      pageRight.classList.add("sticky-page-right")
    }
    else {
      pageRight.classList.remove("sticky-page-right")
    }
  };

  toggleCollapse = () => {
    this.setState({collapseSearch: !this.state.collapseSearch})
  };

  toggleModalRecommendDetail = (item) =>  {
    if (typeof this.props.toggleModalRecommendDetail === 'function') {
      return this.props.toggleModalRecommendDetail(item);
    }
  };

  render() {
    const {listRecommend} = this.state;
    return (
        <div id={"page-right"}>
          {
            listRecommend.length ?
                listRecommend.slice(6, 9).map((item, index) => {
                  return (
                      <Card key={index}>
                        {
                          index === 0 ?
                              <CardBody>
                                <strong style={{color: '#4b4f56'}}>Bất động sản được gợi
                                  ý</strong>
                              </CardBody> : null
                        }
                        <CardImg top width="100%"
                                 src={item.imageUrlList.length ? item.imageUrlList[0].imageUrl.replace("/resize/200x200", "")
                                     : 'https://image.theleader.vn/upload/ngocson/2018/1/9/phoi-canh-cua-chung-cu-hinode-city.png'}
                                 alt="Card image cap"/>
                        <CardBody>
                          <CardText>{item.descriptionNews ? item.descriptionNews.substring(0,50) + "..." : "Recommend for you"}</CardText>
                          <button className={"pull-right"} onClick={()=>this.toggleModalRecommendDetail(item)}>
                            <i className="fa fa-plus"/> Chi tiết
                          </button>
                        </CardBody>
                      </Card>
                  )
                }) :
                <React.Fragment>
                  <Card>
                    <CardBody>
                      <strong style={{color: '#4b4f56'}}>Bất động sản được gợi
                        ý</strong>
                    </CardBody>
                    <CardImg top width="100%"
                             src="https://www.ngoisaoso.vn/uploads/news/2014/02/19/thiet-ke-web-bat-dong-san-2.jpg"
                             alt="Card image cap"/>
                    <CardBody>
                      <CardText>With supporting text below as a natural lead-in to
                        additional content.</CardText>
                      <button className={"pull-right"} onClick={()=>this.toggleModalRecommendDetail()}>
                        <i className="fa fa-plus"/> Chi tiết
                      </button>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardImg top width="100%"
                             src="https://www.worldatlas.com/r/w728-h425-c728x425/upload/31/4c/3d/beautiful-new-zealand.jpg"
                             alt="Card image cap"/>
                    <CardBody>
                      <CardText>With supporting text below as a natural lead-in to
                        additional content.</CardText>
                      <button className={"pull-right"} onClick={()=>this.toggleModalRecommendDetail()}>
                        <i className="fa fa-plus"/> Chi tiết
                      </button>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardImg top width="100%"
                             src="http://image.english.vov.vn/h500/uploaded/tmt2b47lhgly8uzveukg/2017_10_10/h_1_VEWM.jpg"
                             alt="Card image cap"/>
                    <CardBody>
                      <CardText><strong>Recommend for you</strong></CardText>
                      <button className={"pull-right"} onClick={()=>this.toggleModalRecommendDetail()}>
                        <i className="fa fa-plus"/> Chi tiết
                      </button>
                    </CardBody>
                  </Card>
                </React.Fragment>
          }

          <Card
              style={{display: 'flex', flexDirection: 'row', padding: '10px'}}>
            <CardText style={{marginRight: '20px'}}>Tiếng Việt.</CardText>
            <CardText style={{marginRight: '20px'}}>English.</CardText>
            <button className={"pull-right"}>
              <i className="fa fa-plus"/> Thêm
            </button>
          </Card>
          <div style={{display: 'flex'}}>
            <a href="https://mdbootstrap.com/education/bootstrap/"
               style={{color: '#616770', marginRight: '10px'}}>Điều khoản.</a>
            <a href="https://mdbootstrap.com/education/bootstrap/"
               style={{color: '#616770'}}>Quảng cáo.</a>
          </div>
          <span id={"Copyright"}
              style={{color: '#616770'}}>© 2019 Copyright: Rebook.com.vn</span>
        </div>
    )
  }
}

export default PageRight;