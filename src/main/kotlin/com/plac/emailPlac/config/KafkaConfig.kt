package com.plac.emailPlac.config

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams


const val TO_EMAIL_PLAC: String = "toEmailPlacTopic"
const val TO_API: String = "toApiTopic"
const val TO_EMAIL_PLAC_PRINT: String = "printInvoices"


@Configuration
@EnableKafkaStreams
class KafkaConfig {
}