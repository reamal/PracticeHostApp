这是一个根据 baobao 《Android插件化开发指南》 的练习demo

Demo 运行在 api 21 模拟器上面。


* app 包含两个插件 { firstPlugin , secondPlugin }
  启动插件中的service

* activityHook 包含一个插件 {hookPlugin}
    加载插件中类的方案1：为每个插件创建一个ClassLoader
* hook2act 包含一个插件 {hookPlugin}
     加载插件中类的方案2：合并多个dex

* zeus14 包含插件{zeusplugin} zeus classloader 方案