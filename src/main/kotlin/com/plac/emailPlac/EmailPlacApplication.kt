package com.plac.emailPlac

import org.apache.kafka.streams.KafkaStreams
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class EmailPlacApplication

fun main(args: Array<String>) {
	runApplication<EmailPlacApplication>(*args)
	println("\nApp 'emailPlac' has started\n")

}
