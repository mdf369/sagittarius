package com.sagittarius.write;

import com.sagittarius.bean.common.MetricMetadata;
import com.sagittarius.bean.common.TimePartition;
import com.sagittarius.exceptions.*;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface Writer {
    /**
     * register host and it's metrics(metric metadata).you can register metrics to a host at any time when you need to,
     * new metrics will be add to this host, duplicate metrics(metric metadata with the same metric field) will overwrite previous.
     * @param host the host name(or id)
     * @param metricMetadatas metrics info which belong to this host
     */
    void registerHostMetricInfo(String host, List<MetricMetadata> metricMetadatas) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    /**
     * register tags to a host. you can register tags to a host at any time when you need to,
     * new tags will be added to this host, duplicate tags will overwrite previous.
     * @param host the host name(or id)
     * @param tags a collection of tag_name:tag_value pairs
     */
    void registerHostTags(String host, Map<String, String> tags) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    /**
     * manually update the latest time_slice.
     * @param host the host name
     * @param metric the metric id
     * @param latestTime time string format yyyy-mm-dd hh:mm:ss
     */
    void updateLatestTimeSlice(String host, String metric, String latestTime) throws ParseException;

    /**
     * manually update the latest time_slice.
     * @param host  the host name
     * @param metric    the metric id
     * @param latestTime long type timestamp
     */
    void updateLatestTimeSlice(String host, String metric, long latestTime);


    /**
     * insert a metric data point info whose metric value type is INT.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, int value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, int value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is LONG.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, long value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, long value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is FLOAT.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, float value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, float value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is DOUBLE.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, double value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, double value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is BOOLEAN.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, boolean value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, boolean value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is STRING.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, String value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, String value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is GEO.
     * for geo data there are fields: latitude and longitude.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param latitude latitude value
     * @param longitude longitude value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, float latitude, float longitude) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, float latitude, float longitude) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;

    /**
     * insert a metric data point info whose metric value type is DOUBLE.
     * there are two time parameters: primaryTime and secondaryTime.
     * primaryTime is more important than secondaryTime as their names imply.
     * the concern is there may be two time for a data point, one can be the
     * create time and another can be the receive time. You should set a time
     * which you think most important as the primaryTime. Besides, primaryTime
     * must have a meaningful value while secondaryTime can be null by setting
     * it to -1.
     * we provide a method without the TimePartition parameter, in this method
     * we will automatically query the timePartition information. if the device
     * or sensor doesn't exsist, the method will throw a Unregistered device or
     * sensor Exception.
     * the no-timepartition-parm method will also check if the datatype of parameter VALUE
     * and the registered sensor's datatype matches, or it will throw an
     * datatype mismatch Exception.
     * @param host the host name(or id)
     * @param metric the metric name(or id)
     * @param primaryTime timestamp in millisecond, must have a meaningful value
     * @param secondaryTime timestamp in millisecond, -1 means null
     * @param timePartition time partition
     * @param value the metric value
     */
    void insert(String host, String metric, long primaryTime, long secondaryTime, TimePartition timePartition, ByteBuffer value) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void insert(String host, String metric, long primaryTime, long secondaryTime, ByteBuffer value) throws NoHostAvailableException, TimeoutException, QueryExecutionException, UnregisteredHostMetricException, DataTypeMismatchException;


    void insert(String host, String metric, long timeSlice, double maxValue, double minValue, double countValue, double sumValue) throws NoHostAvailableException, TimeoutException, QueryExecutionException;

    void deleteRange(List<String> hosts, List<String> metrics, long startTime, long endTime) throws QueryExecutionException, TimeoutException, NoHostAvailableException;

    void closeSender();
}
