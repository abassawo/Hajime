package presentation

import data.Channel
import data.ChannelRaw

data class ChannelViewEntity(val channel: ChannelRaw) : Channel by channel