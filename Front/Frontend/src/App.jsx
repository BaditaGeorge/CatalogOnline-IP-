import React, { Component, Suspense } from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import { connect, Provider } from "react-redux";
import configureStore from './store';

import { PersistGate } from 'redux-persist/integration/react'
import { AdminDashboardWithRedux } from './components/AdminDashboard';
import { ProfessorDashboardRedux } from './components/ProfessorDashboard';
import { StudentDashboardRedux } from './components/StudentDashboard';
import { LoginWithRedux } from './components/Login';
import { RegisterWithRedux } from './components/Register';
import { VerifyWithRedux } from './components/Verify';

// import "bootstrap/dist/css/bootstrap.min.css";
// import "./css/animate.min.css";
// import "./css/sass/light-bootstrap-dashboard-react.scss?v=1.3.0";
// import "./css/demo.css";
// import "./css/pe-icon-7-stroke.css";

const config = configureStore();
const { store, persistor } = config;

class App extends Component {
  render () {
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


  render () {
    console.log(this.props.role, 'asdasdasdsad')
    let whatToRender = <Redirect to={'/login'}/>;
    if (this.props.token.length)
      switch (this.props.role) {
        case 'student':
          whatToRender = <StudentDashboardRedux/>;
          if (!this.props.verified)
            whatToRender = <Redirect to={'/verify'}/>
          break;
        case 'professor':
          whatToRender = <ProfessorDashboardRedux/>;
          break;
        case 'admin':
          whatToRender = <AdminDashboardWithRedux/>;
          break;
        default:
          break;
      }

    return (
      <Router>
        <Suspense fallback={null}>
          <Switch>
            <Route path="/login" component={LoginWithRedux}/>
            <Route path="/register" component={RegisterWithRedux}/>
            <Route path="/verify" component={VerifyWithRedux}/>
            <Route path="/dashboard" render={() => (whatToRender)}/>
            <Route exact path="" render={() => (
              !whatToRender ? (
                <Redirect to="/login"/>
              ) : (
                <Redirect to="/dashboard"/>
              )
            )}/>
          </Switch>
        </Suspense>
      </Router>
    )
  }
}

const RoutingWithRedux = connect((state) => ({
  role: state.loginReducer.role,
  token: state.loginReducer.token,
  verified: state.loginReducer.verified,
  loading: state.loginReducer.loading
}), {})(Routing);

export default App;
