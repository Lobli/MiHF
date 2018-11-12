package com.company;


import java.util.*;

import static java.util.Scanner.*;

class InputReader {


    public static Scanner scanner;

    private Integer[][] Matrix;
    private Integer ThingNumber=0;


    public InputReader(){
        int RowLength;
        scanner = new Scanner(System.in);

        String line;
        ArrayList<Integer> IntegerLine;

        line = scanner.nextLine();
        IntegerLine = CreateIntArrayFromLine(line);
        RowLength = IntegerLine.size();
        Matrix = new Integer[RowLength][RowLength];

        for (int j = 0; j < RowLength; j++) {
            int num = IntegerLine.get(j);
            Matrix[0][j] = num;
            //System.out.print(Matrix[0][j]);
            //System.out.print(' ');
        }

        for (int i = 1; i < RowLength; i++) {
            line = scanner.nextLine();
            IntegerLine = CreateIntArrayFromLine(line);
            for (int j = 0; j < RowLength; j++) {
                int num = IntegerLine.get(j);
                Matrix[i][j] = num;
                //System.out.print(Matrix[i][j]);
                //System.out.print(' ');
            }
            //System.out.print("\n");
        }

        line = scanner.nextLine();
        ThingNumber=Integer.parseInt(line);

//        System.out.print("Final:\n\n");
//
//        for (int i = 0; i < RowLength; i++) {
//            for (int j = 0; j < RowLength; j++) {
//                System.out.print(Matrix[i][j]);
//                System.out.print(' ');
//            }
//            System.out.print("\n");
//        }
//        System.out.print(ThingNumber);
    }

    public Integer[][] getMatrix(){
        return Matrix;
    }

    public Integer getThingNumber(){
        return ThingNumber;
    }

    public static ArrayList<Integer> CreateIntArrayFromLine(String s){
        ArrayList<Integer> IntegerLine = new ArrayList<Integer>();
        List<String> stringLine = Arrays.asList(s.split(" "));

        for (String str : stringLine)
            IntegerLine.add(Integer.parseInt(str));

        return IntegerLine;
    }

    public static Integer ConvertCharToInteger(char c){
        return Integer.parseInt(Character.toString(c));
    }

}


class Rules {
    public static Integer opposite(int i) {
        if (i == 8) return 2;
        if (i == 2) return 8;
        if (i == 4) return 1;
        return 4;
    }

    public static Set<Integer> decide(int field, Set<Integer> dirs) {
        String binary = Integer.toBinaryString(field);
        for (int i = binary.length() - 1; 0 <= i; i--) {
            if (binary.charAt(i) == '1') {
                int elem = (int) Math.pow(2, binary.length() - i - 1);
                if (elem == 16) {
                    dirs.add(16);
                } else {
                    dirs.remove(elem);
                }
            }
        }
        return dirs;
    }

    public static Coord directionToCoord(int i) throws Exception {
        if (i == 1) return new Coord(0, -1);
        if (i == 2) return new Coord(1, 0);
        if (i == 4) return new Coord(0, 1);
        if (i == 8) return new Coord(-1, 0);
        throw new Exception(String.format("%s is a value that is not supported", i));
    }

    public static int coordToDirection(Coord c) {
        if (c.x == 0 && c.y == -1) return 1;
        if (c.x == 1 && c.y == 0) return 2;
        if (c.x == 0 && c.y == 1) return 4;
        return 8;
    }

    public static Set<Integer> getDirsForDirection(int direction) {
        Set<Integer> set = new HashSet<>(Arrays.asList(8, 4, 2, 1));
        set.remove(direction);
        return set;
    }
}

class Coord {
    int x;
    int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("%s %s", y, x);
    }
}

class TreeNode {

    public int value;
    public TreeNode parent;
    public ArrayList<TreeNode> children;
    public boolean isExit;
    public boolean isTreasure;
    public Coord coords;
    public boolean visited;

