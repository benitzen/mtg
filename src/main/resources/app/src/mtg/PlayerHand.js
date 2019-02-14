import React, {PureComponent} from 'react';
import {connect} from 'react-redux'
import Card from './Card'

class OpponentHand extends PureComponent {
  render() {
    return (
      <div id="player-hand">
        {this.props.cards.map((cardInstance) => <Card key={cardInstance.id} name={cardInstance.card.name} />)}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    cards: state.player.hand.cards
  }
}

const mapDispatchToProps = dispatch => {
  return {
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(OpponentHand)