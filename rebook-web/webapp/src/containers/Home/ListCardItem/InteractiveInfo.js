import React, {Component} from 'react';
import './_listCardItem.css';
import '../_home.css';
import shallowCompare from "react-addons-shallow-compare";

class InteractiveInfo extends Component {
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
      this.setState({newsItem: nextProps.newsItem})
    }
  }

  handleRenderComment = (newsId) => {
    if (typeof this.props.handleRenderComment === 'function') {
      return this.props.handleRenderComment(newsId);
    }
  };

  render() {
    const {newsItem} = this.state;
    return (
        <div style={{margin: '0 20px'}}>
          <a className="amount-like-share" style={{color: '#606770'}}>
            <img className={"styleIcon"} src="/icon/thumb-up.svg"/>
            <img className={"styleIcon"} src="/icon/heart.svg"/>
            {newsItem.likeAmount}
          </a>
          <a className="float-right amount-like-share"
             style={{marginLeft: '10px',color: '#606770'}}>
            {newsItem.shareAmount} lượt share
          </a>
          <a className="float-right amount-like-share"
             onClick={()=>this.handleRenderComment(newsItem.newsId)}
             style={{color: '#606770'}}
          >
            {newsItem.commentList.length} comment
          </a>
        </div>
    );
  }

}

export default InteractiveInfo;