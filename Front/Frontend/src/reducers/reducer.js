import { combineReducers } from 'redux'
import adminReducer from './adminReducer'
import professorReducer from './professorReducer'
import studentReducer from './studentReducer'
import loginReducer from './loginReducer'

export default combineReducers({
  adminReducer,
  professorReducer,
  studentReducer,
  loginReducer
})