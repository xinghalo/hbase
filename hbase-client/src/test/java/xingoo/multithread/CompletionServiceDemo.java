package xingoo.multithread;


import java.util.Random;
import java.util.concurrent.*;

class QueryFromReplicasService {
    private final Query[] tasks;
    private Executor pool;
    private volatile Query completed = null;

    QueryFromReplicasService(Integer replicasSize){
        this.tasks = new Query[replicasSize];
        this.pool = Executors.newFixedThreadPool(replicasSize);
    }

    public void submit(Integer id){
        Query f = new QueryFromReplicasService.Query("thread_"+id);
        pool.execute(f);
        tasks[id] = f;
    }

    public Query poll() throws InterruptedException {
        return completed;
    }

    class Query implements Runnable{
        private String name;
        public Query(String name){
            this.name = name;
        }
        @Override
        public void run() {
            try {
                int wait = new Random().nextInt(20000);
                Thread.sleep(wait);
                this.name += " 总耗时：" + wait;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                synchronized (tasks) {
                    if (completed == null) {
                        completed = Query.this;
                    }
                }
            }
        }
        public String get(){
            return name;
        }
    }
}

public class CompletionServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        QueryFromReplicasService cs = new QueryFromReplicasService(4);
        System.out.println("创建查询");
        for(int i = 0; i< 4; i++){
            cs.submit(i);
        }
        System.out.println("等待获取结果");
        QueryFromReplicasService.Query f = null;
        while((f=cs.poll()) == null){
            Thread.sleep(10);
        }
        System.out.println("第一个结果来了");
        System.out.println(f.get());

        // TODO 停止线程池内的所有调用
        System.exit(0);
    }


}
