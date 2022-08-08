package com.musongzi.test.bean;


import com.musongzi.test.bean.respone.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ListDataBean<E> extends BaseResponse {

    public List<E> data = new ArrayList<>();

}
