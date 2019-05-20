import React, { Component } from 'react';
import { connect } from "react-redux";
import Catalog from "./Catalog"
import {
  getProfessorCatalog,
  getProfessorDisciplines,
  getDisciplineFormulas,
  insertProfessorCatalog,
  insertProfessorDisciplines,
  insertDisciplineFormulas,
  setDefaultDiscipline,
} from "../actions/adminActions";
import Formula from "./Formula";
import Navigation from "./Navigation";
import { logoutUser } from "../actions/loginActions";


class AdminDashboard extends Component {
  constructor (props) {
    super(props)
  }

  componentWillMount () {
    if (this.props.userId && this.props.token.length  && this.props.token.length) {
      this.props.getProfessorDisciplines(this.props.userId)
      this.props.getDisciplineFormulas(this.props.userId)
    }
  }

  componentWillUpdate (nextProps, nextState, nextContext) {
    if (nextProps.token !== this.props.token && nextProps.userId && nextProps.token.length) {
      if (nextProps.currentDiscipline.id_materie && !nextProps.columns.length) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, this.props.userId)
      }

      if (nextProps.currentDiscipline.id_materie !== this.props.currentDiscipline.id_materie && nextProps.rows.length) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, this.props.userId)
      }

      if (nextProps.didUpdate === true && this.props.currentDiscipline.id_materie) {
        this.props.getProfessorCatalog(this.props.currentDiscipline.id_materie, this.props.userId)

      }
    }
  }

  render () {
    if (!this.props.loading)
      return (
        <div className={'dashboard'}>
          <Navigation user={{ name: this.props.userName, role: this.props.role, userId: this.props.userId }}
                      disciplines={this.props.disciplines}
                      currentDiscipline={this.props.currentDiscipline}
                      onAddDiscipline={this.props.insertProfessorDisciplines}
                      onDisciplineChange={this.props.setDefaultDiscipline}
                      onUserLogout={this.props.logoutUser}
          />
          <Catalog user={{ name: this.props.userName, role: this.props.role, userId: this.props.userId }}
                   currentDiscipline={this.props.currentDiscipline}
                   formulas={this.props.formulas}
                   rows={this.props.rows}
                   columns={this.props.columns}
                   onCatalogChange={this.props.insertProfessorCatalog}
          />
          <Formula formulas={this.props.formulas}
                   currentDiscipline={this.props.currentDiscipline}
                   onChangeFormula={this.props.insertDisciplineFormulas}
          />
        </div>
      );
    return <div>Loading</div>
  }
}

export const AdminDashboardWithRedux = connect((state) => ({
  global: state.professorReducer.global,
  columns: state.professorReducer.columns,
  disciplines: state.professorReducer.disciplines,
  didUpdate: state.professorReducer.didUpdate,
  currentDiscipline: state.professorReducer.currentDiscipline,
  formulas: state.professorReducer.formulas,
  rows: state.professorReducer.rows,
  loading: state.professorReducer.loading,
  userName: state.loginReducer.userName,
  role: state.loginReducer.role,
  toke: state.loginReducer.toke,
  verified: state.loginReducer.verified,
}), {
  getProfessorCatalog,
  getProfessorDisciplines,
  getDisciplineFormulas,
  insertProfessorCatalog,
  insertDisciplineFormulas,
  insertProfessorDisciplines,
  setDefaultDiscipline,
  logoutUser,
})(AdminDashboard)


