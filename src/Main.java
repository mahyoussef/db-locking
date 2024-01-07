public class Main {

    private static CinemaSeatRecord cinemaSeatRecord = new CinemaSeatRecord(null);
    private static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                updateCinemaSeatRecordPessimistic(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                updateCinemaSeatRecordPessimistic(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void updateCinemaSeatRecordOptimistic(Integer userId) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is trying to access this record...");
        // processing time
        Thread.sleep(2000);
        // check version
        int currentVersion = cinemaSeatRecord.getVersion();
        Thread.sleep(2000);

        if (cinemaSeatRecord.getUserId() == null && currentVersion == cinemaSeatRecord.getVersion()) {
           cinemaSeatRecord.setUserId(userId);
           cinemaSeatRecord.updateVersion();
           System.out.println(Thread.currentThread().getName() + " has already updated the record...");
           return;
        }
        System.out.println(Thread.currentThread().getName() + " has discovered a conflict and the seat was already" +
                "reserved by: " + cinemaSeatRecord.getUserId());

    }

    public static void updateCinemaSeatRecordPessimistic(Integer userId) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is trying to access this record...");

        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " has acquired the lock..");
            // processing time
            Thread.sleep(2000);
            if (cinemaSeatRecord.getUserId() == null) {
                cinemaSeatRecord.setUserId(userId);
                System.out.println(Thread.currentThread().getName() + " has already updated the record...");
            } else {
                System.out.println(Thread.currentThread().getName() + " has discovered that seat was " +
                        " already reserved by: " + cinemaSeatRecord.getUserId());
            }
        }
        System.out.println(Thread.currentThread().getName() + " has released the lock..");
    }

}