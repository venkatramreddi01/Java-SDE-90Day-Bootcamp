import java.util.concurrent.*;

public class ProducerConsumer {

    // Shared buffer — capacity of 5 items
    static BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5);

    // PRODUCER
    static class Producer implements Runnable {
        public void run() {
            int item = 1;
            while (item <= 10) {
                try {
                    System.out.println("Produced: " + item);
                    buffer.put(item);   // Add to buffer (WAITS if full)
                    item++;
                    Thread.sleep(500);      // Simulate production time
                } catch (InterruptedException e) {}
            }
        }
    }

    // CONSUMER
    static class Consumer implements Runnable {
        public void run() {
            int count = 0;
            while (count < 10) {
                try {
                    int item = buffer.take();  // Take from buffer (WAITS if empty)
                    System.out.println("        Consumed: " + item);
                    count++;
                    Thread.sleep(1000);           // Simulate slower consumer
                } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        producer.start();
        consumer.start();
    }
}
