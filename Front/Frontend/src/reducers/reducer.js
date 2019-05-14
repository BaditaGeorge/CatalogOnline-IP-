import {combineReducers} from 'redux'
import professorReducer from './professorReducer'
import studentReducer from './studentReducer'

export default combineReducers({
    professorReducer,
    studentReducer
})