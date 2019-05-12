import React, {Component, Fragment} from 'react';
import '../App.css';
import {connect} from "react-redux";
import equal from "fast-deep-equal"
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
        const user = {name: "Alex Ivan", role: "professor", id_prof: 1}
        this.props.getProfessorDisciplines(user.id_prof)
        this.props.getProfessorCatalog(7, user.id_prof)
        // this.props.getDisciplineFormulas(5)
        // this.props.insertProfessorCatalog(catalog)
        // this.props.insertDisciplineFormulas(10, "L1asd*L2")
        // this.props.insertProfessorDisciplines(3, 'DSFUM')
    }

    // componentDidUpdate(prevProps) {
    //     if(!equal(this.props, prevProps))
    //     {
    //         this.forceUpdate()
    //     }
    // }

    render() {
        const user = {name: "Alex Ivan", role: "professor", id_prof: 1}
        return (
            <Fragment>
                <NavProf user={user}
                         disciplines={this.props.disciplines}
                         onAddDiscipline={this.props.insertProfessorDisciplines}
                />
                <Catalog user={user}
                         rows={this.props.rows}
                         columns={this.props.columns}
                />
                <Formula formulas={this.props.formulas}
                         onAddFormulas={this.props.insertDisciplineFormulas}
                />
            </Fragment>
        );
    }
}

export const ProfessorDashboardRedux = connect((state) => ({
    global: state.professorReducer.global,
    columns: state.professorReducer.columns,
    disciplines: state.professorReducer.disciplines,
    currentDiscipline: state.professorReducer.currentDiscipline,
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


