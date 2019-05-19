import React, {Component, Fragment} from "react";
import {NavItem, Nav, NavDropdown} from "react-bootstrap";

export default class AdminNavbarLinks extends Component {
  render() {
    const notification = (
      <div>
        <i className="fa fa-globe"/>
        <b className="caret"/>
        <span className="notification">5</span>
        <p className="hidden-lg hidden-md">Notification</p>
      </div>
    );
    return (
      <Fragment>
        <Nav>
          <NavItem eventKey={1} href="#">
            <i className="fa fa-dashboard"/>
            <p className="hidden-lg hidden-md">Dashboard</p>
          </NavItem>
          <NavDropdown
            eventKey={2}
            title={notification}
            noCaret
            id="basic-nav-dropdown"
          >
            <NavDropdown.Item> eventKey={2.1}>Notification 1</NavDropdown.Item>
            <NavDropdown.Item> eventKey={2.2}>Notification 2</NavDropdown.Item>
            <NavDropdown.Item> eventKey={2.3}>Notification 3</NavDropdown.Item>
            <NavDropdown.Item> eventKey={2.4}>Notification 4</NavDropdown.Item>
            <NavDropdown.Item> eventKey={2.5}>Another notifications</NavDropdown.Item>
          </NavDropdown>
          <NavItem eventKey={3} href="#">
            <i className="fa fa-search"/>
            <p className="hidden-lg hidden-md">Search</p>
          </NavItem>
        </Nav>
        <Nav pullRight>
          <NavItem eventKey={1} href="#">
            Account
          </NavItem>
          <NavDropdown
            eventKey={2}
            title="Dropdown"
            id="basic-nav-dropdown-right"
          >
            <NavDropdown.Item eventKey={2.1}>Action</NavDropdown.Item>
            <NavDropdown.Item eventKey={2.2}>Another action</NavDropdown.Item>
            <NavDropdown.Item eventKey={2.3}>Something</NavDropdown.Item>
            <NavDropdown.Item eventKey={2.4}>Another action</NavDropdown.Item>
            <NavDropdown.Item eventKey={2.5}>Something</NavDropdown.Item>
            <NavDropdown.Item divider/>
            <NavDropdown.Item eventKey={2.5}>Separated link</NavDropdown.Item>
          </NavDropdown>
          <NavItem eventKey={3} href="#">
            Log out
          </NavItem>
        </Nav>
      </Fragment>
    );
  }
}