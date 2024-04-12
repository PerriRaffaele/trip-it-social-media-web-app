import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import store from '../store/index.js'

import UserProfileView from '../views/UserProfileView.vue'
import NotificationView from "@/views/NotificationView.vue";

const routes = [

  [
    { path: '/HomeView', component: HomeView },
    { path: '/UserProfileView', component: UserProfileView }
  ],

  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/profile',
    name: 'owner-profile',

    // redirect: `/`,
    // { name : 'user-profile', params: store.state.user.id },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/UserProfileView.vue')
  },
  {
    path: '/user/:id',
    name: 'user-profile',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/UserProfileView.vue')
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/add',
    name: 'add',
    component: () => import(/* webpackChunkName: "add" */ '../views/AddUserView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "add" */ '../views/LoginView.vue')
  },
  {

    path: '/journey/:id',
    name: 'journey',
    component: () => import(/* webpackChunkName: "add" */ '../views/JourneyView.vue')
  },
  {
    path: '/Journal/:id',
    name: 'Journal',
    component: () => import(/* webpackChunkName: "add" */ '../views/JournalView.vue')
  },
  {
    path: '/notifications/',
    component: NotificationView
  },
  {
    path: '/like/',
    component: () => import(/* webpackChunkName: "add" */ '../views/LikeNotification.vue')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
