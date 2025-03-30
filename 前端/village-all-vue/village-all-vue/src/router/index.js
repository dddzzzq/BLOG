import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      component:()=>import("../views/head/index.vue"),
      redirect:'/hall',
      children:[
        {
          path: '/detail',
          component:()=>import("../views/details/index.vue")
        },
        {
          path: '/hall',
          component:()=>import("../views/home/index.vue")
        },
        {
          path: '/usr',
          component:()=>import("../views/user-center/index.vue")
        },
        {
          path:'/activity',
          component:()=>import("../views/act/index.vue")
        },
        {
          path: '/activityDetail',
          component:()=>import("../views/actDetail/index.vue")
        }

      ]
    },

      //后台界面
    {
         path:'/cl',
         component:()=>import("../views/controlLayout.vue"),
        redirect: '/blog',
         children:[
           {
             path:'/blog',
             component:()=>import("../views/Blog/index.vue")
           },
           {
             path:'/users',
             component:()=>import("../views/user/user.vue")
           },
           {
             path:'/cate',
             component:()=>import("../views/cate/index.vue")
           },
           {
             path:'/act',
             component:()=>import("../views/activity/index.vue")
           },
           {
             path:'/up',
             component:()=>import("../views/upload/index.vue")
           },
           {
             path: '/com',
             component:()=>import("../views/comment/index.vue")
           },
           {
             path: '/join',
             component:()=>import("../views/join/index.vue")
           }

         ]
    },
    {
      path:'/center',
      component:()=>import("../views/personcenter/personal.vue")
    },

    //登录界面的路由配置
    {
      path:'/log',
      name: 'login',
      component: () => import('../views/log/index.vue'),
    },
  ]
})

export default router
