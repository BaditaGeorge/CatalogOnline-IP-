import React, {Component} from 'react';
import {connect} from "react-redux";
import Catalog from "./Catalog"
import {
  getStudentCatalog,
  getStudentDisciplines,
  setDefaultDiscipline
} from "../actions/studentActions";
import Navigation from "./Navigation";
import {logoutUser} from "../actions/loginActions";
import equal from "fast-deep-equal";


class StudentDashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentDiscipline: {}
    }
  }


  componentWillMount() {
    if (this.props.token) {
      this.props.getStudentDisciplines(this.props.userId)
    }
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    if (nextProps.token) {
      if (nextProps.currentDiscipline.id_materie !== this.props.currentDiscipline.id_materie) {
        console.log(nextProps, 'asdasdasds')
        this.props.getStudentCatalog(nextProps.userId, nextProps.currentDiscipline.id_materie)
      }
    }
  }

  render() {
    if (!this.props.loading) {
      let catalogIndex = 0;
      this.props.catalogs.map((catalog, index) => {
        if (catalog.denumire_materie === this.props.currentDiscipline.denumire_materie) {
          catalogIndex = index;
        }
      });
      const user = {name: this.props.userName, role: this.props.role, userId: this.props.userId};
      return (
        <div className={'dashboard'}>
          <Navigation user={user} {...this.props}/>
          <Catalog user={user}
                   rows={this.props.catalogs.length ? this.props.catalogs[catalogIndex].rows : []}
                   columns={this.props.catalogs.length ? this.props.catalogs[catalogIndex].columns : []}
                   {...this.props}
                   insertProfessorCatalog={() => {
                   }}
          />
        </div>
      );
    }
    return <div>Loading</div>
  }
}

export const StudentDashboardRedux = connect(
  state => ({...state.studentReducer, ...state.loginReducer}),
  {
    getStudentCatalog,
    getStudentDisciplines,
    setDefaultDiscipline,
    logoutUser,
  })(StudentDashboard);


