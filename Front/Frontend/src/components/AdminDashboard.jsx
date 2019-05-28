import React, {Component} from 'react';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import equal from "fast-deep-equal"
import {
  getProfessorCatalog,
  getProfessorDisciplines,
  getDisciplineFormulas,
  getProfessorsList,
  insertProfessorCatalog,
  insertProfessorDisciplines,
  insertProfessor,
  insertDisciplineFormulas,
  setDefaultDiscipline,
  setDefaultProfessor,
} from "../actions/adminActions";
import Formula from "./Formula";
import Navigation from "./Navigation";
import {logoutUser} from "../actions/loginActions";


class AdminDashboard extends Component {
  constructor(props) {
    super(props)
  }

  componentWillMount() {
    if (this.props.token) {
      this.props.getProfessorsList()
      if (this.props.currentProfessor.id_professor && this.props.currentDiscipline.id_materie) {
        this.props.getProfessorCatalog(this.props.currentDiscipline.id_materie, this.props.currentProfessor.id_professor)
        this.props.getDisciplineFormulas(this.props.currentProfessor.id_professor)
      }
    }
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (nextProps.token) {
      if (nextProps.currentDiscipline.id_materie && nextProps.currentProfessor.id_professor &&
        !equal(this.props.columns, nextProps.columns)) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
        this.props.getDisciplineFormulas(nextProps.currentProfessor.id_professor)
      }

      if (nextProps.currentDiscipline.id_materie !== this.props.currentDiscipline.id_materie ||
        nextProps.currentProfessor.id_professor !== this.props.currentProfessor.id_professor) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
        this.props.getDisciplineFormulas(nextProps.currentProfessor.id_professor)

      }

      if (nextProps.currentDiscipline.id_materie && nextProps.didUpdate) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
        this.props.getDisciplineFormulas(nextProps.currentProfessor.id_professor)
      }
    }
  }

  render() {
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

export const AdminDashboardWithRedux = connect(
  (state) => ({...state.adminReducer, ...state.loginReducer}),
  {
    getProfessorCatalog,
    getProfessorDisciplines,
    getDisciplineFormulas,
    getProfessorsList,
    insertProfessorCatalog,
    insertDisciplineFormulas,
    insertProfessorDisciplines,
    insertProfessor,
    setDefaultDiscipline,
    setDefaultProfessor,
    logoutUser,
  })(AdminDashboard)


