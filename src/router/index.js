import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Login from'../views/Login.vue'
import Register from'../views/Register'
import Book from'../views/Book'
import Person from "@/views/Person";
import view from "@element-plus/icons/lib/View";
import News from "@/views/News";


const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/home",
    children:[
      {
        path:"home",
        name:'HomeView',
        component:()=>import("@/views/HomeView"),
      },
      {
        path:'/book',
        name:'Book',
        component:Book,
      },
      {
        path:'/person',
        name:'Person',
        component:Person,
      },
      {
        path:'/news',
        name:'News',
        component:News,
      },

    ],

  },{
    path:'/login',
    name:'Login',
    component:Login,


  }
  ,{
    path:'/register',
    name:'Register',
    component:Register,


  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
