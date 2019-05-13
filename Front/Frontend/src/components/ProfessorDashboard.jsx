import React, { Component, Fragment } from 'react';
import '../App.css';
import { connect } from "react-redux";
import equal from "fast-deep-equal"
import Catalog from "./Catalog"
import {
  getProfessorCatalog,
  getProfessorDisciplines,
  getDisciplineFormulas,
  insertProfessorCatalog,
  insertProfessorDisciplines,
  insertDisciplineFormulas,
  setDefaultDiscipline,
} from "../actions/professorActions";
import Formula from "./Formula";
import NavProf from "./NavProf";

const user = { name: "Alex Ivan", role: "professor", id_prof: 1 }


class ProfessorDashboard extends Component {
  constructor (props) {
    super(props)
  }

  componentWillMount () {
    this.props.getProfessorDisciplines(user.id_prof)
    this.props.getDisciplineFormulas(user.id_prof)
  }

  componentWillUpdate (nextProps, nextState, nextContext) {
    if (nextProps.currentDiscipline && (!nextProps.rows.length || !nextProps.columns.length)) {
      this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, user.id_prof)
    }

    if (!equal(nextProps.currentDiscipline, this.props.currentDiscipline) && (nextProps.rows.length || nextProps.columns.length)) {
      this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, user.id_prof)
    }
  }

  // componentDidUpdate(prevProps) {
  //     if (!equal(prevProps.currentDiscipline, this.props.currentDiscipline)) {
  //         this.props.getProfessorCatalog(this.props.currentDiscipline.id_materie, user.id_prof)
  //         this.setState({currentDiscipline: this.props.currentDiscipline})
  //     }
  //     if (!equal(prevProps.disciplines, this.props.disciplines)) {
  //         this.setState({disciplines: this.props.disciplines})
  //     }
  //     if (!equal(prevProps.rows, this.props.rows)) {
  //         this.setState({rows: this.props.rows})
  //     }
  //     if (!equal(prevProps.columns, this.props.columns)) {
  //         this.setState({columns: this.props.columns})
  //     }
  //     if (!equal(prevProps.formulas, this.props.formulas)) {
  //         this.props.formulas.map((item) => {
  //             console.log(item, this.state.currentDiscipline.id_materie.replace(' ', ''))
  //             if(item.id_materie === parseInt(this.state.currentDiscipline.id_materie, 10))
  //                 this.setState({formulas: item.formula_calcul})
  //         })
  //     }
  // }


  render () {
    if (!this.props.loading)
      return (
        <Fragment>
          <NavProf user={user}
                   disciplines={this.props.disciplines}
                   currentDiscipline={this.props.currentDiscipline}
                   onAddDiscipline={this.props.insertProfessorDisciplines}
                   onDisciplineChange={this.props.setDefaultDiscipline}
          />
          <Catalog user={user}
                   rows={this.props.rows}
                   columns={this.props.columns}
          />
          <Formula formulas={this.props.formulas}
                   currentDiscipline={this.props.currentDiscipline}
                   onAddFormulas={this.props.insertDisciplineFormulas}
          />
        </Fragment>
      );
    return <div>Loading</div>
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
  insertProfessorDisciplines,
  setDefaultDiscipline,
})(ProfessorDashboard)


