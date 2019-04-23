import React, {Component} from 'react';
import {Row, Col, Form, Button} from "react-bootstrap";

export default class Formula extends Component {
    constructor(props) {
        super(props);
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({formulas: nextProps.formulas})
    }

    render() {
        return (
            <div className={"mt-3"}>
                <Form.Label>Formule</Form.Label>
                <Row>
                    <Col xs={10}>
                        <Form.Control type="text"
                                      value={this.props.formulas && this.props.formulas[0] ? this.props.formulas[0].formula : ''}
                                      placeholder="Enter formula"/>
                    </Col>
                    <Col xs={2}>
                        <Button className={"col"} variant="primary" type="button">
                            Submit
                        </Button>
                    </Col>
                </Row>
                <Form.Text className="text-muted">
                    Ex: sum(L1:L10)/10
                </Form.Text>
            </div>
        )
    }
}