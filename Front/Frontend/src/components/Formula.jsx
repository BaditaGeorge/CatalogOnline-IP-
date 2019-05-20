import React, { Component, Fragment } from 'react';
import equal from "fast-deep-equal"
import { Button } from "react-bootstrap";
import "../css/Formula.css";

export default class Formula extends Component {
  constructor (props) {
    super(props);
    this.state = {
      formulas: []
    }
  }

  componentWillReceiveProps (nextProps, nextContext) {
    this.setState({ formulas: nextProps.formulas })
  }

  askUserInput = (message, defaultInput) => {
    const result = prompt(message);
    if (result === "") {
      return defaultInput
    } else if (result) {
      return result
    } else {
      return defaultInput
    }
  };

  onChangeFormula () {
    const formula = this.askUserInput('Please specify a new formula (ex: L1 + L2)', '')
    if (formula !== '')
      this.props.onChangeFormula(this.props.currentDiscipline.id_materie, formula)
  }

  componentWillUpdate (nextProps, nextState, nextContext) {
    if (!equal(nextProps.formulas, this.props.formulas))
      this.setState({ formulas: nextProps.formulas })
  }

  render () {
    return (
      <div className={'formula'}>
        {this.props.formulas.map((item, key) => {
          if (item.id_materie === this.props.currentDiscipline.id_materie) {
            return (
              <div className={'col'} key={key}>
                <h4>{item.formula_calcul}</h4>
                <Button variant="primary"
                        type="button"
                        onClick={() => this.onChangeFormula()}
                >
                  Edit formula
                </Button>
              </div>
            )
          }
        })}
      </div>
    )
  }
}