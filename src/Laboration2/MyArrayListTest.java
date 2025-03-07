package Laboration2;

/**
 * Håkan Jonsson, LTU
 */
public class MyArrayListTest {

    private static MyArrayList<Integer> data;

    private static void insert(int n) {
        for (int i = 0; i < n; i++) {
            data.add(i);
        }
    }

    public static void testAvMyArrayList() {
        System.out.println("Test av MyArrayList() starts");
        try {
            data = new MyArrayList<Integer>(-1);
        } catch (IllegalArgumentException e) {
            // fungerar; det ska bli IllegalArgumentException
        } catch (Exception e) {
            throw new RuntimeException("MyArrayList 1: " + e);
        }

        data = new MyArrayList<Integer>();
        int N = 100;
        try {
            insert(N);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("MyArrayList 2, N==" + N);
        }
        System.out.println("Test av MyArrayList() OK");
    }

    public static void testAvSize() {
        System.out.println("Test av size() starts");
        data = new MyArrayList<Integer>();
        if (!(data.size() == 0 )) throw new RuntimeException("size() 1");;
        data.add(1);
        data.add(1);
        data.add(1);
        if (!(data.size() != 0 )) throw new RuntimeException("size() 2");;
        System.out.println("Test av size() OK");
    }

    public static void testAvIsEmpty() {
        System.out.println("Test av isEmpty() starts");
        data = new MyArrayList<Integer>();
        if (!(data.isEmpty() )) throw new RuntimeException("isEmpty() 1");;
        data.add(1);
        if (!(!data.isEmpty() )) throw new RuntimeException("isEmpty() 2");;
        System.out.println("Test av isEmpty() OK");
    }

    public static void testAvClear() {
        System.out.println("Test av clear() starts");
        data = new MyArrayList<Integer>();
        data.add(1);
        if (!(data.size() > 0 )) throw new RuntimeException("clear() 1");;
        data.clear();
        if (!(data.size() == 0 )) throw new RuntimeException("clear() 2");;
        System.out.println("Test av clear() OK");
    }

    // -- 2 --

    // -- 3 --

    public static void testAvAddindex() {
        System.out.println("Test av add(index) startar");
        data = new MyArrayList<Integer>(100);
        try {
            data.add(-1, null);
        } catch (IndexOutOfBoundsException e) {
            // fungerar; det ska bli IndexOutOfBoundsException
        } catch (Exception e) {
            throw new RuntimeException("add(index) 1");
        }
        if (!(data.size() == 0 )) throw new RuntimeException("add(index) 2");;
        data.add(0, 10);
        data.add(1, 11);
        data.add(2, 12);
        data.add(1, 21);
        data.add(0, 20);
        if (!(data.get(0) == 20 )) throw new RuntimeException("add(index) 3");;
        if (!(data.get(1) == 10 )) throw new RuntimeException("add(index) 4");;
        if (!(data.get(2) == 21 )) throw new RuntimeException("add(index) 5");;
        if (!(data.get(3) == 11 )) throw new RuntimeException("add(index) 6");;
        if (!(data.get(4) == 12 )) throw new RuntimeException("add(index) 7");;
        System.out.println("Test av add(index) OK");
    }

    public static void testAvAddE() {
        System.out.println("Test av add(E) startar");
        data = new MyArrayList<Integer>();
        insert(4);
        data.add(100);
        if (!(data.size() == 5 )) throw new RuntimeException("add(E) 1");;
        if (!(data.get(4).equals(new Integer(100)) )) throw new RuntimeException("add(E) 2");;
        System.out.println("Test av add(E) OK");
    }

    // -- 4 --

    public static void testAvGetindex() {
        System.out.println("Test av get(index) startar");
        data = new MyArrayList<Integer>();
        insert(4);
        if (!(data.get(2).equals(new Integer(2)) )) throw new RuntimeException("get(index) 1");;
        System.out.println("Test av get(index) OK");
    }

    public static void testAvSetindex() {
        System.out.println("Test av set(index) startar");
        data = new MyArrayList<Integer>();
        insert(5);
        Integer i = data.set(3, new Integer(100));
        if (!(i.equals(new Integer(3)) )) throw new RuntimeException("set(index) 1");;
        if (!(data.get(3).equals(new Integer(100)) )) throw new RuntimeException("set(index) 2");;
        System.out.println("Test av set(int index, E element) OK");
    }

    // -- 5 --

    public static void testAvRemoveindex() {
        System.out.println("Test av remove(index) startar");
        data = new MyArrayList<Integer>();
        insert(7);

        Integer i = data.remove(2);
        if (!(data.size() == 6 )) throw new RuntimeException("remove(index) 1");;
        if (!(i.equals(new Integer(2)) )) throw new RuntimeException("remove(index) 2");;

        System.out.println("Test av remove(int index) OK");
    }

    protected static void testAvRemoveRange() {
        System.out.println("Test av removeRange() startar");
        data = new MyArrayList<Integer>();
        insert(6); // 0 .. 5
        data.removeRange(2, 4);
        if (!(data.contains(0) )) throw new RuntimeException("removeRange 1");;
        if (!(data.contains(1) )) throw new RuntimeException("removeRange 2");;
        if (!(!data.contains(2) )) throw new RuntimeException("removeRange 3");;
        if (!(!data.contains(3) )) throw new RuntimeException("removeRange 4");;
        if (!(data.contains(4) )) throw new RuntimeException("removeRange 5");;
        if (!(data.contains(5) )) throw new RuntimeException("removeRange 6");;
        if (!(!data.contains(6) )) throw new RuntimeException("removeRange 7");;

        System.out.println("Test av removeRange() OK");
    }

