package io.github.yangxj96.common.respond

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.Serial
import java.io.Serializable

/**
 * 通用响应
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class R(code: Int, msg: String) : Serializable {

    var data: Any? = null

    constructor(code: Int, msg: String, data: Any?) : this(code, msg) {
        this.data = data
    }

    companion object {

        @Serial
        @JsonIgnore
        private val serialVersionUID = 1L

        @JvmStatic
        fun success(): R {
            setHeader(RStatus.SUCCESS)
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg)
        }

        @JvmStatic
        fun success(data: Any?): R {
            setHeader(RStatus.SUCCESS)
            return R(RStatus.SUCCESS.code, RStatus.SUCCESS.msg, data)
        }

        @JvmStatic
        fun failure(): R {
            setHeader(RStatus.FAILURE)
            return R(RStatus.FAILURE.code, RStatus.FAILURE.msg)
        }

        @JvmStatic
        fun specify(status: RStatus): R {
            setHeader(status)
            return R(status.code, status.msg)
        }

        fun specify(status: RStatus, data: Any?): R {
            setHeader(status)
            return R(status.code, status.msg, data)
        }

        /**
         * 设置请求头
         *
         * @param status 请求状态
         */
        private fun setHeader(status: RStatus) {
            val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
            if (attributes != null) {
                val response = attributes.response
                response?.setIntHeader(RHttpHeadersExpand.RESULT_CODE, status.code)
            }
        }
    }
}
