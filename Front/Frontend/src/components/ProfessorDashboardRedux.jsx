import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {getGlobal, getProfessorCatalog} from "../actions/actions";
import {Container, Row, Col} from "react-bootstrap";
import Formula from "./Formula";
import NavProf from "./NavProf";

class ProfessorDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
        this.props.getGlobal()
        this.props.getProfessorCatalog()
    }

    render() {
        return (
            <div>
                <NavProf/>
                <Catalog rows={this.props.rows} columns={this.props.columns}/>
                <Formula/>
            </div>
        );
    }
}

export const ProfessorDashboardRedux = connect((state) => ({
    global: state.global,
    columns: state.columns,
    rows: state.rows,
    loading: state.loading
}), {
    getGlobal, getProfessorCatalog
})(ProfessorDashboard)


