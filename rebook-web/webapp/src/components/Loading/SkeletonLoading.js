import React from "react";
import Skeleton from 'react-loading-skeleton';
import {Card} from "reactstrap";

class SkeletonLoading extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Card style={{padding:'15px'}}>
        <div style={{marginBottom: '15px'}}>
          <Skeleton circle={true} height={50} width={50} />
        </div>
        <Skeleton count={5}/>
      </Card>
    );
  }

}

export default SkeletonLoading;