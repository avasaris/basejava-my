/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LOW = 0;
    private static final int STORAGE_HI = 10000;

    Resume[] storage = new Resume[STORAGE_HI];

    private int count = STORAGE_LOW;

    void clear() {
        for (int i = STORAGE_LOW; i < count; i++) {
            storage[i] = null;
        }
        count = STORAGE_LOW;
    }

    void save(Resume r) {
        if (count == STORAGE_HI) return;

        int index = getIndex(r.toString());
        if (index >= STORAGE_LOW && index <= STORAGE_HI) return;

        storage[count] = r;
        count++;
    }

    Resume get(String uuid) {
        int index = this.getIndex(uuid);
        if (index >= STORAGE_LOW && index <= STORAGE_HI)
            return storage[index];
        else
            return null;
    }

    void delete(String uuid) {
        int index = this.getIndex(uuid);
        if (!(index >= STORAGE_LOW && index <= STORAGE_HI)) return;

        for (int i = index; i < count - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[count] = null;
        count--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] retval = new Resume[count];
        for (int i = STORAGE_LOW; i < count; i++) {
            retval[i] = storage[i];
        }
        return retval;
    }

    int size() {
        return count - STORAGE_LOW;
    }

    int getIndex(String uuid) {
        for (int i = STORAGE_LOW; i < count; i++) {
            if (storage[i].toString().equals(uuid)) return i;
        }
        return STORAGE_LOW - 1;
    }
}
