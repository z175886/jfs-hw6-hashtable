public class Main {
    /**some testing on adding and removing, handling collision(based on the hw pdf)*/
    public static void main(String[] args) {

        LinearProbingHashTable table1 = new LinearProbingHashTable<>(5);

        table1.put(3, "Hello");
        table1.put(6, "World");
        table1.put(8, "foo");
        System.out.println("---After add key 3,6,8---");
        for (int i = 0; i < table1.size(); i++) {
            System.out.print(i);
            if(table1.getArray()[i] == null)
                System.out.println(table1.getArray()[i]);
            else{
                System.out.println((table1.getArray()[i].getValue().toString() +" available: "+ table1.getArray()[i].isAvailable()));
            }
        }

        table1.remove(6);
        table1.remove(3);
        table1.remove(8);
        System.out.println("---After remove key 6,3,8---");
        for (int i = 0; i < table1.size(); i++) {
            System.out.print(i);
            if(table1.getArray()[i] == null)
                System.out.println(table1.getArray()[i]);
            else{
                System.out.println((table1.getArray()[i].getValue().toString() +" available: "+ table1.getArray()[i].isAvailable()));

            }

        }
        table1.put(13, "bar");
        System.out.println("---After add key 13---");
        for (int i = 0; i < table1.size(); i++) {
            System.out.print(i);
            if(table1.getArray()[i] == null)
                System.out.println(table1.getArray()[i]);
            else{
                System.out.println((table1.getArray()[i].getValue().toString() +" available: "+ table1.getArray()[i].isAvailable()));

            }

        }

    }
}
