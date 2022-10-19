package com.musongzi.core.itf;

import org.jetbrains.annotations.NotNull;

/*** created by linhui * on 2022/7/20 */
public interface IViewInstance extends IClear {

    void runOnUiThread(@NotNull Runnable runnable);

}
