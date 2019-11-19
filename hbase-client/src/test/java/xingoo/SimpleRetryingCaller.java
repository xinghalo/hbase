package xingoo;

import org.apache.hadoop.hbase.client.RetryingCallable;

public class SimpleRetryingCaller {
    long pause = 100;
    long retries = 31;
    long[] backoff = new long[]{1, 2, 3, 5, 10, 20, 40, 100, 100, 100, 100, 200, 200};

    public Object callWithRetries(RetryingCallable callable, int callTimeout) throws InterruptedException {
        long start = System.currentTimeMillis();
        long timeout = callTimeout;

        // 循环重试次数
        for(int i=0; i<retries; i++){
            try{
                callable.prepare(i!=0);
                // 调用时计算还剩多少时间
                int remaining = (int) (start + timeout - System.currentTimeMillis());
                return callable.call(remaining);
            }catch (Exception e){}
            // 每次失败后，通过背压配置，暂停
            int index = i>backoff.length? backoff.length-1 : i;
            long expectedSleep = pause * backoff[index];
            Thread.sleep(expectedSleep);
        }
        return null;
    }
}
