package com.musongzi.core.itf;

import org.jetbrains.annotations.Nullable;

/**
 * 寓意为需求
 *
 */
public interface INeed {

    /**
     * 需求永无止境，所以还有下个需求
     * 没想好，感觉像个坑
     * @param sreach
     * @return
     */
    @Nullable
    <Next extends IBusiness> Next getNext(Class<Next> sreach);

}
