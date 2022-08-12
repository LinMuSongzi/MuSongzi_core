package com.musongzi.music.itf

/*** created by linhui
 *基于队列新增一个播放控制
 * * on 2022/7/28 */
internal interface ISmartPlayQueueManager : IPlayQueueManager, IPlayObservable {
    /**
     * 此方法 [IPlayQueueManager.playMusic] 调用最终会回调到此方法。
     * @param info 当前的播放音乐信息
     * @param musicArray 当前播放的音乐队列/专辑
     */
    fun playMusic(info: IMediaPlayInfo, musicArray: IMusicArray<IMediaPlayInfo>)

    /**
     * 此方法 [IPlayQueueManager.playMusic] 调用最终会回调到此方法。
     * @param info 当前的播放音乐信息
     * @param musicArray 当前播放的音乐队列/专辑
     */
    fun playMusic(stringUrl: String, musicArray: IMusicArray<IMediaPlayInfo>)

    /**
     * 此方法 [IPlayQueueManager.pauseMusic] 调用最终会回调到此方法。
     * @param musicArray 当前播放的音乐队列/专辑
     */
    fun pauseMusic(musicArray: IMusicArray<IMediaPlayInfo>)

    /**
     * 此方法 [IPlayQueueManager.stopMusic] 调用最终会回调到此方法。
     * @param musicArray 当前播放的音乐队列/专辑
     */
    fun stopMusic(musicArray: IMusicArray<IMediaPlayInfo>)

    /**
     * 播放音乐后回回调此方法
     * @param playInfoNow 当前播放的音乐
     */
    fun joinHistory(playInfoNow: IMediaPlayInfo?)

}