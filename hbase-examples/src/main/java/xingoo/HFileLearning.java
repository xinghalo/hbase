package xingoo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.io.hfile.HFilePrettyPrinter;
import org.apache.hadoop.util.ToolRunner;

public class HFileLearning {
    public static void main(String[] args) throws Exception {
        String[] myargs = "-m -p -f /hbase/data/test/sankey/756c30103450cad5ef0336eaa30d4c94/t/4b963a8f4ed445c78d3106e86a49f17b".split(" ");
        Configuration conf = HBaseConfiguration.create();
        conf.setFloat(HConstants.HFILE_BLOCK_CACHE_SIZE_KEY, 0);
        int ret = ToolRunner.run(conf, new HFilePrettyPrinter(conf), myargs);
        System.exit(ret);
    }
}
