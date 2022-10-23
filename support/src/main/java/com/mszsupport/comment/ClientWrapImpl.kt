package com.mszsupport.comment

import com.mszsupport.itf.IClient

class ClientWrapImpl(private val client:IClient) :IClient by client {


}