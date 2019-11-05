package xingoo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class ScanTest {
    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "localnode2,localnode5,localnode7");

        Connection connection = ConnectionFactory.createConnection(configuration);

        try(Table table = connection.getTable(TableName.valueOf("rec:user_product_rec"))){
            Scan scan = new Scan();
            scan.setMaxResultSize(2);
            ResultScanner scanner4 = table.getScanner(scan);

            for (Result res : scanner4) {
                System.out.println(res);
            }
        }
    }
}
