/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int CAPACITY = 10000;

    Resume[] storage = new Resume[CAPACITY];

    private int count = 0;

    void clear() {
        for (int i = 0; i < count; i++) {
            storage[i] = null;
        }
        count = 0;
    }

    void save(Resume r) {
        if (count == CAPACITY) return;

        int index = getIndex(r.toString());
        if (index >= 0) {
            return;
        }

        storage[count] = r;
        count++;
    }

    Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        }

        return storage[index];
    }

    void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return;
        }

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
        Resume[] resumes = new Resume[count];
        if (count >= 0) System.arraycopy(storage, 0, resumes, 0, count);
        return resumes;
    }

    int size() {
        return count;
    }

    int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].toString().equals(uuid)) return i;
        }
        return -1;
    }
}
