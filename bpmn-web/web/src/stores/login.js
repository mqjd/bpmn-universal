import { defineStore } from 'pinia'

export const useLoginStore = defineStore('loginStore', {
    state: () => {
        token: null,
        userInfo: null
    }
})