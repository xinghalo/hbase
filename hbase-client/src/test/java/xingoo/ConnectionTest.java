package xingoo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ConnectionTest {
    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "localnode2,localnode5,localnode7");

        Connection connection = ConnectionFactory.createConnection(configuration);

        try(Table table = connection.getTable(TableName.valueOf("xingoo:test_v"))){
            Get get = new Get(Bytes.toBytes("1"));
            Result result = table.get(get);
            byte[] v = result.getValue(Bytes.toBytes("v"), Bytes.toBytes("c1"));
            System.out.println(new String(v));
        }
    }
}



