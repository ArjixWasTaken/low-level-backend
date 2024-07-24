package com.arjix.http

enum class HttpStatus(val code: Int, val message: String) {
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/100">MDN Web Docs</a> */
    Continue(100, "Continue"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/101">MDN Web Docs</a> */
    SwitchingProtocols(101, "Switching Protocols"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/102">MDN Web Docs</a> */
    Processing(102, "Processing"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/103">MDN Web Docs</a> */
    EarlyHints(103, "Early Hints"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/200">MDN Web Docs</a> */
    OK(200, "OK"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/201">MDN Web Docs</a> */
    Created(201, "Created"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/202">MDN Web Docs</a> */
    Accepted(202, "Accepted"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/203">MDN Web Docs</a> */
    NonAuthoritativeInformation(203, "Non-Authoritative Information"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/204">MDN Web Docs</a> */
    NoContent(204, "No Content"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/205">MDN Web Docs</a> */
    ResetContent(205, "Reset Content"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/206">MDN Web Docs</a> */
    PartialContent(206, "Partial Content"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/207">MDN Web Docs</a> */
    MultiStatus(207, "Multi-Status"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/208">MDN Web Docs</a> */
    AlreadyReported(208, "Already Reported"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/226">MDN Web Docs</a> */
    IMUsed(226, "IM Used"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/300">MDN Web Docs</a> */
    MultipleChoices(300, "Multiple Choices"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/301">MDN Web Docs</a> */
    MovedPermanently(301, "Moved Permanently"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/302">MDN Web Docs</a> */
    Found(302, "Found"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/303">MDN Web Docs</a> */
    SeeOther(303, "See Other"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304">MDN Web Docs</a> */
    NotModified(304, "Not Modified"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/305">MDN Web Docs</a> */
    UseProxy(305, "Use Proxy"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/306">MDN Web Docs</a> */
    unused(306, "unused"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/307">MDN Web Docs</a> */
    TemporaryRedirect(307, "Temporary Redirect"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/308">MDN Web Docs</a> */
    PermanentRedirect(308, "Permanent Redirect"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400">MDN Web Docs</a> */
    BadRequest(400, "Bad Request"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/401">MDN Web Docs</a> */
    Unauthorized(401, "Unauthorized"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/402">MDN Web Docs</a> */
    PaymentRequired(402, "Payment Required"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/403">MDN Web Docs</a> */
    Forbidden(403, "Forbidden"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404">MDN Web Docs</a> */
    NotFound(404, "Not Found"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/405">MDN Web Docs</a> */
    MethodNotAllowed(405, "Method Not Allowed"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/406">MDN Web Docs</a> */
    NotAcceptable(406, "Not Acceptable"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/407">MDN Web Docs</a> */
    ProxyAuthenticationRequired(407, "Proxy Authentication Required"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/408">MDN Web Docs</a> */
    RequestTimeout(408, "Request Timeout"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/409">MDN Web Docs</a> */
    Conflict(409, "Conflict"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/410">MDN Web Docs</a> */
    Gone(410, "Gone"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/411">MDN Web Docs</a> */
    LengthRequired(411, "Length Required"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/412">MDN Web Docs</a> */
    PreconditionFailed(412, "Precondition Failed"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/413">MDN Web Docs</a> */
    PayloadTooLarge(413, "Payload Too Large"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/414">MDN Web Docs</a> */
    URITooLong(414, "URI Too Long"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/415">MDN Web Docs</a> */
    UnsupportedMediaType(415, "Unsupported Media Type"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/416">MDN Web Docs</a> */
    RangeNotSatisfiable(416, "Range Not Satisfiable"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/417">MDN Web Docs</a> */
    ExpectationFailed(417, "Expectation Failed"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/418">MDN Web Docs</a> */
    ImATeapot(418, "I'm a teapot"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/421">MDN Web Docs</a> */
    MisdirectedRequest(421, "Misdirected Request"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/422">MDN Web Docs</a> */
    UnprocessableContent(422, "Unprocessable Content"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/423">MDN Web Docs</a> */
    Locked(423, "Locked"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/424">MDN Web Docs</a> */
    FailedDependency(424, "Failed Dependency"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/425">MDN Web Docs</a> */
    TooEarly(425, "Too Early"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/426">MDN Web Docs</a> */
    UpgradeRequired(426, "Upgrade Required"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/428">MDN Web Docs</a> */
    PreconditionRequired(428, "Precondition Required"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/429">MDN Web Docs</a> */
    TooManyRequests(429, "Too Many Requests"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/431">MDN Web Docs</a> */
    RequestHeaderFieldsTooLarge(431, "Request Header Fields Too Large"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/451">MDN Web Docs</a> */
    UnavailableForLegalReasons(451, "Unavailable For Legal Reasons"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500">MDN Web Docs</a> */
    InternalServerError(500, "Internal Server Error"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/501">MDN Web Docs</a> */
    NotImplemented(501, "Not Implemented"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/502">MDN Web Docs</a> */
    BadGateway(502, "Bad Gateway"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/503">MDN Web Docs</a> */
    ServiceUnavailable(503, "Service Unavailable"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/504">MDN Web Docs</a> */
    GatewayTimeout(504, "Gateway Timeout"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/505">MDN Web Docs</a> */
    HTTPVersionNotSupported(505, "HTTP Version Not Supported"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/506">MDN Web Docs</a> */
    VariantAlsoNegotiates(506, "Variant Also Negotiates"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/507">MDN Web Docs</a> */
    InsufficientStorage(507, "Insufficient Storage"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/508">MDN Web Docs</a> */
    LoopDetected(508, "Loop Detected"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/510">MDN Web Docs</a> */
    NotExtended(510, "Not Extended"),
    
    /**@see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/511">MDN Web Docs</a> */
    NetworkAuthenticationRequired(511, "Network Authentication Required")
}
