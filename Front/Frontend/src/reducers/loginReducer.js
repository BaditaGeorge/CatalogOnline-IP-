import {
  GET_USER_DATA,
  GET_USER_DATA_SUCCESS,
  GET_USER_DATA_FAIL,
  LOGOUT_USER_SUCCESS
} from "../actions/loginActions";


const INITIAL_STATE = {
  userName: '',
  userId: undefined,
  role: '',
  token: '',
  loading: false,
};

export default function loginReducer(state = INITIAL_STATE, action) {
  switch (action.type) {
    case GET_USER_DATA: {
      return {
        ...state,
        loading: true
      };
    }
    case GET_USER_DATA_SUCCESS: {
      return {
        ...state,
        userId: action.payload.userId,
        userName: action.payload.username,
        role: action.payload.role,
        token: action.payload.token,
        loading: false
      };
    }
    case LOGOUT_USER_SUCCESS: {
      return {
        ...state,
        userId: null,
        role: '',
        token: '',
        loading: false
      };
    }

    case GET_USER_DATA_FAIL: {
      return {
        ...state,
        loading: false
      };
    }
    default:
      return state;
  }
}
