//Antonino Febbraro
//Project 4
//Due November 18, 2016

//Use this for finding mst to make sure no cycles -- finds cycles

public class Union {

    private int[] id;

    public Union(int n)
    {
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    public boolean connected(int id1, int id2) {
        return id[id1] == id[id2];
    }

    public void union(int id1, int id2) {
        int id_1 = id[id1];
        int id_2 = id[id2];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == id_1) id[i] = id_2;
        }
    }

}
