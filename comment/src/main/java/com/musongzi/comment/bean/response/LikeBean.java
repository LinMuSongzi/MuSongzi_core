package com.musongzi.comment.bean.response;

/*** created by linhui * on 2022/7/22 */
public class LikeBean extends MessageItemBean {

    int livePeople = 0;
    int lockStatus = 0;
    int roomStatus = -1;


    public int getLockStatus() {
        return lockStatus;
    }

    public int getLivePeople() {
        return livePeople;
    }

    public void setLivePeople(int livePeople) {
        this.livePeople = livePeople;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }


    public String getLivePeopleStr() {
        return livePeople + "人在看";
    }

    public String getRoomStatusStr() {
        switch (roomStatus) {
            case 0:
                return "语聊房";
            case 1:
                return "唱歌房";
            case 2:
                return "游戏房";
            default:
                return "直播房";
        }
    }

}
