package com.musongzi.test.bean.respone

/*** created by linhui * on 2022/8/3 */
class AlbumMusicsRemoteBean : BaseResponse() {


    var dataBean = DataBean()


    class DataBean{
        var list = ArrayList<MusicDetailRemoteBean>()
    }


}