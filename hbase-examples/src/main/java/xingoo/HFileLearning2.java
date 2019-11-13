package xingoo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.io.hfile.CacheConfig;
import org.apache.hadoop.hbase.io.hfile.HFileDataBlockEncoder;
import org.apache.hadoop.hbase.io.hfile.NoOpDataBlockEncoder;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.regionserver.StoreFile;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.DecimalFormat;

public class HFileLearning2 {
    public static void main(String[] args) throws Exception {
        String tableName = "taglog";
        byte[] family = Bytes.toBytes("logs");
        //配置文件设置
        Configuration conf = HBaseConfiguration.create();
        //conf.set("hbase.master", "192.168.1.133:60000");
        //conf.set("hbase.zookeeper.quorum", "192.168.1.135");
        //conf.set("zookeeper.znode.parent", "/hbase");
        //conf.set("hbase.metrics.showTableName", "false");
        //conf.set("io.compression.codecs", "org.apache.hadoop.io.compress.SnappyCodec");

        String outputdir = "hdfs://hadoop.Master:8020/user/SEA/hfiles/";
        Path dir = new Path(outputdir);
        Path familydir = new Path(outputdir, Bytes.toString(family));
        FileSystem fs = familydir.getFileSystem(conf);
        BloomType bloomType = BloomType.NONE;
        final HFileDataBlockEncoder encoder = NoOpDataBlockEncoder.INSTANCE;
        int blockSize = 64000;
        Configuration tempConf = new Configuration(conf);
        tempConf.set("hbase.metrics.showTableName", "false");
        tempConf.setFloat(HConstants.HFILE_BLOCK_CACHE_SIZE_KEY, 1.0f);
        //实例化HFile的Writer，StoreFile实际上只是HFile的轻量级的封装
        StoreFile.Writer writer = new StoreFile.WriterBuilder(conf, new CacheConfig(tempConf),fs)
                .withOutputDir(familydir)
                .withBloomType(bloomType)
                .withComparator(KeyValue.COMPARATOR)
                .build();

        long start = System.currentTimeMillis();

        DecimalFormat df = new DecimalFormat("0000000");



        KeyValue kv1 = null;
        KeyValue kv2 = null;
        KeyValue kv3 = null;
        KeyValue kv4 = null;
        KeyValue kv5 = null;
        KeyValue kv6 = null;
        KeyValue kv7 = null;
        KeyValue kv8 = null;

        //这个是耗时操作，只进行一次
        byte[] cn = Bytes.toBytes("cn");
        byte[] dt = Bytes.toBytes("dt");
        byte[] ic = Bytes.toBytes("ic");
        byte[] ifs = Bytes.toBytes("if");
        byte[] ip = Bytes.toBytes("ip");
        byte[] le = Bytes.toBytes("le");
        byte[] mn = Bytes.toBytes("mn");
        byte[] pi = Bytes.toBytes("pi");

        int maxLength = 3000000;
        for(int i=0;i<maxLength;i++){
            String currentTime = ""+System.currentTimeMillis() + df.format(i);
            long current = System.currentTimeMillis();
            //rowkey和列都要按照字典序的方式顺序写入，否则会报错的
            kv1 = new KeyValue(Bytes.toBytes(currentTime),
                    family, cn,current,KeyValue.Type.Put,Bytes.toBytes("3"));

            kv2 = new KeyValue(Bytes.toBytes(currentTime),
                    family, dt,current,KeyValue.Type.Put,Bytes.toBytes("6"));

            kv3 = new KeyValue(Bytes.toBytes(currentTime),
                    family, ic,current,KeyValue.Type.Put,Bytes.toBytes("8"));

            kv4 = new KeyValue(Bytes.toBytes(currentTime),
                    family, ifs,current,KeyValue.Type.Put,Bytes.toBytes("7"));

            kv5 = new KeyValue(Bytes.toBytes(currentTime),
                    family, ip,current,KeyValue.Type.Put,Bytes.toBytes("4"));

            kv6 = new KeyValue(Bytes.toBytes(currentTime),
                    family, le,current,KeyValue.Type.Put,Bytes.toBytes("2"));

            kv7 = new KeyValue(Bytes.toBytes(currentTime),
                    family, mn,current,KeyValue.Type.Put,Bytes.toBytes("5"));

            kv8 = new KeyValue(Bytes.toBytes(currentTime),
                    family,pi,current,KeyValue.Type.Put,Bytes.toBytes("1"));

            writer.append(kv1);
            writer.append(kv2);
            writer.append(kv3);
            writer.append(kv4);
            writer.append(kv5);
            writer.append(kv6);
            writer.append(kv7);
            writer.append(kv8);
        }


        writer.close();

        //把生成的HFile导入到hbase当中
        HTable table = new HTable(conf,tableName);
        LoadIncrementalHFiles loader = new LoadIncrementalHFiles(conf);
        loader.doBulkLoad(dir, table);
    }
}
