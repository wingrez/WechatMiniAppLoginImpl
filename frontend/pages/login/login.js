Page({
  data: {

  },

  onLoad: function (options) {

  },

  login:function(e){
    console.log(e.detail.errMsg)
    console.log(e.detail.userInfo)
    console.log(e.detail.rawData)

    wx.login({
      success:function(res){
        console.log(res)
        var code=res.code //获取登录的临时凭证
        //调用后端，获取session_key、secret
        wx.request({
          url: 'http://192.168.0.102:8080/login',
          method:'POST',
          data:{
            code: code,
          },
          header: { 'content-type': 'application/x-www-form-urlencoded' },
          success:function(res){
            console.log(res)
          }
        })
      }
    })
  }

})