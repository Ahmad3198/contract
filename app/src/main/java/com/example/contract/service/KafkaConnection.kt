package com.example.contract.service

import com.example.contract.util.Constants
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

class KafkaConnection {
    val props = Properties()
    var consumer = KafkaConsumer<String, String>(props)
    var producer = KafkaProducer<String, String>(props)

    init {
        setProperties()
    }

    fun setProperties(){
        props["bootstrap.servers"] = Constants.KAFKA_URL
        props["group.id"] = "mygroup"
        props["key.deserializer"] = StringDeserializer::class.java.canonicalName
        props["value.deserializer"] = StringDeserializer::class.java.canonicalName

        connectConsumer()
        connectProducer()
    }

    fun connectConsumer() {
        consumer = KafkaConsumer(props)
        consumer.subscribe(listOf("mytopic"))
        val running = true
        while (running) {
            val records = consumer.poll(100)
            for (record in records) {
                println(record.value())
            }
        }
    }

    fun connectProducer(){
        producer = KafkaProducer(props)
        for (i in 1..10000) {
            val record = ProducerRecord<String, String>("mytopic", "value-$i")
            producer.send(record)
            Thread.sleep(250)
        }
    }

    fun colseConnection(){
        consumer.close()
        producer.close()
    }
}

