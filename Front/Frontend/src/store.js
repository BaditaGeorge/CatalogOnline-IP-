import {applyMiddleware, createStore} from "redux";

// import logger from "redux-logger";
import thunk from "redux-thunk";

import reducer from "./reducers/reducer";

// const middleware = applyMiddleware(thunk, logger);
const middleware = applyMiddleware(thunk);

export default createStore(reducer, middleware);
