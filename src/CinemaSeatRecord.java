public class CinemaSeatRecord {

    private Integer userId;
    private int version = 1;

    public CinemaSeatRecord(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getVersion() {
        return version;
    }

    public void updateVersion() {
        this.version++;
    }
}
