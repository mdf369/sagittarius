package com.sagittarius.example;

import com.datastax.driver.core.Cluster;
//import com.datastax.spark.connector.japi.CassandraRow;
//import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
//import com.datastax.spark.connector.util.Symbols;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.WriteTimeoutException;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.sagittarius.bean.common.MetricMetadata;
import com.sagittarius.bean.common.TimePartition;
import com.sagittarius.bean.common.ValueType;
import com.sagittarius.bean.query.AggregationType;
import com.sagittarius.bean.query.Shift;
import com.sagittarius.bean.result.DoublePoint;
import com.sagittarius.bean.result.FloatPoint;
import com.sagittarius.bean.result.IntPoint;
import com.sagittarius.bean.result.StringPoint;
import com.sagittarius.bean.table.DoubleData;
import com.sagittarius.core.SagittariusClient;
import com.sagittarius.exceptions.NoHostAvailableException;
import com.sagittarius.exceptions.QueryExecutionException;
import com.sagittarius.exceptions.SparkException;
import com.sagittarius.exceptions.TimeoutException;
import com.sagittarius.read.Reader;
import com.sagittarius.read.SagittariusReader;
import com.sagittarius.util.TimeUtil;
import com.sagittarius.write.SagittariusWriter;
import com.sagittarius.write.Writer;
//import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import scala.Tuple22;
//import scala.tools.cmd.gen.AnyVals;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

//import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static java.lang.System.exit;


