package com.plac.emailPlac.controller

import com.plac.emailPlac.TO_API
import com.plac.emailPlac.TO_EMAIL_PLAC
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class KafkaController() {

    @Bean
    fun kafkaStreamTopollogy(kStreamBuilder: StreamsBuilder): KStream<String, String> {
        val stream: KStream<String, String> = kStreamBuilder.stream(TO_EMAIL_PLAC)
        stream
            .peek( {x, y -> println("moj stream: x: $x i y: $y") })
            .to(TO_API)
        return stream
    }
}