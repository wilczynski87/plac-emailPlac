package com.plac.emailPlac

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig.*
import org.apache.kafka.streams.kstream.*
import org.apache.kafka.streams.kstream.internals.TimeWindow
import org.apache.kafka.streams.processor.WallclockTimestampExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import java.time.Duration
import java.util.*
import java.util.Map
import java.util.stream.Stream


const val TO_EMAIL_PLAC: String = "toEmailPlacTopic"
const val TO_API: String = "toApiTopic"


@Configuration
@EnableKafkaStreams
class KafkaConfig {
}