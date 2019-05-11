import React, {Component} from 'react';
import '../App.css';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertProfessorCatalog,
    insertProfessorDisciplines,
    insertDisciplineFormulas
} from "../actions/professorActions";
import Formula from "./Formula";
import NavProf from "./NavProf";

class ProfessorDashboard extends Component {
    constructor(props) {
        super(props)
    }

    componentWillMount() {
        const catalog = {
            "profesor": "1",
            "disciplina": "Proba",
            "columns": [
                {
                    "key": "id",
                    "name": "ID",
                    "type": "text"
                },
                {
                    "key": "student",
                    "name": "Student",
                    "type": "text"
                },
                {
                    "key": "group",
                    "name": "Group",
                    "type": "text"
                },
                {
                    "key": "l1",
                    "name": "L1",
                    "type": "number"
                },
                {
                    "key": "l2",
                    "name": "L2",
                    "type": "number"
                },
                {
                    "key": "l3",
                    "name": "L3",
                    "type": "number"
                },
                {
                    "key": "l4",
                    "name": "L4",
                    "type": "number"
                },
                {
                    "key": "l5",
                    "name": "L6",
                    "type": "number"
                },
                {
                    "key": "presences",
                    "name": "Presences",
                    "type": "number"
                },
                {
                    "key": "exam",
                    "name": "Exam",
                    "type": "number"
                }
            ],
            "rows": [
                {
                    "id": 0,
                    "student": "James Doe 1",
                    "group": "B5",
                    "l1": 1.5,
                    "l2": 4,
                    "l3": 6,
                    "l4": 8,
                    "l5": 10,
                    "l6": 10,
                    "presences": 10,
                    "exam": 10
                },
                {
                    "id": 0,
                    "student": "James Doe 2",
                    "group": "B2",
                    "l1": 2,
                    "l2": 4,
                    "l3": 6,
                    "l4": 8,
                    "l5": 10,
                    "l6": 10,
                    "presences": 10,
                    "exam": 10
                }
            ]
        }
        // this.props.getProfessorCatalog(7, 1)
        // this.props.getProfessorDisciplines(2)
        // this.props.getDisciplineFormulas(7)
        // this.props.insertProfessorCatalog(catalog)
        this.props.insertDisciplineFormulas(10, "L1asd*L2")
        this.props.insertProfessorDisciplines(3, 'DSFUM')
    }

    render() {
        console.log(this.props.formulas)
        const user = {name: "Alex", prenume: "Andrei", role: "professor"}
        return (
            <div>
                <NavProf disciplines={this.props.disciplines}/>
                <Catalog user={user} rows={this.props.rows} columns={this.props.columns}/>
                <Formula formulas={this.props.formulas}/>
            </div>
        );
    }
}

export const ProfessorDashboardRedux = connect((state) => ({
    global: state.professorReducer.global,
    columns: state.professorReducer.columns,
    disciplines: state.professorReducer.disciplines,
    formulas: state.professorReducer.formulas,
    rows: state.professorReducer.rows,
    loading: state.professorReducer.loading
}), {
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertProfessorCatalog,
    insertDisciplineFormulas,
    insertProfessorDisciplines
})(ProfessorDashboard)


