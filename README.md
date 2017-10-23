# Web4Android
“泡在网上的日子”第三方Andoid版本的app。<br>

## 数据来源:
所有数据来源于http://www.jcodecraeer.com/ <br>

所有数据通过解析html网页获得<br>

## 开发过程:

挺喜欢[泡在网上的日子](http://www.jcodecraeer.com/)这个网站的,由于网站一直都没有相关的客户端(不过现在貌似有了),17年4月份萌生了想要给该网站制作Android客户端的的想法。</br>

由于网站未提供相关的API获取数据，所以试过多种的网页解析库，最后发现Jsoup库挺合适。这下数据来源有了就好办了。</br>

UI设计方面遵循在有限的页面显示更多内容的理念，采用radioGroup+Fragment方式进行切换显示相关模块的内容。每个Fragment里面还可以嵌套Tablayout，提高了显示内容的容纳量。<br>

app包括4个Fragment，即“首页”，“代码”，“话题”，“问答”4个模块。"首页"模块采用Tablayout以显示若干个子模块<br>

app整体采用MVP模式，通过Jsoup获取数据，使用Glide展示列表图片，WebView展示具体内容。<br>

整体没什么难度，烦人的是网页解析时手动F12找标签，获取某个关键数据时需要反复的去试，肝都试出来(*￣︶￣)<br>

遗憾的是没有登录选项，只能单机浏览 o(╥﹏╥)o<br>
    
## 项目截图：
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742807.png)
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742811.png)
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742815.png)
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742819.png)
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742823.png)
![](https://github.com/JamJunLe/Web4Android/blob/master/screenshots/Screenshot_1508742827.png)

## 第三方框架：
#### 网页解析 [Jsoup](https://jsoup.org/)
#### 网络 [Volley](https://github.com/google/volley)
#### 图片加载 [Gide](https://github.com/bumptech/glide)
#### 对话框 [MaterialDialogs](https://github.com/afollestad/material-dialogs)
#### 状态栏 [StatusBarUtil](https://github.com/laobie/StatusBarUtil)


## 关于
#### 如果有问题,提出您宝贵的[意见和想法](https://github.com/JamJunLe/Web4Android/issues)



