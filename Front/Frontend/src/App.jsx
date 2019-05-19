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
            <Redirect from="/" to="/login"/>
          </Switch>
        </Suspense>
      </Router>
    )
  }
}

export default App;
