import {
  GET_PROFESSOR_CATALOG,
  GET_PROFESSOR_CATALOG_SUCCESS,
  GET_PROFESSOR_CATALOG_FAIL,
  GET_PROFESSOR_DISCIPLINES,
  GET_PROFESSOR_DISCIPLINES_SUCCESS,
  GET_PROFESSOR_DISCIPLINES_FAIL,
  GET_DISCIPLINE_FORMULAS,
  GET_DISCIPLINE_FORMULAS_SUCCESS,
  GET_DISCIPLINE_FORMULAS_FAIL,
  GET_PROFESSOR_LIST,
  GET_PROFESSOR_LIST_SUCCESS,
  GET_PROFESSOR_LIST_FAIL,
  POST_DISCIPLINE_FORMULAS,
  POST_DISCIPLINE_FORMULAS_SUCCESS,
  POST_DISCIPLINE_FORMULAS_FAIL,
  POST_PROFESSOR,
  POST_PROFESSOR_SUCCESS,
  POST_PROFESSOR_FAIL,
  POST_PROFESSOR_CATALOG,
  POST_PROFESSOR_CATALOG_SUCCESS,
  POST_PROFESSOR_CATALOG_FAIL,
  POST_PROFESSOR_DISCIPLINES,
  POST_PROFESSOR_DISCIPLINES_SUCCESS,
  POST_PROFESSOR_DISCIPLINES_FAIL,
  SET_CURRENT_DISCIPLINE,
  SET_CURRENT_PROFESSOR,
} from "../actions/adminActions";


const INITIAL_STATE = {
  global: undefined,
  columns: [],
  rows: [],
  professors: [],
  disciplines: [],
  currentDiscipline: {},
  currentProfessor: {},
  formulas: [],
  didUpdate: false,
  loading: false
};

export default function adminReducer(state = INITIAL_STATE, action) {
  switch (action.type) {
    case GET_PROFESSOR_CATALOG: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_PROFESSOR_CATALOG_SUCCESS: {
      return {
        ...state,
        rows: action.payload.rows,
        columns: action.payload.columns,
        didUpdate: action.payload.didUpdate,
        loading: false
      };
    }
    case GET_PROFESSOR_CATALOG_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case GET_PROFESSOR_DISCIPLINES: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_PROFESSOR_DISCIPLINES_SUCCESS: {
      return {
        ...state,
        disciplines: action.payload.disciplines,
        currentDiscipline: action.payload.currentDiscipline,
        loading: false
      };
    }
    case GET_PROFESSOR_DISCIPLINES_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case GET_DISCIPLINE_FORMULAS: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_DISCIPLINE_FORMULAS_SUCCESS: {
      return {
        ...state,
        formulas: action.payload.formulas,
        loading: false
      };
    }
    case GET_DISCIPLINE_FORMULAS_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case GET_PROFESSOR_LIST: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_PROFESSOR_LIST_SUCCESS: {
      return {
        ...state,
        professors: action.payload.professors,
        disciplines: action.payload.disciplines,
        currentDiscipline: action.payload.currentDiscipline,
        currentProfessor: action.payload.currentProfessor,
        loading: false
      };
    }
    case GET_PROFESSOR_LIST_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case SET_CURRENT_DISCIPLINE: {
      return {
        ...state,
        currentDiscipline: action.payload.currentDiscipline
      }
    }

    case SET_CURRENT_PROFESSOR: {
      return {
        ...state,
        currentProfessor: action.payload.currentProfessor,
        currentDiscipline: action.payload.currentDiscipline,
        disciplines: action.payload.disciplines
      }
    }
    case POST_DISCIPLINE_FORMULAS: {
      return {
        ...state,
        loading: true,
      };
    }
    case POST_DISCIPLINE_FORMULAS_SUCCESS: {
      return {
        ...state,
        formulas: action.payload.formulas,
        loading: false
      };
    }
    case POST_DISCIPLINE_FORMULAS_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case POST_PROFESSOR_CATALOG: {
      return {
        ...state,
        loading: true
      };
    }
    case POST_PROFESSOR_CATALOG_SUCCESS: {
      return {
        ...state,
        didUpdate: action.payload.didUpdate,
        loading: false
      };
    }
    case POST_PROFESSOR_CATALOG_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case POST_PROFESSOR: {
      return {
        ...state,
        loading: true
      };
    }
    case POST_PROFESSOR_SUCCESS: {
      return {
        ...state,
        professors: action.payload.professors,
        loading: false
      };
    }
    case POST_PROFESSOR_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    case POST_PROFESSOR_DISCIPLINES: {
      return {
        ...state,
        loading: true
      };
    }
    case POST_PROFESSOR_DISCIPLINES_SUCCESS: {
      return {
        ...state,
        disciplines: action.payload.disciplines,
        loading: false
      };
    }
    case POST_PROFESSOR_DISCIPLINES_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    default:
      return state;
  }
}
