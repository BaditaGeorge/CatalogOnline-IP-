import React, {Component} from 'react';
import {connect} from "react-redux";
import Catalog from "./Catalog"
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
    if (this.props.token && this.props.token.length) {
      this.props.getProfessorsList()
    }
    if (this.props.currentProfessor.id_professor && this.props.currentDiscipline.id_materie) {
      this.props.getProfessorCatalog(this.props.currentDiscipline.id_materie, this.props.currentProfessor.id_professor)
    }
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (nextProps.token !== this.props.token || this.props.token.length) {
      console.log(nextProps.currentProfessor)
      if (nextProps.currentDiscipline.id_materie && nextProps.currentProfessor.id_professor && !nextProps.columns.length) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
      }
      if (nextProps.currentDiscipline.id_materie !== this.props.currentDiscipline.id_materie && nextProps.rows.length) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
      }

      if (nextProps.currentProfessor.id_professor !== this.props.currentProfessor.id_professor && nextProps.rows.length) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
      }

      if (nextProps.didUpdate === true && this.props.currentDiscipline.id_materie) {
        this.props.getProfessorCatalog(nextProps.currentDiscipline.id_materie, nextProps.currentProfessor.id_professor)
      }
    }
  }

  render() {
    if (!this.props.loading)
      return (
        <div className={'dashboard'}>
          <Navigation user={{name: this.props.userName, role: this.props.role, userId: this.props.userId}}
                      professors={this.props.professors}
                      currentProfessor={this.props.currentProfessor}
                      disciplines={this.props.disciplines}
                      currentDiscipline={this.props.currentDiscipline}
                      onAddDiscipline={this.props.insertProfessorDisciplines}
                      onAddProfessor={this.props.insertProfessor}
                      onDisciplineChange={this.props.setDefaultDiscipline}
                      onProfessorChange={this.props.setDefaultProfessor}
                      onUserLogout={this.props.logoutUser}
          />
          <Catalog user={{name: this.props.userName, role: this.props.role, userId: this.props.userId}}
                   currentDiscipline={this.props.currentDiscipline}
                   currentProfessor={this.props.currentProfessor}
                   formulas={this.props.formulas}
                   rows={this.props.rows}
                   columns={this.props.columns}
                   onCatalogChange={this.props.insertProfessorCatalog}
          />
          {/*<Formula formulas={this.props.formulas}*/}
          {/*         currentDiscipline={this.props.currentDiscipline}*/}
          {/*         onChangeFormula={this.props.insertDisciplineFormulas}*/}
          {/*/>*/}
        </div>
      );
    return <div>Loading</div>
  }
}

export const
  AdminDashboardWithRedux = connect((state) => ({
    columns: state.adminReducer.columns,
    professors: state.adminReducer.professors,
    currentProfessor: state.adminReducer.currentProfessor,
    disciplines: state.adminReducer.disciplines,
    didUpdate: state.adminReducer.didUpdate,
    currentDiscipline: state.adminReducer.currentDiscipline,
    formulas: state.adminReducer.formulas,
    rows: state.adminReducer.rows,
    loading: state.adminReducer.loading,
    userName: state.loginReducer.userName,
    role: state.loginReducer.role,
    token: state.loginReducer.token,
    verified: state.loginReducer.verified,
  }), {
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


