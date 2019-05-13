import {
    GET_GLOBAL,
    GET_GLOBAL_SUCCESS,
    GET_GLOBAL_FAIL,
    GET_PROFESSOR_CATALOG,
    GET_PROFESSOR_CATALOG_SUCCESS,
    GET_PROFESSOR_CATALOG_FAIL,
    GET_PROFESSOR_DISCIPLINES,
    GET_PROFESSOR_DISCIPLINES_SUCCESS,
    GET_PROFESSOR_DISCIPLINES_FAIL,
    GET_DISCIPLINE_FORMULAS,
    GET_DISCIPLINE_FORMULAS_SUCCESS,
    GET_DISCIPLINE_FORMULAS_FAIL,
    POST_DISCIPLINE_FORMULAS,
    POST_DISCIPLINE_FORMULAS_SUCCESS,
    POST_DISCIPLINE_FORMULAS_FAIL,
    POST_PROFESSOR_CATALOG,
    POST_PROFESSOR_CATALOG_SUCCESS,
    POST_PROFESSOR_CATALOG_FAIL,
    POST_PROFESSOR_DISCIPLINES,
    POST_PROFESSOR_DISCIPLINES_SUCCESS,
    POST_PROFESSOR_DISCIPLINES_FAIL,
    SET_CURRENT_DISCIPLINE,
} from "../actions/professorActions";


const INITIAL_STATE = {
    global: undefined,
    columns: [],
    disciplines: [],
    currentDiscipline: {},
    formulas: [],
    rows: [],
    loading: false
};

export default function professorReducer(state = INITIAL_STATE, action) {
    switch (action.type) {
        case GET_GLOBAL: {
            return {
                ...state,
                loading: true
            };
        }
        case GET_GLOBAL_SUCCESS: {
            return {
                ...state,
                global: action.payload.global,
                loading: false
            };
        }
        case GET_GLOBAL_FAIL: {
            return {
                ...state,
                loading: false
            };
        }
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
        case SET_CURRENT_DISCIPLINE:
        {
            return {
                ...state,
                currentDiscipline: action.payload.currentDiscipline
            }
        }
        case POST_DISCIPLINE_FORMULAS: {
            return {
                ...state,
                loading: true
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
                rows: action.payload.rows,
                columns: action.payload.columns,
                loading: false
            };
        }
        case POST_PROFESSOR_CATALOG_FAIL: {
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
                currentDiscipline: action.payload.currentDiscipline,
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
