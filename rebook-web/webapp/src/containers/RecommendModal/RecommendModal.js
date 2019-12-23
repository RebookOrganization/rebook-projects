import React, {Component} from "react";
import {Modal, ModalBody, ModalHeader} from "reactstrap";
import Skeleton from 'react-loading-skeleton';

class RecommendModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      recommendModal: false
    }
  }

  componentWillMount() {
    this.setState({recommendModal: this.props.recommendModal})
  }

  componentWillReceiveProps(nextProps) {
    this.setState({recommendModal: nextProps.recommendModal})
  }

  toggleModalRecommendDetail = () =>  {
    if (typeof this.props.toggleModalRecommendDetail === 'function') {
      return this.props.toggleModalRecommendDetail();
    }
  };

  render() {
    return (
        <Modal isOpen={this.state.recommendModal}
               toggle={()=>this.toggleModalRecommendDetail()}
               className={'modal-lg modal-lg-custom ' + this.props.className}
               centered={true}
        >
          <ModalBody>
            <div style={{marginBottom: '15px'}}>
              <Skeleton circle={true} height={50} width={50} />
            </div>
            <Skeleton count={7}/>
          </ModalBody>
        </Modal>
    );
  }
}

export default RecommendModal;