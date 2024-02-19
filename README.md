# Integrating Apache Spark and Kafka in my Spring Application

## Steps to run the system:

**1.** Use the command _make infra_

**2.** Start _SpringApacheSparkApplication_ and _SpringApacheKafkaApplication_

**3.** Send post request to _/api/v1/kafka/publishMessage_




### The message payload should follow the following convention
```
requestType:    <CLUSTERING, CLASSIFICATION, REGRESSION>
hasHeaders:     <TRUE, FALSE>
features:       List of features for input columns eg. ["col1", "col2", "col3"]
labelCol:       Column for testing
name:           A name for the payload
value:          Base64 encoded file
```