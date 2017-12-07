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

package notifications.handler

import notifications.amazon.Amazon
import notifications.framework.Platform
import notifications.framework.ResponseBuilder
import notifications.framework.parse
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * This handler demonstrates how a client device could 'register' itself to our server with its push
 * notification token. Because Amazon requires knowledge of which platform tokens belong to, the client
 * needs to supply its platform on registration.
 *
 * In this example, we are simulating a particular 'userId' registering, to create an association to
 * a pretend 'user' with the given push notification token.
 */
@Path("/register")
class RegistrationHandler {
    data class RequestBody(val platform: String, val userId: String, val token: String)

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun registerToken(json: String): Response {
        json.parse<RequestBody>()?.run {
            return when (platform) {
                Platform.IOS.jsonKey -> if (Amazon.registerEndPoint(Platform.IOS, userId, token)) {
                    ResponseBuilder.success("iOS token registered successfully")
                } else {
                    ResponseBuilder.internalServerError("iOS token failed to update")
                }

                Platform.ANDROID.jsonKey -> if (Amazon.registerEndPoint(Platform.ANDROID, userId, token)) {
                    ResponseBuilder.success("Android token registered successfully")
                } else {
                    ResponseBuilder.internalServerError("Android token failed to update")
                }

                else -> ResponseBuilder.badRequest("Missing valid platform type")
            }
        }

        return ResponseBuilder.badRequest("Bad registration request or request")
    }
}
