package com.example.schedule_clone.domain.userevent

import com.example.schedule_clone.data.pref.UserEventMessage
import com.example.schedule_clone.domain.users.ReservationRequestAction
import com.example.schedule_clone.domain.users.StarUpdatedStatus
import com.example.schedule_clone.domain.users.SwapRequestAction
import com.example.schedule_clone.model.Session
import com.example.schedule_clone.model.SessionId
import com.example.schedule_clone.model.userdata.UserEvent
import com.example.schedule_clone.shared.result.Result
import kotlinx.coroutines.flow.Flow

interface UserEventDataSource {

    fun getObservableUserEvents(userId: String): Flow<UserEventsResult>

    fun getObservableUserEvent(userId: String, eventId: SessionId): Flow<UserEventResult>

    fun getUserEvents(userId: String): List<UserEvent>

    fun getUserEvent(userId: String, eventId: SessionId): UserEvent?

    /**
     * Toggle the isStarred status for an event.
     *
     * @param userId the userId ([FirebaseUser#uid]) of the current logged in user
     * @param userEvent the [UserEvent], which isStarred is going to be the updated status
     * @return the Result that represents the status of the star operation.
     */
    suspend fun startEvent(userId: String, userEvent: UserEvent): Result<StarUpdatedStatus>

    suspend fun recordFeedbackSent(
        userId: String,
        userEvent: UserEvent
    ): Result<Unit>

    suspend fun requestReservation(
        userId: String,
        session: Session,
        action: ReservationRequestAction
    ): Result<ReservationRequestAction>

    suspend fun swapReservation(
        userId: String,
        fromSession: Session,
        toSession: Session
    ): Result<SwapRequestAction>

    fun clearSingleEventSubscriptions()
}

data class UserEventsResult(
    val userEvents: List<UserEvent>,
    val userEventsMessage: UserEventMessage? = null
)

data class UserEventResult(
    val userEvent: UserEvent?,
    val userEventMessage: UserEventMessage? = null
)