    // -- 6 --

    public static void testAvIndexOf() {
        System.out.println("Test av indexOf(Object o) startar");
        data = new MyArrayList<Integer>();
        insert(5);
        if (!(data.indexOf(0) == 0 )) throw new RuntimeException("");;
        if (!(data.indexOf(4) == 4 )) throw new RuntimeException("");;
        if (!(data.indexOf(2) == 2 )) throw new RuntimeException("");;
        if (!(data.indexOf(-100) == -1 )) throw new RuntimeException("");;
        if (!(data.indexOf(100) == -1 )) throw new RuntimeException("");;
        System.out.println("Test av indexOf(Object o) OK");
    }

    public static void testAvRemoveobject() {
        System.out.println("Test av remove(Object o) startar");
        data = new MyArrayList<Integer>();
        insert(5);
        if (!(data.remove(new Integer(0)) )) throw new RuntimeException("remove(Object o) 1");;
        if (!(data.remove(new Integer(4)) )) throw new RuntimeException("remove(Object o) 2");;
        if (!(!data.remove(new Integer(-100)) )) throw new RuntimeException("remove(Object o) 3");;
        if (!(!data.remove(new Integer(100)) )) throw new RuntimeException("remove(Object o) 4");;
        System.out.println("Test av remove(Object o) OK");
    }

    public static void testAvContainsobject() {
        System.out.println("Test av contains(Object o) startar");
        data = new MyArrayList<Integer>();
        insert(3);
        if (!(data.contains(0) )) throw new RuntimeException("contains(Object o) 1");;
        if (!(data.contains(1) )) throw new RuntimeException("contains(Object o) 2");;
        if (!(data.contains(2) )) throw new RuntimeException("contains(Object o) 3");;
        if (!(!data.contains(-1) )) throw new RuntimeException("contains(Object o) 4");;
        if (!(!data.contains(4) )) throw new RuntimeException("contains(Object o) 5");;
        System.out.println("Test av contains(Object o) OK");
    }

    // -- 7 --

    private static class IntegerBox {
        int x;

        IntegerBox(int x) {
            this.x = x;
        }
        public boolean equals(IntegerBox box) {
            return this.x == box.x;
        }
    }

    public static void testAvClone() {
        System.out.println("Test av clone() startar");
        data = new MyArrayList<Integer>();
        int N = 3;
        insert(3);
        MyArrayList<Integer> clone = (MyArrayList<Integer>) data.clone();
        if (!(clone.get(0).equals(data.get(0)) )) throw new RuntimeException("clone() 1");;
        if (!(clone.get(1).equals(data.get(1)) )) throw new RuntimeException("clone() 2");;
        if (!(clone.get(2).equals(data.get(2)) )) throw new RuntimeException("clone() 3");;

        clone.set(1, N + 1);
        if (!(!data.get(1).equals(N + 1) )) throw new RuntimeException("clone() 4");;
        data.set(2, N + 2);
        if (!(!clone.get(1).equals(N + 2) )) throw new RuntimeException("clone() 5");;

        if (!(data.size() == clone.size() )) throw new RuntimeException("clone() 6");;

        MyArrayList<IntegerBox> box = new MyArrayList<IntegerBox>();
        box.add(new IntegerBox(10));
        MyArrayList<IntegerBox> box2 = (MyArrayList<IntegerBox>) box.clone();
        if (!(box.get(0).equals(box2.get(0)) )) throw new RuntimeException("clone() 7");;
        box.get(0).x = 30;
        if (!(box.get(0).equals(box2.get(0)) )) throw new RuntimeException("clone() 8");;

        System.out.println("Test av clone() OK");
    }

    public static void testAvToArray() {
        System.out.println("Test av toArray() startar");
        data = new MyArrayList<Integer>();
        int N = 4;
        insert(4);
        Object[] tmp = (Object[]) data.toArray();
        if (!(tmp.length == N )) throw new RuntimeException("toArray() 1");;
        if (!((Integer) tmp[1] == 1 )) throw new RuntimeException("toArray() 2");;
        if (!(data.get(0) == 0 )) throw new RuntimeException("toArray() 3");;
        if (!(data.get(2) == 2 )) throw new RuntimeException("toArray() 4");;
        if (!(data.get(3) == 3 )) throw new RuntimeException("toArray() 5");;
        System.out.println("Test av toArray() OK");
    }

    public static void main(String[] args) {
        System.out.println("MyArrayListTest Version: 2025-02-04");

//      Avkommentera nedan de tester du vill genomföra.
//      Kom ihåg att aktivera assertions med "-ea" i Eclipse (annars 
//      fungerar testerna inte.
//        
//      Innan rättningen/presentationen ska alla tester ge OK.

        testAvMyArrayList();
        testAvSize();
        testAvIsEmpty();
        testAvClear();
        testAvAddindex();
        testAvAddE();
        testAvGetindex();
        testAvSetindex();
        testAvRemoveindex();
        testAvRemoveRange();
        testAvIndexOf();
        testAvRemoveobject();
        testAvContainsobject();
        testAvClone();
        testAvToArray();
        System.out.println("hej");
        assert false;
    }
}
