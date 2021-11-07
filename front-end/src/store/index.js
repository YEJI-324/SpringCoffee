import { createStore } from 'vuex'
import axios from "axios";


let store = createStore({
  state(){  // 데이터보관하고 싶으면 여기에 기재
    return{
      username: '',
      cartCount: 0,
      boardList: [],
      boardDetail: {},
    }
  },
  mutations: { // 변경하길 원하는 것들은 이곳에다가 기재한다
    setBoard(state, board) {
      state.boardList = board;
    },
    setBoardDetail(state,boardDetail){
      state.boardDetail = boardDetail;
    },
    saveUsername(state, username) {
      state.username = username;
    },
    saveCartCount(state, cartCount) {
      state.cartCount = cartCount;
    }
  },
  actions: { // ajax 넣는곳, 오래 걸리는 작업들 넣는곳
    fetchBoard(context){
      axios.get(`/v1/list`)
        .then(response => {
          context.commit('setBoard', response.data.dtoList);
        })
    },
    fetchBoardDetail(context, boardNo){
      axios.get(`/v1/${boardNo}`)      // axios dynamic URL,
        .then(response =>{
          context.commit('setBoardDetail', response.data);
        })
    },
    saveUsername(context, username) {
      context.commit('saveUsername', username);
    },
    saveCartCount(context, cartCount) {
        context.commit('saveCartCount', cartCount);
    },

    login(context, boardNo){
      axios.get(`/v1/${boardNo}`)      // axios dynamic URL,
        .then(response =>{
          context.commit('setBoardDetail', response.data);
        })
    },
    register(context, boardNo){
      axios.get(`/v1/${boardNo}`)      // axios dynamic URL,
        .then(response =>{
          context.commit('setBoardDetail', response.data);
        })
    },
  },
})

export default store;