import { applyMiddleware, createStore } from "redux";
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import logger from "redux-logger";
import thunk from "redux-thunk";

import rootReducer from "./reducers/reducer";

const middleware = applyMiddleware(thunk, logger);

// export default createStore(reducer, middleware);

const persistConfig = {
  key: 'root',
  storage,
}

const persistedReducer = persistReducer(persistConfig, rootReducer);

export default () => {
  let store = createStore(persistedReducer, middleware)
  let persistor = persistStore(store)
  return { store, persistor }
}