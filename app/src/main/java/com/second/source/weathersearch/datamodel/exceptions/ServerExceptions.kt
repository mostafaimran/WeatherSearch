package com.second.source.weathersearch.datamodel.exceptions

class ServerException(message: String) : Exception(message)
class LocalException(message: String) : Exception(message)
class HttpServerException(code: Int) : Exception()
class ClientRequestException(code: Int, message: String) :
    Exception("http code : $code, message : $message")

class NetworkException(cause: Throwable?) : Exception(cause) {
}
