package projectinfo437;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProjectInfo437 {

    Map<String, Point> pointsNames = new HashMap<>();   //HashMap pour reconnètre le nom des points avec les coordonnées
    Map<String, Point> clustersNames = new HashMap<>(); //HashMap pour reconnètre le nom des clusters avec les coordonnées
    //des points qu'ils contients
    Map<String, ArrayList<String>> clusters = new HashMap<>();

    public double distanceuc(Point p1, Point p2) {  //retourne la distance calculer suivant l'algorithme de distance euclidienne
        return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
    }

    public double distanceDeManhattan(Point p1, Point p2) { //retourne la distance calculer suivant l'algorithme de Manhattan
        return Math.abs((p1.getX() - p2.getX())) + Math.abs((p1.getY() - p2.getY()));
    }

    public double distanceDeMinkowski(Point p1, Point p2, double d, double q) { //retourne la distance calculer suivant l'algorithme de Minkowski
        if (d == 1) {
            distanceDeManhattan(p1, p2);
        } else if (d == 2) {
            distanceuc(p1, p2);
        }
        return Math.pow(Math.pow(Math.abs((p1.getX() - p2.getX())), d) + Math.pow(Math.abs((p1.getY() - p2.getY())), d), q);
    }

    public Point getCentroid(String clusterName) {  //retourne le point centre qu'on obtient avec ses coordonnées
        double x = 0, y = 0;
        ArrayList<String> ls = clusters.get(clusterName);
        if (ls == null) {
            return null;
        }
        for (String s : ls) {
            Point p = pointsNames.get(s);
            x += p.getX();
            y += p.getY();
        }
        return new Point(x / ls.size(), y / ls.size());
    }

    public boolean containsPoint(String cluster, String point) {  //retourne le nom de point s'il se trouve dans la liste : ls
        ArrayList<String> ls = clusters.get(cluster);
        return ls.contains(point);
    }

    public String getClusterOfPoint(String p) {     //retourne le nom de cluster qui contient le point qu'on donne
        for (String clusterName : clusters.keySet()) {
            if (containsPoint(clusterName, p)) {
                return clusterName;
            }
        }
        return null;
    }

    public String getWinCluster(Double[] array) {   //retourne le cluster après la modification suivant l'algorithme qu'on utilise
        double min = array[0];
        int winIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= min) {
                min = array[i];
                winIndex = i + 1;
            }
        }
        return "C" + winIndex;
    }

    public String getClusterString() {  //retourne le nom de cluster avec les points qu'ils contients
        String s = "";
        for(String keys : clusters.keySet()) {
            s += keys;
            for(String values : clusters.get(keys)) {
                s += values;
            }
            s += "-";
        }
        return s;
    }
    
    public boolean euclidienne() {
        System.out.println(clusters);
        System.out.println(pointsNames);
        System.out.println("");
        String clusterAvant = getClusterString();

        for (int i = 1; i <= pointsNames.size(); i++) {
            String nameP = "A" + i;
            Double[] distToM = new Double[clusters.size()];
            for (int j = 1; j <= clusters.size(); j++) {
                double distance;
                String nameC = "C" + j;
                String nameCtrd = "M" + j;
                distance = distanceuc(pointsNames.get(nameP), getCentroid(nameC));
                distToM[j-1] = distance;
                System.out.println(nameP + nameCtrd + ": " + distance);
            }
            //les points qui sont changeés
            System.out.println("");
            String winClust = getWinCluster(distToM);
            String namePcluster = getClusterOfPoint(nameP);
            if (namePcluster != null) {
                clusters.get(namePcluster).remove(nameP);
                System.out.println(nameP + " removed from " + namePcluster);
            }
            clusters.get(winClust).add(nameP);
            System.out.println(nameP + " added to " + winClust);
            System.out.println("");
            System.out.println("Clusters: " + clusters);
        }
        String clusterApres = getClusterString();
        if(clusterAvant.equals(clusterApres)) {
            //pas de changement == stop
            return false;
        }
        return true;
    }

    public boolean Manhattan() {
        System.out.println(clusters);
        System.out.println(pointsNames);
        System.out.println("");
        String clusterAvant = getClusterString();

        for (int i = 1; i <= pointsNames.size(); i++) {
            String nameP = "A" + i;
            Double[] distToM = new Double[clusters.size()];
            for (int j = 1; j <= clusters.size(); j++) {
                double distance;
                String nameC = "C" + j;
                String nameCtrd = "M" + j;
                distance = distanceDeManhattan(pointsNames.get(nameP), getCentroid(nameC));
                distToM[j-1] = distance;
                System.out.println(nameP + nameCtrd + ": " + distance);
            }
            //les points qui sont changeés
            System.out.println("");
            String winClust = getWinCluster(distToM);
            String namePcluster = getClusterOfPoint(nameP);
            if (namePcluster != null) {
                clusters.get(namePcluster).remove(nameP);
                System.out.println(nameP + " removed from " + namePcluster);
            }
            clusters.get(winClust).add(nameP);
            System.out.println(nameP + " added to " + winClust);
            System.out.println("");
            System.out.println("Clusters: " + clusters);
        }
        String clusterApres = getClusterString();
        if(clusterAvant.equals(clusterApres)) {
            //pas de changement == stop
            return false;
        }
        return true;
    }

    public boolean Minkowski() {
        Scanner key = new Scanner(System.in);
        System.out.print("donner q:");
        String s = key.nextLine();
        double d = Double.parseDouble(s);
        double q = 1 / d;   //q em radicale
        System.out.println(clusters);
        System.out.println(pointsNames);
        System.out.println("");
        String clusterAvant = getClusterString();

        for (int i = 1; i <= pointsNames.size(); i++) {
            String nameP = "A" + i;
            Double[] distToM = new Double[clusters.size()];
            for (int j = 1; j <= clusters.size(); j++) {
                double distance;
                String nameC = "C" + j;
                String nameCtrd = "M" + j;
                distance = distanceDeMinkowski(pointsNames.get(nameP), getCentroid(nameC),d,q);
                distToM[j-1] = distance;
                System.out.println(nameP + nameCtrd + ": " + distance);
            }
            //les points qui sont changeés
            System.out.println("");
            String winClust = getWinCluster(distToM);
            String namePcluster = getClusterOfPoint(nameP);
            if (namePcluster != null) {
                clusters.get(namePcluster).remove(nameP);
                System.out.println(nameP + " removed from " + namePcluster);
            }
            clusters.get(winClust).add(nameP);
            System.out.println(nameP + " added to " + winClust);
            System.out.println("");
            System.out.println("Clusters: " + clusters);
        }
        String clusterApres = getClusterString();
        if(clusterAvant.equals(clusterApres)) {
            //pas de changement == stop
            return false;
        }
        return true;
    }

    public void pointCluster() {
        Scanner key = new Scanner(System.in);
        System.out.print("donner n:");
        String s1 = key.nextLine();
        int n = Integer.parseInt(s1);

        for (int i = 0; i < n; i++) {
            String name = "A" + (i + 1);
            System.out.print(name + ": ");
            String s = key.nextLine();
            String[] t = s.split(",");
            Double t0 = Double.parseDouble(t[0]);
            Double t1 = Double.parseDouble(t[1]);
            Point p = new Point(t0, t1);
            pointsNames.put(name, p);
        }
        System.out.println("D=" + pointsNames);
        System.out.print("donner le nombre de Cluster:");
        String s2 = key.nextLine();
        int numC = Integer.parseInt(s2);
        if (numC < n) {
            System.out.println("Les Clusters sont:");
            for (int i = 0; i < numC; i++) {
                System.out.println("Cluster C" + (i + 1));
            }
            System.out.println("Les centres initiaux sont:");
            for (int j = 0; j < numC; j++) {
                String nameC = "C" + (j + 1);
                String s3 = key.nextLine();
                System.out.println(nameC + "={" + s3.toString() + "}");
                ArrayList<String> tmpls = new ArrayList();
                tmpls.add(s3);
                clusters.put("C" + (j + 1), tmpls);
            }
        } else {
            System.out.println("you loose");
        }

        System.out.println("Clusters: " + clusters);
        int choice;
        System.out.println("1-euclidienne");
        System.out.println("2-de Manhattan");
        System.out.println("3-de Minkowski");
        System.out.println("0-exit");

        System.out.print("ton choix: ");
        choice = Integer.parseInt(key.nextLine());
        System.out.println("");

        int x=1;
        switch (choice) {
            case 1:
                System.out.println("itération nombre : " + x);
                while(euclidienne()){   //si les clusters avant itération != les clusters apres itération
                    System.out.println("");
                    System.out.println("continuer le calcule");
                    System.out.println("");
                    x++;
                    System.out.println("itération nombre : " + x);
                }
                System.out.println("itération nombre : " + (x+1) + " Stop : pas de changement");
                break;
            case 2:
                System.out.println("itération nombre : " + x);
                while(Manhattan()){ ////si les clusters avant itération != les clusters apres itération
                    System.out.println("");
                    System.out.println("continuer le calcule");
                    System.out.println("");
                    x++;
                    System.out.println("itération nombre : " + x);
                }
                System.out.println("itération nombre : " + (x+1) + " Stop : pas de changement");
                break;
            case 3:
                System.out.println("itération nombre : " + x);
                while(Minkowski()){ //si les clusters avant itération != les clusters apres itération
                    System.out.println("");
                    System.out.println("continuer le calcule");
                    System.out.println("");
                    x++;
                    System.out.println("itération nombre : " + x);
                }
                System.out.println("itération nombre : " + (x+1) + " Stop : pas de changement");
                break;
            case 0:
                return;
            default:
                System.out.println("choix impossible");
                break;
        }
    }

    public static void main(String[] args) {
        ProjectInfo437 pr = new ProjectInfo437();
        pr.pointCluster();
    }
}