public class Example {
    private static final Logger logger = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) throws IOException, QueryExecutionException, TimeoutException, NoHostAvailableException, ParseException, SparkException, InterruptedException {
        CassandraConnection connection = CassandraConnection.getInstance();
        Cluster cluster = connection.getCluster();
//        int threads = Integer.parseInt(args[0]);
//        SparkConf sparkConf = new mvnSparkConf();
////        sparkConf.setMaster("spark://192.168.3.17:7077").setAppName("test");
//        sparkConf.setMaster("spark://192.168.15.114:7077").setAppName("kmxtest");
//        sparkConf.set("spark.ui.port", "4044");
//        //to fix the can't assign from .. to .. Error
//        String[] jars = {"examples-1.0-SNAPSHOT-jar-with-dependencies.jar"};
//        sparkConf.setJars(jars);
//        sparkConf.set("spark.cassandra.connection.host", "192.168.15.114");
//        sparkConf.set("spark.cassandra.connection.port", "9042");
//        sparkConf.set("spark.cassandra.connection.keep_alive_ms", "600000");
//        sparkConf.set("spark.driver.host","192.168.15.123");
//        sparkConf.set("spark.executor.memory", "2g");
//        SagittariusClient client = new SagittariusClient(cluster, 10000);
//        SagittariusWriter writer = (SagittariusWriter) client.getWriter();
//        SagittariusReader reader = (SagittariusReader)client.getReader();
//        ReadTask task1 = new ReadTask(reader, time, "value >= 33 and value <= 34");
//        ReadTask task2 = new ReadTask(reader, time, "value >= 34 and value <= 35");
//        ReadTask task3 = new ReadTask(reader, time, "value >= 35 and value <= 36");
        //batchTest(writer, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //batchTest1(client, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //test(client.getSparkContext());
        //floatRead(reader);
//        for (int i = 0; i < 100; i++){
//            writer.insert("1", "2", System.currentTimeMillis(), -1, TimePartition.DAY, 5);
//            Thread.sleep(100);
//        }
//        writer.closeSender();
//        Thread.sleep(10000);
//        insert(writer);
//        TestTask testTask = new TestTask(reader);
//        testTask.start();
        //floatRead(reader);
        //logger.info("consume time: " + (System.currentTimeMillis() - time) + "ms");
        //registerHostMetricInfo(writer);
        //registerHostTags(writer);
        //registerOwnerInfo(writer);
        //insert(writer);
        //writeTest(writer, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //batchWriteTest(writer, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
//        batchWriteTest(writer, 1, 1, 1000);
        //insertLoop(writer);

        //read(reader);
        //readbyRange(reader);
        //readFuzzy(reader);
//        long st = System.currentTimeMillis();
//        floatRead(reader);
//        System.out.println(cluster.getClusterName());
//        cluster.getMetadata().getAllHosts().size();
//        System.out.println(TimeUtil.date2String(1493568000123L));
//        System.out.println(TimeUtil.string2Date("2017-05-01 00:00:00.123"));
//        test(reader);
//        int threads = Integer.valueOf(args[0]);
//        int batchSize = Integer.valueOf(args[1]);
//        String directory = args[2];
//        batchWriteBigData2(writer, threads, batchSize, directory);

//        deleteTest(reader, writer);
//        writeForReadTest(writer);
        readForReadTest(cluster, 0);
        exit(0);

    }

    private static void readForReadTest(Cluster cluster, int threads) throws ParseException, NoHostAvailableException, QueryExecutionException, TimeoutException, InterruptedException {
        Session session = cluster.connect("sagittarius");

        ArrayList<String> querys = new ArrayList<>();
        querys.add("select * from sagittarius.data_test where host in ('11') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('12','13') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('13','15','17') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('14','16','18','20') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('15','19','21','23','25') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('16','18','20','22','24','26') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('17','19','21','23','25','27','29') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('18','20','22','24','26','28','30','32') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('19','21','23','25','27','29','31','33','35') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('20','22','24','26','28','30','32','34','36','38') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('19','21','23','25','27','29','31','33','35','37','39') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('20','22','24','26','28','30','32','34','36','38','40','42') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('21','23','25','27','29','31','33','35','37','39','41','43','45') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('21','23','25','27','29','31','33','35','37','39','41','43','45','47') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('24','26','28','30','32','34','36','38','40','42','44','46','48','50','52') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('21','23','25','27','29','31','33','35','37','39','41','43','45','47','49','51') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('26','28','30','32','34','36','38','40','42','44','46','48','50','52','54','56','58') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('25','27','29','31','33','35','37','39','41','43','45','47','49','51','53','55','57','59') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('30','32','34','36','38','40','42','44','46','48','50','52','54','56','58','59','62','64','66') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");
        querys.add("select * from sagittarius.data_test where host in ('27','29','31','33','35','37','39','41','43','45','47','49','51','53','55','57','59','61','63','65') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19')");

        for(int i = 0; i < 20; i++){
            Long startTime = System.currentTimeMillis();
            ResultSet resultSet = session.execute(querys.get(i));
            resultSet.all().size();
            Long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }



//        ArrayList<String> querys = new ArrayList<>();
//        querys.add("select * from sagittarius.data_test where host in ('0','10','20','30','40','50','60','70','80','90') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('1','11','21','31','41','51','61','71','81','91') and time_slice in ('2017D245') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('2','12','22','32','42','52','62','72','82','92') and time_slice in ('2017D246') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('3','13','23','33','43','53','63','73','83','93') and time_slice in ('2017D247') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('4','14','24','34','44','54','64','74','84','94') and time_slice in ('2017D248') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('5','15','25','35','45','55','65','75','85','95') and time_slice in ('2017D249') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('6','16','26','36','46','56','66','76','86','96') and time_slice in ('2017D250') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('7','17','27','37','47','57','67','77','87','97') and time_slice in ('2017D244') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('8','18','28','38','48','58','68','78','88','98') and time_slice in ('2017D245') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('9','19','29','39','49','59','69','79','89','99') and time_slice in ('2017D246') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('0','10','20','30','40','50','60','70','80','90') and time_slice in ('2017D247') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('1','12','23','34','45','56','67','78','89','90') and time_slice in ('2017D248') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('9','18','27','36','45','54','63','72','81','92') and time_slice in ('2017D249') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//        querys.add("select * from sagittarius.data_test where host in ('6','13','27','34','41','52','65','78','83','97') and time_slice in ('2017D250') and metric in ('0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19') ");
//
//        int num = threads;
//
//        List<ParallelReadThread> tasks = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            ParallelReadThread task = new ParallelReadThread(session, i, querys.get(i));
//            task.start();
//            tasks.add(task);
//        }
//
//        boolean finished = true;
//        while (true){
//            for(ParallelReadThread task : tasks){
//                finished = finished && task.isFinished();
//            }
//            if (!finished) {
//                finished = true;
//                Thread.sleep(1000);
//            }
//            else{
//                break;
//            }
//        }
    }

    private static void writeForReadTest(SagittariusWriter writer) throws ParseException, NoHostAvailableException, QueryExecutionException, TimeoutException {
        Random random = new Random();
        for(Integer i = 0; i < 100; i++){
            String device = i.toString();
            for(Integer j = 0; j < 20; j++){
                String sensor = j.toString();
                for(Integer k = 0; k < 7; k++){
                    long priTime = TimeUtil.string2Date("2017-09-01 00:00:00");
                    priTime += k * 86400000;
                    SagittariusWriter.Data data = writer.newData();
                    for(int s = 0; s < 1000; s++){
                        data.addDatum(device, sensor, priTime+s*1000, priTime+s*1000, TimePartition.DAY, random.nextDouble() * 100);
                    }
                    writer.bulkInsert(data);
                    System.out.println(device + " : " + sensor + " : " + k);
                }
            }
        }
    }

    private static void countInfo(SagittariusReader reader){

    }

    private static void deleteTest(SagittariusReader reader, SagittariusWriter writer) throws ParseException, NoHostAvailableException, QueryExecutionException, TimeoutException, SparkException {
        ArrayList<String> hosts = new ArrayList<>();
        hosts.add("128934");
//        hosts.add("128579");
//        hosts.add("130889");
//        hosts.add("131979");
//        hosts.add("135931");
//        hosts.add("139307");
//        hosts.add("129007");
//        hosts.add("130894");
//        hosts.add("130966");
//        hosts.add("131967");
//        hosts.add("133678");
//        hosts.add("134738");
//        hosts.add("135896");
//        hosts.add("135922");
//        hosts.add("139025");
//        hosts.add("139334");
        ArrayList<String> metrics = new ArrayList<>();
//        metrics.add("加速踏板位置1");
//        metrics.add("当前转速下发动机负载百分比");
//        metrics.add("实际发动机扭矩百分比");
//        metrics.add("发动机转速");
//        metrics.add("高精度总里程(00)");
//        metrics.add("总发动机怠速使用燃料");
//        metrics.add("后处理1排气质量流率");
//        metrics.add("总发动机操作时间");
//        metrics.add("总发动机使用的燃油");
//        metrics.add("发动机冷却液温度");
//        metrics.add("基于车轮的车辆速度");
//        metrics.add("发动机燃料消耗率");
        metrics.add("大气压力");
//        metrics.add("发动机进气歧管1压力");
//        metrics.add("发动机进气歧管1温度");
//        metrics.add("发动机进气压力");
//        metrics.add("车速");
//        metrics.add("大气温度");
//        metrics.add("发动机进气温度");
//        metrics.add("高精度总里程(EE)");
//        metrics.add("后处理1进气氮氧化物浓度");
        long startTime = TimeUtil.string2Date("2016-06-02 15:00:00");
        long endTime = TimeUtil.string2Date("2016-06-05 12:00:00");
//        1464840000000
//        1464883200000
//        1465099200000
//        1465056000000
//        reader.preAggregateFunction(hosts, metrics, startTime, endTime, writer);
//        Map<String, Map<String, List<FloatPoint>>> result5 = reader.getFloatRange(hosts, metrics, startTime,endTime,false);
//        Map<String, Map<String, Double>> result = reader.getFloatRange(hosts, metrics, startTime, endTime, null, AggregationType.COUNT);
//        Map<String, Map<String, Double>> result3 = reader.getFloatRange(hosts, metrics, startTime, endTime, null, AggregationType.COUNT);
//        Map<String, Map<String, Double>> result2 = reader.getAggregationRange(hosts, metrics, startTime, endTime, null, AggregationType.COUNT);
//        System.out.println(result.get(hosts.get(0)).get(metrics.get(0)));
//        System.out.println(result3.get(hosts.get(0)).get(metrics.get(0)));
//        System.out.println(result5.get(hosts.get(0)).get(metrics.get(0)).size());
    }

    private static void preAggTest(SagittariusReader reader, SagittariusWriter writer) throws ParseException, QueryExecutionException, TimeoutException, NoHostAvailableException, SparkException, IOException {
        ArrayList<String> hosts = new ArrayList<>();
        hosts.add("128934");
//        hosts.add("128579");
//        hosts.add("130889");
//        hosts.add("131979");
//        hosts.add("135931");
//        hosts.add("139307");
//        hosts.add("129007");
//        hosts.add("130894");
//        hosts.add("130966");
//        hosts.add("131967");
//        hosts.add("133678");
//        hosts.add("134738");
//        hosts.add("135896");
//        hosts.add("135922");
//        hosts.add("139025");
//        hosts.add("139334");
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add("加速踏板位置1");
        metrics.add("当前转速下发动机负载百分比");
        metrics.add("实际发动机扭矩百分比");
        metrics.add("发动机转速");
        metrics.add("高精度总里程(00)");
        metrics.add("总发动机怠速使用燃料");
        metrics.add("后处理1排气质量流率");
        metrics.add("总发动机操作时间");
        metrics.add("总发动机使用的燃油");
        metrics.add("发动机冷却液温度");
        metrics.add("基于车轮的车辆速度");
        metrics.add("发动机燃料消耗率");
        metrics.add("大气压力");
        metrics.add("发动机进气歧管1压力");
        metrics.add("发动机进气歧管1温度");
        metrics.add("发动机进气压力");
        metrics.add("车速");
        metrics.add("大气温度");
        metrics.add("发动机进气温度");
        metrics.add("高精度总里程(EE)");
        metrics.add("后处理1进气氮氧化物浓度");
        long startTime = TimeUtil.string2Date("2016-06-01 00:00:00");
        long endTime = TimeUtil.string2Date("2016-06-08 00:00:00");
        FileWriter fileWritter = null;
        try {
            fileWritter = new FileWriter("D:\\ty\\PreAggTestResult" , true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        List<String> queryHosts = new ArrayList<>();
        for(String host : hosts){
            queryHosts.add(host);
            List<String> queryMetric = new ArrayList<>();
            for(String metric : metrics){
                queryMetric.add(metric);
                for(long i = 1; i < 31; i++){
                    long day = (long)(Math.random()*50);
                    System.out.println(day);
                    long queryStartTime = startTime + day * 86400000L;
                    long queryEndTime = queryStartTime + i * 86400000L;
                    long timer = System.currentTimeMillis();
                    reader.preAggregateFunction(queryHosts, queryMetric, queryStartTime, queryEndTime, writer);
//        Map<String, Map<String, Double>> result = reader.getFloatRange(hosts, metrics, startTime, endTime, null, AggregationType.COUNT);
//        Map<String, Map<String, Double>> result = reader.getAggregationRange(hosts, metrics, startTime, endTime, null, AggregationType.COUNT, ValueType.FLOAT);
                    timer = System.currentTimeMillis() - timer;
                    bufferWritter.write(queryHosts.size()*queryMetric.size()*i + ","+ timer + "\n");
                    bufferWritter.flush();
                }
            }
        }
//        System.out.println(startTime/86400000);
//        System.out.println(result == null);
//        for(String m : metrics){
//            System.out.println(result.get(hosts.get(0)).get(m));
//        }
    }

    public static void test(Reader reader) {
        ArrayList<String> hosts = new ArrayList<>();
        hosts.add("128934");
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add("当前转速下发动机负载百分比");
        metrics.add("实际发动机扭矩百分比");
        metrics.add("发动机转速");
        metrics.add("高精度总里程(00)");
        metrics.add("总发动机怠速使用燃料");

    }

    private static void IntRead(Reader reader){
        ArrayList<String> hosts = new ArrayList<>();
        hosts.add("clihost");
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add("climetric");
        Map<String, Map<String, IntPoint>> result = null;
        try {
            result = reader.getIntPoint(hosts, metrics, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.get("clihost").get("climetric").getValue());
    }

    private static void floatRead(Reader reader) throws ParseException {
        ArrayList<String> hosts = new ArrayList<>();
        hosts.add("130667");
        ArrayList<String> metrics = new ArrayList<>();
        metrics.add("PP_0001_00_16825352");
        long start = TimeUtil.string2Date("2017-05-01 23:00:00");
        System.out.println(start);
        long end = TimeUtil.string2Date("2017-05-02 00:00:00");
        System.out.println(end);
        Map<String, Map<String, List<FloatPoint>>> result = null;
        try {
            result = reader.getFloatRange(hosts, metrics, start, end, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.get("130667").get("PP_0001_00_16825352").get(0).getPrimaryTime());
    }

    private static void batchTest1(SagittariusClient client, int threads, int runTime) {
        List<String> hosts = new ArrayList<>();
        for (int i = 0; i < threads; ++i) {
            hosts.add("12828" + i);
        }
        List<BatchTest> tasks = new ArrayList<>();
        for (String host : hosts) {
            BatchTest task = new BatchTest(client.getWriter(), host, runTime);
            task.start();
            tasks.add(task);
        }

        long start = System.currentTimeMillis();
        long count = 0;
        long consume;
        while (true) {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (BatchTest task : tasks) {
                count += task.getCount();
            }
            consume = System.currentTimeMillis() - start;
            double throughput = count / ((double) consume / 1000);

            logger.info("throughput: " + throughput + ", count: " + count);
            count = 0;
        }
    }

    private static void batchTest(Writer writer, int threads, int runTime) {
        List<String> hosts = new ArrayList<>();
        for (int i = 0; i < threads; ++i) {
            hosts.add("12828" + i);
        }
        List<BatchTest> tasks = new ArrayList<>();
        for (String host : hosts) {
            BatchTest task = new BatchTest(writer, host, runTime);
            task.start();
            tasks.add(task);
        }

        long start = System.currentTimeMillis();
        long count = 0;
        long consume;
        while (true) {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (BatchTest task : tasks) {
                count += task.getCount();
            }
            consume = System.currentTimeMillis() - start;
            double throughput = count / ((double) consume / 1000);
            count = 0;
            logger.info("throughput: " + throughput + ", count: " + count);
        }
    }

    private static void batchWriteTest(Writer writer, int threads, int runTime, int batchSize) {
        List<String> hosts = new ArrayList<>();
        for (int i = 0; i < threads; ++i) {
            hosts.add("12828" + i);
        }
        List<BatchWriteTask> tasks = new ArrayList<>();
        for (String host : hosts) {
            BatchWriteTask task = new BatchWriteTask(writer, host, runTime, batchSize);
            task.start();
            tasks.add(task);
        }

        while (true) {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double throughput = 0;
            long count = 0;
            for (BatchWriteTask task : tasks) {
                throughput += task.getThroughput();
                count += task.getCount();
            }
            logger.info("throughput: " + throughput + ", count: " + count);
        }
    }

    private static void writeTest(Writer writer, int threads, int runTime) {
        List<String> hosts = new ArrayList<>();
        for (int i = 0; i < threads; ++i) {
            hosts.add("12828" + i);
        }
        List<WriteTask> tasks = new ArrayList<>();
        for (String host : hosts) {
            WriteTask task = new WriteTask(writer, host, runTime);
            task.start();
            tasks.add(task);
        }

        /*final LoadBalancingPolicy loadBalancingPolicy =
                cluster.getConfiguration().getPolicies().getLoadBalancingPolicy();
        final PoolingOptions poolingOptions =
                cluster.getConfiguration().getPoolingOptions();

        ScheduledExecutorService scheduled =
                Executors.newScheduledThreadPool(1);
        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Session.State state = client.getSession().getState();
                for (Host host : state.getConnectedHosts()) {
                    HostDistance distance = loadBalancingPolicy.distance(host);
                    int connections = state.getOpenConnections(host);
                    int inFlightQueries = state.getInFlightQueries(host);
                    System.out.printf("%s connections=%d, current load=%d, maxload=%d%n",
                            host, connections, inFlightQueries,
                            connections * poolingOptions.getMaxRequestsPerConnection(distance));
                }
            }
        }, 5, 5, TimeUnit.SECONDS);*/

        while (true) {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double throughput = 0;
            long count = 0;
            for (WriteTask task : tasks) {
                throughput += task.getThroughput();
                count += task.getCount();
            }
            logger.info("throughput: " + throughput + ", count: " + count);
        }
    }

    private static void read(Reader reader) {
        List<String> hosts = new ArrayList<>();
        hosts.add("128280");
        hosts.add("128290");
        List<String> metrics = new ArrayList<>();
        metrics.add("APP");
        Map<String, Map<String, DoublePoint>> result = null;
        try {
            result = reader.getDoublePoint(hosts, metrics, 1482319512851L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Map<String, DoublePoint>> entry : result.entrySet()) {
            //
        }
    }
    private static void readLatest(Reader reader) {
        List<String> hosts = new ArrayList<>();
        hosts.add("128280");
        hosts.add("128290");
        List<String> metrics = new ArrayList<>();
        metrics.add("APP");
        Map<String, Map<String, DoublePoint>> result = null;
        try {
            result = reader.getDoubleLatest(hosts, metrics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Map<String, DoublePoint>> entry : result.entrySet()) {
            //
        }
    }
    private static void readbyRange(Reader reader) {
        List<String> hosts = new ArrayList<>();
        //hosts.add("131980");
        System.out.println("查找");
        hosts.add("128280");
        hosts.add("1282835");
        List<String> metrics = new ArrayList<>();
        metrics.add("APP");
        LocalDateTime start = LocalDateTime.of(1993,10,11,0,0);
        LocalDateTime end = LocalDateTime.of(1993,10,14,5,59);
        Map<String, Map<String, List<DoublePoint>>> result = null;
        try {
            result = reader.getDoubleRange(hosts, metrics,start.toEpochSecond(TimeUtil.zoneOffset)*1000,end.toEpochSecond(TimeUtil.zoneOffset)*1000, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Map<String, List<DoublePoint>>> entry : result.entrySet()) {
            //
        }
    }

    private static void readFuzzy(Reader reader) {

        String host="128280";
        String metric="APP";
        DoublePoint point = null;
        try {
            point = reader.getFuzzyDoublePoint(host,metric,1483712410000L, Shift.NEAREST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertLoop(Writer writer) {
        LocalDateTime start = LocalDateTime.of(1993,10,12,0,0);
        LocalDateTime end = LocalDateTime.of(1993,10,14,23,59);
        System.out.println("插入");
        while (!start.isAfter(end)) {
            double value=Math.random()*100;
            try {
                writer.insert("1282835", "APP", start.toEpochSecond(TimeUtil.zoneOffset)*1000, start.toEpochSecond(TimeUtil.zoneOffset)*1000, TimePartition.DAY,value );
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("APP" + " " + start.toEpochSecond(TimeUtil.zoneOffset)*1000+" "+ start.toString()+ " " + value);
            start = start.plusHours(6);
        }
    }

    private static void insert(Writer writer) {
        long time1 = System.currentTimeMillis();
        long time2 = System.currentTimeMillis();
        System.out.println(time1);
        System.out.println(time2);
        //for (int i = 0; i < 3000; ++i) {
        try {
            writer.insert("128280", "APP", time1, -1, TimePartition.DAY, "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ++time1;
        //}
        logger.info("" + (System.currentTimeMillis() - time1));
        /*long start = System.currentTimeMillis();
        SagittariusWriter sWriter = (SagittariusWriter)writer;
        SagittariusWriter.Datas datas = sWriter.newDatas();
        long time = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 3000; ++i) {
            datas.addData("128280", "APP", time, time, TimePartition.DAY, random.nextDouble() * 100);
            ++time;
        }
        sWriter.bulkInsert(datas);
        logger.info(" " + (System.currentTimeMillis() - start));*/
    }

    private static void registerHostMetricInfo(Writer writer) {
        MetricMetadata metricMetadata1 = new MetricMetadata("APP", TimePartition.DAY, ValueType.DOUBLE, "加速踏板位置");
        MetricMetadata metricMetadata2 = new MetricMetadata("ECT", TimePartition.DAY, ValueType.INT, "发动机冷却液温度");
        List<MetricMetadata> metricMetadatas = new ArrayList<>();
        metricMetadatas.add(metricMetadata1);
        metricMetadatas.add(metricMetadata2);
        List<String> hosts = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            hosts.add("12828" + i);
        }
        for (String host : hosts) {
            long time = System.currentTimeMillis();
            try {
                writer.registerHostMetricInfo(host, metricMetadatas);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("consume time: " + (System.currentTimeMillis() - time) + "ms");
        }
    }

    private static void registerHostTags(Writer writer) {
        Map<String, String> tags = new HashMap<>();
        tags.put("price", "¥.10000");
        try {
            writer.registerHostTags("128280", tags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static float myParseFloat(String d){
        if(d.equals("")){
            return -1;
        }
        else {
            return Float.parseFloat(d);
        }
    }

//    private static void batchWriteBigData(SagittariusWriter writer, int threads, int batchSize, String directoryPath) throws IOException {
//
//        List<String> metrics = new ArrayList<>();
//        metrics.add("加速踏板位置1");
//        metrics.add("当前转速下发动机负载百分比");
//        metrics.add("实际发动机扭矩百分比");
//        metrics.add("发动机转速");
//        metrics.add("高精度总里程(00)");
//        metrics.add("总发动机怠速使用燃料");
//        metrics.add("后处理1排气质量流率");
//        metrics.add("总发动机操作时间");
//        metrics.add("总发动机使用的燃油");
//        metrics.add("发动机冷却液温度");
//        metrics.add("基于车轮的车辆速度");
//        metrics.add("发动机燃料消耗率");
//        metrics.add("大气压力");
//        metrics.add("发动机进气歧管1压力");
//        metrics.add("发动机进气歧管1温度");
//        metrics.add("发动机进气压力");
//        metrics.add("车速");
//        metrics.add("发动机扭矩模式");
//        metrics.add("大气温度");
//        metrics.add("发动机进气温度");
//        metrics.add("高精度总里程(EE)");
//        metrics.add("后处理1进气氮氧化物浓度");
//
//        //a map to store all datas
//        HashMap<String, ArrayList<Tuple22<FloatPoint,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float>>> map = new HashMap<>();
//
//        //get all data files
//        File[] fileList = new File(directoryPath).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                if (pathname.getName().endsWith(".csv"))
//                    return true;
//                return false;
//            }
//        });
//        System.out.println("the number of files = " + fileList.length);
//
//        //put every file's data into map
//        for (File file : fileList){
//            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            //skip the first line
//            String line = bf.readLine();
//            String host = "";
//            //array to store data lines
//            ArrayList<Tuple22<Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float>> rowDataSets = new ArrayList<>();
//            while ((line = bf.readLine()) != null){
//                String[] row = line.split(",", -1);
//                host = row[0];
//                try {
//                    long primaryTime = TimeUtil.string2Date(row[1], TimeUtil.dateFormat1);
//                    long secondaryTime = TimeUtil.string2Date(row[2], TimeUtil.dateFormat1);
//                } catch (Exception e) {
//                    continue;
//                }
//
//                Tuple22<Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float> rowData =
//                        new Tuple22<>(myParseFloat(row[3]),myParseFloat(row[4]),myParseFloat(row[5]),myParseFloat(row[6]),myParseFloat(row[7]),
//                                myParseFloat(row[8]),myParseFloat(row[9]),myParseFloat(row[10]),myParseFloat(row[11]),
//                                myParseFloat(row[12]),myParseFloat(row[13]),myParseFloat(row[14]),myParseFloat(row[15]),
//                                myParseFloat(row[16]),myParseFloat(row[17]),myParseFloat(row[18]),myParseFloat(row[19]),
//                                row[20],myParseFloat(row[21]),myParseFloat(row[22]),myParseFloat(row[23]),myParseFloat(row[24]));
//                rowDataSets.add(rowData);
//            }
//            if(map.containsKey(host)){
//                rowDataSets.addAll(map.get(host));
//                map.put(host,rowDataSets);
//            }
//            else {
//                map.put(host,rowDataSets);
//            }
//        }
//
//        System.out.println("put all data into map");
//        Set<String> hosts = map.keySet();
//        System.out.println("the number of hosts = " + hosts.size());
//        List<BatchWriteBigDataTask> tasks = new ArrayList<>();
//        long start = System.currentTimeMillis();
//        for(int i = 0; i < threads; i++){
//            BatchWriteBigDataTask task = new BatchWriteBigDataTask(writer, i, batchSize, metrics, map);
//            task.start();
//            tasks.add(task);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("time : " + (end-start));
//
//        long sumer = 0;
//        while (true){
//            try {
//                Thread.sleep(60000);
//            }catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            for (BatchWriteBigDataTask task : tasks) {
//                sumer += task.getThrought();
//            }
//            System.out.println(sumer/60);
//            sumer = 0;
//        }
//    }

//    private static void batchWriteBigData2(SagittariusWriter writer, int threads, int batchSize, String directoryPath) throws IOException {
//
//        List<String> metrics = new ArrayList<>();
//        metrics.add("加速踏板位置1");
//        metrics.add("当前转速下发动机负载百分比");
//        metrics.add("实际发动机扭矩百分比");
//        metrics.add("发动机转速");
//        metrics.add("高精度总里程(00)");
//        metrics.add("总发动机怠速使用燃料");
//        metrics.add("后处理1排气质量流率");
//        metrics.add("总发动机操作时间");
//        metrics.add("总发动机使用的燃油");
//        metrics.add("发动机冷却液温度");
//        metrics.add("基于车轮的车辆速度");
//        metrics.add("发动机燃料消耗率");
//        metrics.add("大气压力");
//        metrics.add("发动机进气歧管1压力");
//        metrics.add("发动机进气歧管1温度");
//        metrics.add("发动机进气压力");
//        metrics.add("车速");
//        metrics.add("发动机扭矩模式");
//        metrics.add("大气温度");
//        metrics.add("发动机进气温度");
//        metrics.add("高精度总里程(EE)");
//        metrics.add("后处理1进气氮氧化物浓度");
//
//        //a map to store all datas
//        HashMap<String, ArrayList<Tuple22<FloatPoint,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float>>> map = new HashMap<>();
//
//        //get all data files
//        File[] fileList = new File(directoryPath).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                if (pathname.getName().endsWith(".csv"))
//                    return true;
//                return false;
//            }
//        });
//        System.out.println("the number of files = " + fileList.length);
//
//        //put every file's data into map
//        for (File file : fileList){
//            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            //skip the first line
//            String line = bf.readLine();
//            String host = "";
//            //array to store data lines
//            ArrayList<Tuple22<FloatPoint,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float>> rowDataSets = new ArrayList<>();
//            while ((line = bf.readLine()) != null){
//                String[] row = line.split(",", -1);
//                host = row[0];
//                long primaryTime, secondaryTime;
//                try {
//                    primaryTime = TimeUtil.string2Date(row[1]);
//                    secondaryTime = TimeUtil.string2Date(row[2]);
//                } catch (Exception e) {
//                    System.out.println(file.getCanonicalPath());
//                    continue;
//                }
//
//                Tuple22<FloatPoint,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,Float,String,Float,Float,Float,Float> rowData =
//                        new Tuple22<>(
//                                new FloatPoint(metrics.get(0), primaryTime, secondaryTime, myParseFloat(row[3])),
//                                myParseFloat(row[4]),
//                                myParseFloat(row[5]),
//                                myParseFloat(row[6]),
//                                myParseFloat(row[7]),
//                                myParseFloat(row[8]),
//                                myParseFloat(row[9]),
//                                myParseFloat(row[10]),
//                                myParseFloat(row[11]),
//                                myParseFloat(row[12]),
//                                myParseFloat(row[13]),
//                                myParseFloat(row[14]),
//                                myParseFloat(row[15]),
//                                myParseFloat(row[16]),
//                                myParseFloat(row[17]),
//                                myParseFloat(row[18]),
//                                myParseFloat(row[19]),
//                                row[20],
//                                myParseFloat(row[21]),
//                                myParseFloat(row[22]),
//                                myParseFloat(row[23]),
//                                myParseFloat(row[24]));
//                rowDataSets.add(rowData);
//            }
//            if(map.containsKey(host)){
//                rowDataSets.addAll(map.get(host));
//                map.put(host,rowDataSets);
//            }
//            else {
//                map.put(host,rowDataSets);
//            }
//        }
//
//        System.out.println("put all data into map");
//        Set<String> hosts = map.keySet();
//        System.out.println("the number of hosts = " + hosts.size());
//        List<BatchWriteBigDataTask> tasks = new ArrayList<>();
//        long start = System.currentTimeMillis();
//        for(int i = 0; i < threads; i++){
//            BatchWriteBigDataTask task = new BatchWriteBigDataTask(writer, i, batchSize, metrics, map);
//            task.start();
//            tasks.add(task);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("time : " + (end-start));
//
//        long sumer = 0;
//        long recorder = sumer;
//        while (true){
//            try {
//                Thread.sleep(60000);
//            }catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            for (BatchWriteBigDataTask task : tasks) {
//                sumer += task.getThrought();
//            }
//            System.out.println("whentimeis " + TimeUtil.date2String(System.currentTimeMillis(), TimeUtil.dateFormat1) + ", " + (sumer-recorder)/60);
//            recorder = sumer;
//            sumer = 0;
//        }
//    }

    private static void batchWriteBigData3(SagittariusWriter writer, int threads, int batchSize, String directoryPath) throws IOException {

        //to compute the max number of frames devices sent to kmx per second

        List<Long> times = new ArrayList<>();
        //get all data files
        File[] fileList = new File(directoryPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".csv"))
                    return true;
                return false;
            }
        });
        System.out.println("the number of files = " + fileList.length);
        long time1 = 0;
        try {
            time1 = TimeUtil.string2Date("2016-06-02 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time2 = 0;
        try {
            time2 = TimeUtil.string2Date("2016-06-05 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //put every file's data into map
        for (File file : fileList){
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //skip the first line
            String line = bf.readLine();

            //array to store data lines
             while ((line = bf.readLine()) != null) {
                String[] row = line.split(",", -1);
                long primaryTime;
                try {
                    primaryTime = TimeUtil.string2Date(row[1]);
                } catch (Exception e) {
                    System.out.println(file.getCanonicalPath());
                    continue;
                }
                if(primaryTime >= time1 && primaryTime < time2){
                    times.add(primaryTime);
                }
            }



        }

        System.out.println(times.size());

        times.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });

        long temp = 0;
        long max = 0;
        long tempTime = times.get(0);
        for(int i = 1; i < times.size(); i++){
            if(times.get(i) == tempTime){
                temp += 1;
            }
            else {
                tempTime = times.get(i);
                max = temp > max ? temp : max;
                temp = 0;
            }
        }

        System.out.println(max);

        System.out.println("put all data into map");

    }
}
