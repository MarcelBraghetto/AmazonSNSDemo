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
import notifications.framework.ResponseBuilder
import notifications.framework.parse
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * This demo allows us to invoke a POST request to trigger the notification process. Its likely that in a real
 * implementation this functionality wouldn't be exposed through an API to consumers, however it could make for
 * an interesting peer messaging platform by allowing users to send notifications to each other.
 */
@Path("/send")
class SendNotificationHandler {
    data class RequestBody(val userId: String, val notificationTitle: String, val notificationBody: String)

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun triggerNotification(json: String): Response {
        json.parse<RequestBody>()?.run {
            Amazon.publishNotification(userId, notificationTitle, notificationBody)
            return ResponseBuilder.success("Notification title '$notificationTitle and message '$notificationBody' sent to user '$userId'")
        }

        return ResponseBuilder.badRequest("Bad registration request or request")
    }
}
