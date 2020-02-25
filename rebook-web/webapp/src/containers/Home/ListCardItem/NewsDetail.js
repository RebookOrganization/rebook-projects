import React, {Component} from "react";
import './_listCardItem.css';
import '../_home.css';
import shallowCompare from 'react-addons-shallow-compare';

class NewsDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {newsItem: this.props.newsItem}
  }

  componentWillMount() {
    const { newsItem } = this.props;
    this.setState({newsItem})
  }

  componentWillReceiveProps(nextProps) {
    if (shallowCompare(this, this.props, nextProps)) {
      this.setState({newsItem: nextProps.newsItem},
          ()=> console.log("newsDetail: " + this.state.newsItem.newsDetail))
    }
  }

  handleRenderNewsDetail = (newsId) => {
    if (typeof this.props.handleRenderNewsDetail === 'function') {
      return this.props.handleRenderNewsDetail(newsId);
    }
  };

  render() {
    const { newsItem } = this.state;
    return (
        <div className="row"
             style={{display: 'flex', alignItems: 'center', marginLeft: '15px', marginRight: '15px'}}>
          <p className={"styleTitle"}>
            {newsItem.titleNews ? newsItem.titleNews : null}
          </p>
          <p className={"styleText"}>
            <strong>Giá: </strong>{newsItem.price ? newsItem.price : null}
          </p>
          <p className={"styleText"}>
            <strong>Diện tích: </strong>{newsItem.area ? newsItem.area : null}
          </p>
          <p className={"styleText"}>
            <strong>Địa chỉ: </strong>{newsItem.address_prop ? newsItem.address_prop
              : null}
          </p>
          <p className={"styleTitle"}>
            {newsItem.summaryNews ? newsItem.summaryNews : null}
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
             onClick={()=>this.handleRenderNewsDetail(newsItem.newsId)}>
            {newsItem.textOfReadMore}
          </a>
          {
            newsItem.newsDetail ?
                <p className={"styleTitle"}>
                  {newsItem.descriptionNews ? newsItem.descriptionNews : null}
                </p> : null
          }

          <p className={"styleTitle"}>
            <strong>Liên hệ: </strong>{' '}
            {newsItem.contactName ? newsItem.contactName : null}
            {newsItem.contactPhone ? newsItem.contactPhone : null}
            {newsItem.contactEmail ? newsItem.contactEmail : null}
          </p>
          <p className={"styleTitle"}>
            {newsItem.projectName ? newsItem.projectName : null}
            {newsItem.projectOwner ? newsItem.projectOwner : null}
            {newsItem.projectSize ? newsItem.projectSize : null}
          </p>
        </div>
    );
  }

}

export default NewsDetail;