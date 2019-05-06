import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
    getGlobal,
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertDisciplineFormulas,
} from "../actions/actions";
import Formula from "./Formula";
import NavProf from "./NavProf";

class ProfessorDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
        const catalog = {
            "profesor": "1",
            "disciplina": "7",
            "columns": [
                {
                    "key": "student",
                    "type": "text"
                },
                {
                    "key": "id",
                    "type": "text"
                },
                {
                    "key": "group",
                    "type": "text"
                },
                {
                    "key": "L1",
                    "type": "number"
                },
                {
                    "key": "L2",
                    "type": "number"
                },
                {
                    "key": "L3",
                    "type": "number"
                }], "rows": [
                {
                    "id": "1",
                    "student": "Victor Paval",
                    "group": "B1",
                    "L1": "5",
                    "L2": "10",
                    "L3": "15"
                }]
        }
        this.props.getGlobal()
        this.props.getProfessorCatalog(7, 1)
        this.props.getProfessorDisciplines(2)
        this.props.getDisciplineFormulas(7)
    }

    render() {
        console.log(this.props.disciplines)
        return (
            <div>
                <NavProf disciplines={this.props.disciplines}/>
                <Catalog rows={this.props.rows} columns={this.props.columns}/>
                <Formula formulas={this.props.formulas}/>
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
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertDisciplineFormulas,
    insertProfessorCatalog
})(ProfessorDashboard)


