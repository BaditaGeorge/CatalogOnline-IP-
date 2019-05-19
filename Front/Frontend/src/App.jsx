import React, { Component, Suspense } from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import { Provider } from "react-redux";
import configureStore from './store';

import { PersistGate } from 'redux-persist/integration/react'
import { AdminDashboardWithRedux } from './components/AdminDashboard';
import { ProfessorDashboardRedux } from './components/ProfessorDashboard';
import { StudentDashboardRedux } from './components/StudentDashboard';
import { LoginWithRedux } from './components/Login';
import { RegisterWithRedux } from './components/Register';
import Layout from './components/Template/Layout'


import "bootstrap/dist/css/bootstrap.min.css";
import "./css/animate.min.css";
import "./css/sass/light-bootstrap-dashboard-react.scss?v=1.3.0";
import "./css/demo.css";
import "./css/pe-icon-7-stroke.css";


const config = configureStore()
const { store, persistor } = config

class App extends Component {
  render () {
    return (
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          <Routing/>
        </PersistGate>
      </Provider>
    );
  }
}

class Routing extends Component {
  render () {
    return (
      <Router>
        <Suspense fallback={null}>
          <Switch>
            <Route path="/admin" component={AdminDashboardWithRedux}/>
            <Route path="/dashboard" component={ProfessorDashboardRedux}/>
            <Route path="/student" component={StudentDashboardRedux}/>
            <Route path="/login" component={LoginWithRedux}/>
            <Route path="/register" component={RegisterWithRedux}/>
            <Route path="/test" component={Layout}/>
            <Redirect from="/" to="/login"/>
          </Switch>
        </Suspense>
      </Router>
    )
  }
}

export default App;
