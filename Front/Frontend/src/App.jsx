import React, {Component, Suspense} from 'react';
import configureStore from './store';
import {BrowserRouter as Router, Route, Switch, Redirect} from 'react-router-dom';
import {connect, Provider} from "react-redux";
import {PersistGate} from 'redux-persist/integration/react'
import {AdminDashboardWithRedux} from './components/AdminDashboard';
import {ProfessorDashboardRedux} from './components/ProfessorDashboard';
import {StudentDashboardRedux} from './components/StudentDashboard';
import {LoginWithRedux} from './components/Login';
import {RegisterWithRedux} from './components/Register';
import {VerifyWithRedux} from "./components/Verify";
import './App.css';

const config = configureStore();
const {store, persistor} = config;


export default class App extends Component {

  render() {
    return (
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          <RoutingWithRedux/>
        </PersistGate>
      </Provider>
    );
  }
}

class Routing extends Component {
  componentWillMount() {
    LoginWithRedux.redirectIfLogged(this.props)
  }

  componentWillUpdate(nextProps, nextState, nextContext) {
    LoginWithRedux.redirectIfLogged(nextProps)
  }

  render() {
    let whichDashboard;
    if (this.props.token)
      switch (this.props.role) {
        case 'student':
          whichDashboard = <StudentDashboardRedux/>;
          break;
        case 'professor':
          whichDashboard = <ProfessorDashboardRedux/>;
          break;
        case 'admin':
          whichDashboard = <AdminDashboardWithRedux/>;
          break;
        default:
          break;
      }

    return (
      <Router>
        <Suspense fallback={null}>
          <Switch>
            <Route path={"/login"} component={LoginWithRedux}/>
            <Route path={"/register"} component={RegisterWithRedux}/>
            <Route path={"/verify"} component={VerifyWithRedux}/>
            <Route path={"/dashboard"} render={() => (whichDashboard)}/>
            <Route path="" render={() => <Redirect to={this.props.token ? '/dashboard' : '/login'}/>}/>
          </Switch>
        </Suspense>
      </Router>
    )
  }
}

const RoutingWithRedux = connect((state) => ({...state.loginReducer}), {})(Routing);
