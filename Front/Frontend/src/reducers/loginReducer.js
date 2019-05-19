import {
  GET_USER_DATA,
  GET_USER_DATA_SUCCESS,
  GET_USER_DATA_FAIL,
} from "../actions/loginActions";

const INITIAL_STATE = {
  userName: 'James Doe',
  role: 'professor',
  token: 'asdasd7ad712ehsujcksd2husuxsa',
  verified: true,
  loading: false,
};

export default function loginReducer (state = INITIAL_STATE, action) {
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
        userName: action.payload.userName,
        role: action.payload.role,
        token: action.payload.token,
        verified: action.payload.verified,
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
