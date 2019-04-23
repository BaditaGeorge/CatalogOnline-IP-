import React, {Component} from 'react';
import {Row, Col, Form, Button} from "react-bootstrap";

export default class Formula extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formulas: []
        }
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
                                      placeholder="Enter formula"
                                      onChange={(e) => this.setState({formulas: [{formula: e.value}]})}
                        />
                    </Col>
                    <Col xs={2}>
                        <Button className={"col"}
                                variant="primary"
                                type="button"
                                onClick={this.props.insertDisciplineFormulas({
                                    id_materie: 10,
                                    formulas: this.props.formulas
                                })}
                        >

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