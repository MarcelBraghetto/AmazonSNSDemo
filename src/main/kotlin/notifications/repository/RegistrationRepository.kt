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

package notifications.repository

/**
 * The registration repository is responsible for holding the Amazon ARNs that represent each 'endpoint', and
 * whatever business required mapping of those endpoints to users of the mobile applications. In a real application
 * these registration records would be persisted into storage somewhere, but for this demo app they only live in
 * memory when the server is running.
 */
object RegistrationRepository {
    private val registrations: MutableMap<String, MutableList<Registration>> = mutableMapOf()

    fun storeRegistration(registration: Registration) {
        val userId = registration.userId
        var userRegistrations = registrations[userId]

        if (userRegistrations == null) {
            userRegistrations = mutableListOf(registration)
            registrations[userId] = userRegistrations
        }

        userRegistrations.firstOrNull { it.endpointArn == registration.endpointArn } ?: userRegistrations.add(registration)
    }

    fun getRegistrations(userId: String) = registrations[userId]?.toList() ?: listOf()
}