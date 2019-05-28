import React, {Component} from 'react';
import '../css/Professor.css';
import {connect} from "react-redux";
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
import {logoutUser} from "../actions/loginActions"
import Formula from "./Formula";
import Navigation from "./Navigation";
import equal from "fast-deep-equal";


class ProfessorDashboard extends Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    if (this.props.token) {
      this.props.getProfessorDisciplines(this.props.userId)
      this.props.getDisciplineFormulas(this.props.userId)
      if (this.props.currentDiscipline.id_materie) {
        this.props.getProfessorCatalog(this.props.currentDiscipline.id_materie, this.props.userId)
      }
    }
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (nextProps.token) {
      if (nextProps.currentDiscipline.id_materie && !equal(nextProps.columns, this.props.columns)) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.userId)
      }

      if (!equal(nextProps.currentDiscipline, this.props.currentDiscipline)) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.userId)
      }

      if (nextProps.currentDiscipline.id_materie && nextProps.didUpdate) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.userId)

      }
    }
  }


  render() {
    console.log(this.props.currentDiscipline)
    if (!this.props.loading) {
      const user = {name: this.props.userName, role: this.props.role, userId: this.props.userId}
      return (
        <div className={'dashboard'}>
          <Navigation user={user} {...this.props}
          />
          <Catalog user={user} {...this.props}/>

          <Formula {...this.props}/>
        </div>
      );
    }
    return <div>Loading</div>
  }
}

export const ProfessorDashboardRedux = connect(
  (state) => ({...state.professorReducer, ...state.loginReducer}),
  {
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    insertProfessorCatalog,
    insertDisciplineFormulas,
    insertProfessorDisciplines,
    setDefaultDiscipline,
    logoutUser,
  })(ProfessorDashboard)