    public TreeNode(Coord coords, int value, boolean isExit, boolean isTreasure) {
        this.coords = coords;
        this.value = value;
        this.isExit = isExit;
        this.isTreasure = isTreasure;
        this.visited = false;
        this.children = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public TreeNode childWithCoords(Coord c) {
        for (TreeNode child : children) {
            if (child.coords.x == c.x && child.coords.y == c.y) {
                return child;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s", value);
    }
}

class Matrix {
    Integer[][] mat;
    Boolean[][] visited;
    int toBeVisited;

    Matrix(Integer[][] numbers) {
        mat = numbers;
        visited = new Boolean[mat.length][mat.length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                visited[j][i] = false;
            }
        }

        toBeVisited = mat.length * mat.length;
    }

    public void setVisited(int x, int y) throws IndexOutOfBoundsException {
        if (-1 < y && y < visited.length && -1 < x && x < visited[y].length) {
            if (!visited[y][x]) {
                toBeVisited -= 1;
                visited[y][x] = true;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isVisited(int x, int y) throws IndexOutOfBoundsException {
        if (-1 < y && y < visited.length && -1 < x && x < visited[y].length) return visited[y][x];
        System.out.println(String.format("%s %s", x, y));
        throw new IndexOutOfBoundsException();
    }

    public Integer get(int x, int y) throws IndexOutOfBoundsException {
        if (-1 < y && y < mat.length && -1 < x && x < mat[y].length) return mat[y][x];
        throw new IndexOutOfBoundsException();
    }

    public TreeNode toTree() throws Exception {
        Stack<TreeNode> visitedFields = new Stack<>();
        Stack<Integer> fromStack = new Stack<>();
        int x = 0;
        int y = 0;
        int from = 1;
        TreeNode parent = null;
        TreeNode root = null;

        while (toBeVisited > 0) {
            boolean isExit = false;
            TreeNode node = null;
            Integer value = this.get(x, y);
            Coord coords = new Coord(x, y);

            if (value == 2) {
                int a = 3;
            }

            Set<Integer> directions = Rules.decide(value, Rules.getDirsForDirection(from));
            boolean isTreasure = directions.contains(16);
            if (isTreasure) {
                directions.remove(16);
            }


            ArrayList<Coord> notVisitedNeighbors = new ArrayList<>();
            for (Integer direction : directions) {
                Coord vector = Rules.directionToCoord(direction);
                int newX = x + vector.x;
                int newY = y + vector.y;
                if (mat.length <= newX || mat.length <= newY) {
                    isExit = true;
                } else if (!isVisited(newX, newY)) {
                    notVisitedNeighbors.add(vector);
                }
            }

            if (!isVisited(x, y)) {
                node = new TreeNode(coords, value, isExit, isTreasure);
                node.parent = parent;
                if (x == 0 && y == 0) {
                    root = node;
                } else {
                    parent.addChild(node);
                }
                setVisited(x, y);
            } else {
                node = parent.childWithCoords(coords);
            }

            if (directions.size() == 0 || notVisitedNeighbors.size() == 0) {
                x = parent.coords.x;
                y = parent.coords.y;
                from = fromStack.pop();
                parent = visitedFields.pop();
            } else {
                visitedFields.push(parent);
                fromStack.push(from);
                parent = node;
                x = x + notVisitedNeighbors.get(0).x;
                y = y + notVisitedNeighbors.get(0).y;
                from = Rules.opposite(Rules.coordToDirection(notVisitedNeighbors.get(0)));
            }
        }
        return root;
    }

}

public class Main {

    static String times(String chr, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(chr);
        }
        return result.toString();
    }

    private static void printTree(TreeNode treeNode, int depth) {
        String exit = treeNode.isExit ? "=" : "";
        String treasure = treeNode.isTreasure ? "*" : "";
        System.out.println(times(" ", depth) + treeNode.value + exit + treasure);
        if (0 < treeNode.children.size()) {
            for (TreeNode child : treeNode.children) {
                printTree(child, depth + 1);
            }
        }
    }

    private static TreeNode getExit(TreeNode node) {
        if (node.isExit) {
            return node;
        }
        for (TreeNode child : node.children) {
            TreeNode exit = getExit(child);
            if (exit != null) {
                return exit;
            }
        }
        return null;
    }

    private static void reverseWalk(TreeNode node, int treasureNum) {
        ArrayList<String> nodes = new ArrayList<>();
        int treasuresFound = 0;
        TreeNode current = node;
        while (current != null) {
            current.visited = true;
            if (current.isTreasure) {
                current.isTreasure = false;
                nodes.add("felvesz");
                treasuresFound += 1;
            }
            nodes.add(current.coords.toString());

            TreeNode nextNode = null;

            if (treasuresFound == treasureNum || current.children.size() == 0) {
                nextNode = current.parent;
            } else {

                for (TreeNode child : current.children) {
                    if (!child.visited) {
                        nextNode = child;
                        break;
                    }
                }

                if (nextNode == null) {
                    nextNode = current.parent;
                }
            }

            current = nextNode;
        }

        Collections.reverse(nodes);

        nodes.remove(0);

        for (String message : nodes) {
            System.out.println(message);
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {

        System.in.read();
        InputReader reader = new InputReader();

        Integer[][] mat = reader.getMatrix();

        Matrix matrix = new Matrix(mat);
        TreeNode root = matrix.toTree();

        TreeNode exit = getExit(root);

        reverseWalk(exit, reader.getThingNumber());


    }
}

