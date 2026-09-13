package collections;

/**
 * Demonstrates Java Memory Model: Stack vs Heap & equals() vs hashCode() contract.
 */
public class MemoryModelDemo {

    public static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            User user = (User) obj;
            return id == user.id;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }
    }

    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");

        System.out.println("s1 == s2: " + (s1 == s2));       // true (String Pool)
        System.out.println("s1 == s3: " + (s1 == s3));       // false (Heap Memory)
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true (Content equality)
    }
}
