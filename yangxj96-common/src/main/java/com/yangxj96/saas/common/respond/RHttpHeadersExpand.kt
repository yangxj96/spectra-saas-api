package com.yangxj96.saas.common.respond

import org.springframework.http.HttpHeaders
import java.io.Serial

/**
 * 响应头类型扩展
 *
 */
object RHttpHeadersExpand : HttpHeaders() {

    private fun readResolve(): Any = RHttpHeadersExpand

    @Serial
    private val serialVersionUID = -2727675696060704975L

    /**
     * 响应码
     */
    const val RESULT_CODE = "result-code"


}
