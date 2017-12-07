/*
MIT License

Copyright (c) 2017 Marcel Braghetto

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package notifications.framework

import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.*

data class BasicResponseBody(val message: String)

object ResponseBuilder {
    fun success(message: String): Response = buildResponse(OK, message)

    fun badRequest(message: String): Response = buildResponse(BAD_REQUEST, message)

    fun internalServerError(message: String): Response = buildResponse(INTERNAL_SERVER_ERROR, message)

    private fun buildResponse(statusCode: Response.Status, message: String): Response = Response.status(statusCode).entity(BasicResponseBody(message).toJson()).build()
}