package com.example.contract.service

import com.example.contract.util.Constants
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaConnection {
    val props = Properties()



    init {
        setProperties()
    }

    fun setProperties(){
        connectConsumer()
        connectProducer()
    }

    fun connectConsumer() {

            val props = Properties()
            props["bootstrap.servers"] = "10.0.120.81:9092"
            props["key.serializer"] = StringSerializer::class.java.canonicalName
            props["value.serializer"] = StringSerializer::class.java.canonicalName

            val producer = KafkaProducer<String, String>(props)
            for (i in 1..1000) {
                val record = ProducerRecord<String, String>("mytopic", "value-$i")
                producer.send(record)
                Thread.sleep(250)
            }

            producer.close()

    }

    fun connectProducer() {


            val props = Properties()
            props["bootstrap.servers"] = "10.0.120.81:9092"
            props["group.id"] = "mygroup"
            props["key.deserializer"] = StringDeserializer::class.java.canonicalName
            props["value.deserializer"] = StringDeserializer::class.java.canonicalName

            val consumer = KafkaConsumer<String, String>(props)
            consumer.subscribe(listOf("mytopic"))

            val running = true
            while (running) {
                val records = consumer.poll(5)
                for (record in records) {
                    println(record.value())
                }
            }

            consumer.close()

    }


}

