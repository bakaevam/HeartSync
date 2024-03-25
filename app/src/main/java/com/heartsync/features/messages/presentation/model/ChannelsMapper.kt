package com.heartsync.features.messages.presentation.model

import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import io.getstream.chat.android.models.Channel
import java.time.Duration

class ChannelsMapper(
    private val textProvider: TextProvider,
    private val dateTimeRepository: DateTimeRepository,
) {

    fun toUiChatItem(
        channel: Channel,
        userId: String,
    ): UiChatItem {
        val lastMessage = channel.messages.lastOrNull()
        val channelName = channel.name.ifEmpty {
            channel.members.firstOrNull { it.user.id != userId }?.user?.name.takeIf { !it.isNullOrEmpty() }
                ?: textProvider.getString(R.string.messages_default_channel_name)
        }
        val lastMessageTime =
            lastMessage?.createdAt?.time?.let { DateMapper.millisToLocalDateTime(it) }
        val time = lastMessageTime?.let {
            val currentDateTime = dateTimeRepository.getCurrentDateTime()
            val difference = Duration.between(lastMessageTime, currentDateTime).toMillis()
            val days = DateMapper.getDays(difference)
            when (days < INT_ONE) {
                true -> {
                    val hours = DateMapper.getHours(difference)
                    val minutes = DateMapper.getMinutes(difference)
                    if (hours >= INT_ONE) {
                        textProvider.getString(R.string.messages_hours, hours)
                    } else if (minutes >= INT_ONE) {
                        textProvider.getString(R.string.messages_minute, minutes)
                    } else {
                        textProvider.getString(R.string.messages_now)
                    }
                }

                else -> {
                    DateMapper.formatDayMonthYear(lastMessageTime.toLocalDate())
                }
            }
        }
        return UiChatItem(
            cid = channel.cid,
            image = channel.image,
            name = channelName,
            lastMessage = lastMessage?.text ?: textProvider.getString(R.string.messages_empty_text),
            time = time ?: EMPTY_STRING,
            unreadMessages = channel.unreadCount,
        )
    }
}