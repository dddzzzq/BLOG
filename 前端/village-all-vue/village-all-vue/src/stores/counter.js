//pinia状态管理器
import { defineStore } from 'pinia'

export const useStore = defineStore('counter', {
  state: () => {
    return {
      user:[],
    }
  },

  actions: {

  },
})