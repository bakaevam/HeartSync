package com.heartsync.features.messages.presentation.model

import com.heartsync.R
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.core.tools.format.DateTimeFormatter
import com.heartsync.features.chat.presentation.model.UiMessage
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.core.internal.InternalStreamChatApi
import io.getstream.chat.android.models.Channel
import io.getstream.chat.android.models.Message
import io.getstream.chat.android.ui.common.utils.extensions.isMine
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
        val channelName = getName(channel, userId)
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

    @OptIn(InternalStreamChatApi::class)
    fun toUiMessage(
        message: Message,
        chatClient: ChatClient,
    ): UiMessage {
        val createdAt = message.createdAt?.time?.let { DateMapper.millisToLocalDateTime(it) }
        return UiMessage(
            isMine = message.isMine(chatClient),
            text = message.text,
            time = createdAt?.toLocalTime()?.let { DateTimeFormatter.formatTime(it) }
                ?: EMPTY_STRING,
            // TODO find isRead()
            read = false,
        )
    }

    fun getName(
        channel: Channel,
        userId: String,
    ): String {
        return channel.name.ifEmpty {
            channel.members.firstOrNull { it.user.id != userId }?.user?.name.takeIf { !it.isNullOrEmpty() }
                ?: textProvider.getString(R.string.messages_default_channel_name)
        }
    }
}