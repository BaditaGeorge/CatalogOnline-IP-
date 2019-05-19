import {
  GET_STUDENT_CATALOG,
  GET_STUDENT_CATALOG_SUCCESS,
  GET_STUDENT_CATALOG_FAIL,
  GET_STUDENT_DISCIPLINES,
  GET_STUDENT_DISCIPLINES_SUCCESS,
  GET_STUDENT_DISCIPLINES_FAIL,
  SET_STUDENT_CURRENT_DISCIPLINE
} from "../actions/studentActions";

const INITIAL_STATE = {
  disciplines: [],
  currentDiscipline: {},
  loading: false,
  catalogs: [],
};

export default function studentReducer (state = INITIAL_STATE, action) {
  switch (action.type) {
    case GET_STUDENT_CATALOG: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_STUDENT_CATALOG_SUCCESS: {
      return {
        ...state,
        catalogs: action.payload.catalogs,
        loading: false
      };
    }
    case GET_STUDENT_CATALOG_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case GET_STUDENT_DISCIPLINES: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_STUDENT_DISCIPLINES_SUCCESS: {
      return {
        ...state,
        disciplines: action.payload.disciplines,
        currentDiscipline: action.payload.currentDiscipline,
        loading: false
      };
    }
    case GET_STUDENT_DISCIPLINES_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case SET_STUDENT_CURRENT_DISCIPLINE: {
      return {
        ...state,
        currentDiscipline: action.payload.currentDiscipline,
        loading: false
      }
    }
    default:
      return state;
  }
}
