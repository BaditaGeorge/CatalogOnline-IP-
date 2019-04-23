import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
    getGlobal,
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertDisciplineFormulas
} from "../actions/actions";
import Formula from "./Formula";
import NavProf from "./NavProf";

class ProfessorDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
        this.props.getGlobal()
        this.props.getProfessorCatalog(7, 1)
        this.props.getProfessorDisciplines(1)
        this.props.getDisciplineFormulas(10)
        // this.props.insertDisciplineFormulas({
        //     "id_materie": "10",
        //     "formule": "L1-L2"
        // })
    }

    render() {
        console.log(this.props.disciplines);
        return (
            <div>
                <NavProf disciplines={this.props.disciplines}/>
                <Catalog rows={this.props.rows} columns={this.props.columns}/>
                <Formula formula={this.props.formulas}/>
            </div>
        );
    }
}

export const ProfessorDashboardRedux = connect((state) => ({
    global: state.global,
    columns: state.columns,
    disciplines: state.disciplines,
    formulas: state.formulas,
    rows: state.rows,
    loading: state.loading
}), {
    getGlobal, getProfessorCatalog, getProfessorDisciplines, getDisciplineFormulas, insertDisciplineFormulas
})(ProfessorDashboard)


