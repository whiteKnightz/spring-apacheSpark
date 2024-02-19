package com.whiteKnightz.spark.springapacheSpark.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteKnightz.spark.springapacheSpark.domain.entity.EventEntity;
import com.whiteKnightz.spark.springapacheSpark.dto.MessageEvent;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.Evaluator;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.DecisionTreeRegressionModel;
import org.apache.spark.ml.regression.DecisionTreeRegressor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparkServiceImpl implements SparkService {
    @Autowired
    private SparkSession sparkSession;

    private final MessageEventService eventService;

    SparkServiceImpl(MessageEventService eventService){
        this.eventService = eventService;
    }

    public String readStaticFile() {
        Dataset<Row> dataset = sparkSession.read().option("header", "true").csv("../apacheSpark/src/main/resources/raw_data.csv");
        return String.format("<h1>%s</h1>", "Running Apache Spark on/with support of Spring boot") +
                String.format("<h2>%s</h2>", "Spark version = " + sparkSession.sparkContext().version()) +
                String.format("<h3>%s</h3>", "Read csv..") +
                String.format("<h4>Total records %d</h4>", dataset.count()) +
                String.format("<h5>Schema <br/> %s</h5> <br/> Sample data - <br/>", dataset.schema().treeString()) +
                dataset.showString(20, 20, true);
    }

    @Override
    public void performClustering(MessageEvent messageEvent) {
        Dataset<Row> data = sparkSession.read().format("csv")
                .option("header", true).option("inferSchema", true)
                .load(getPathForTempFile(messageEvent).getAbsolutePath());

        // Preprocess data (replace with your specific logic)
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(messageEvent.getFeatures().toArray(new String[0]))
                .setOutputCol("features");
        data = assembler.transform(data);


        // Split data into training and testing sets
        Dataset<Row>[] trainTest = data.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = trainTest[0];
        Dataset<Row> testData = trainTest[1];

        // Define KMeans and train model
        KMeans kmeans = new KMeans().setK(3); // Adjust number of clusters
        KMeansModel model = kmeans.fit(trainingData);

        // Predict clusters and assign labels
        Dataset<Row> clusteredData = model.transform(testData);
//        List<Row> cluster = clusteredData.select("cluster").distinct().collectAsList();
//        System.out.println("KMeans cluster labels: " + cluster);

        HashMap<String, Object> map = new HashMap<>();
        map.put("Centre-1", model.clusterCenters()[0].toArray());
        map.put("Centre-2", model.clusterCenters()[1].toArray());
        map.put("Centre-3", model.clusterCenters()[2].toArray());

        persistEntity(messageEvent, map);

    }

    @Override
    public void performRegression(MessageEvent messageEvent) {
        Dataset<Row> data = sparkSession.read().format("csv")
                .option("header", messageEvent.getHasHeaders())
                .load(getPathForTempFile(messageEvent).getAbsolutePath());

        // Preprocess data (replace with your specific logic)
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(messageEvent.getFeatures().toArray(new String[0]))
                .setOutputCol("features");
        data = assembler.transform(data);


        // Split data into training and testing sets
        Dataset<Row>[] trainTest = data.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = trainTest[0];
        Dataset<Row> testData = trainTest[1];

        // Define regression tree and train model
        DecisionTreeRegressor dtr = new DecisionTreeRegressor()
                .setMaxDepth(5) // Adjust max depth as needed
                .setImpurity("mse");
        DecisionTreeRegressionModel model = dtr.fit(trainingData);

        // Make predictions and evaluate
        Dataset<Row> predictions = model.transform(testData);
        Evaluator evaluator = new RegressionEvaluator()
                .setLabelCol(messageEvent.getLabelCol())
                .setPredictionCol("prediction")
                .setMetricName("rmse");
        double rmse = evaluator.evaluate(predictions);
        System.out.println("Decision Tree Regression RMSE: " + rmse);

        persistEntity(messageEvent, Collections.singletonMap("Root Mean Sq. Error", rmse));
    }

    @Override
    public void performClassification(MessageEvent messageEvent) {
        Dataset<Row> data = sparkSession.read()
                .format("csv")
                .option("header", true)
                .load(getPathForTempFile(messageEvent).getAbsolutePath());

        // Preprocess data (replace with your specific logic)
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(messageEvent.getFeatures().toArray(new String[0]))
                .setOutputCol("features");
        data = assembler.transform(data);


        // Split data into training and testing sets
        Dataset<Row>[] trainTest = data.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = trainTest[0];
        Dataset<Row> testData = trainTest[1];


        MultilayerPerceptronClassifier classifier = new MultilayerPerceptronClassifier()
                .setLayers(new int[]{4, 5, 3}) // Adjust layers and neurons as needed
                .setBlockSize(128)
                .setSeed(1L);
        MultilayerPerceptronClassificationModel model = classifier.fit(trainingData);

        // Make predictions and evaluate
        Dataset<Row> predictions = model.transform(testData);
        Evaluator evaluator = new MulticlassClassificationEvaluator()
                .setLabelCol(messageEvent.getLabelCol())
                .setPredictionCol("prediction");
        double accuracy = evaluator.evaluate(predictions);
        System.out.println("Multilayer Perceptron accuracy: " + accuracy);


        persistEntity(messageEvent, Collections.singletonMap("accuracy", accuracy));
    }

    private File getPathForTempFile(MessageEvent messageEvent) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] encodedBytes = mapper.convertValue(messageEvent.getValue(), byte[].class);
        // String decodedString = new String(mapper.convertValue(encodedBytes, String.class));

        File tempFile = null;
        try {
            tempFile = File.createTempFile("decoded_file", ".tmp");
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(encodedBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    private void persistEntity(MessageEvent messageEvent, Map<String, Object> map) {
        EventEntity eventEntity = EventEntity.createFromMessageEvent(messageEvent);
        eventEntity.setMap(map);
        eventService.persist(eventEntity);
    }
}
