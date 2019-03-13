package jdbc.lesson4.hw1.model;

import java.util.Arrays;

public class Storage {
    private long id;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageMaxSize;

    public Storage(long id, String[] formatsSupported, String storageCountry, long storageMaxSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
    }

    public Storage(String[] formatsSupported, String storageCountry, long storageMaxSize) {
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public long getStorageMaxSize() {
        return storageMaxSize;
    }

    public boolean isSupportedFormat(String format) {
        for (String supportedFormat : formatsSupported) {
            if (supportedFormat.equals(format)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageMaxSize=" + storageMaxSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (storageMaxSize != storage.storageMaxSize) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(formatsSupported, storage.formatsSupported)) return false;
        return storageCountry.equals(storage.storageCountry);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Arrays.hashCode(formatsSupported);
        result = 31 * result + storageCountry.hashCode();
        result = 31 * result + (int) (storageMaxSize ^ (storageMaxSize >>> 32));
        return result;
    }
}
