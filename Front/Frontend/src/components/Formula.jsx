import React, {Component} from 'react';
import {Row, Col, Form, Button} from "react-bootstrap";
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

  render () {
    return (
      <Form className={"mt-3"}>
        {this.props.formulas.map((item, key) => {
          console.log(item.id_materie, this.props.currentDiscipline)
          if (item.id_materie === this.props.currentDiscipline.id_materie)
            return (
              <Row>
                <Col xs={10}>
                  <Form.Control type="text"
                                value={item.formula_calcul}
                                placeholder="Enter formula"
                  />
                </Col>
                <Col xs={2}>
                  <Button className={"col"}
                          variant="primary"
                          type="button"
                  >
                    Save
                  </Button>
                </Col>
              </Row>
            )
        })}
      </Form>
    )
  }
}