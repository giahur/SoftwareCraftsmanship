public class Tester {

    public static String fight(int m, int n) {
        int day = 0;
        int ala = m;
        int ursh = n;
        while(day < (m+n-1) && (ala + ursh > 1)) {
            int scenario = (int)(Math.random() * 3);
            //2 alakazam fight
            if(scenario == 0 && ala >= 2) {
                    ala = ala-1;
                    day=day+1;
            }
            else if(scenario == 1 && ursh >= 2) {
                    ala=ala+1;
                    ursh=ursh-2;
                    day=day+1;
            }
            else if(scenario == 2 && ala>=1 && ursh>=1) {
                    ala=ala-1;
                    day=day+1;
            }
            else {
                ;
            }

            if(ala<0) {
                return "negative alakazam";
            }
            else if(ursh<0) {
                return "negative urshifu";
            }
        }

        if(ala > ursh) {
            return "Alakazam" + (ala-ursh);
        }
        else if (ursh > ala){
            return "Urushifu" + (ursh-ala);
        }
        else {
            return "error";
        }

    }


    public static void main(String[] args) {
        System.out.println("odd m, odd n");
        //odd m, odd n
            System.out.println("m>n");
            //m>n
        System.out.println(fight(5, 3));
        System.out.println(fight(555,17));
            //m<n
            System.out.println("m<n");
        System.out.println(fight(1, 7));
        System.out.println(fight(999, 887));
            //n=m
            System.out.println("m=n");
        System.out.println(fight(5, 5));
        System.out.println(fight(77, 77));
        System.out.println(fight(887, 887));

        //even m, even n
        System.out.println("even m, even n");
            //m>n
            System.out.println("m>n");
        System.out.println(fight(8, 6));
        System.out.println(fight(100, 50));
            //m<n
            System.out.println("m<n");
        System.out.println(fight(10, 30));
        System.out.println(fight(500, 1000));
            //n=m
            System.out.println("m=n");
        System.out.println(fight(900, 900));
        System.out.println(fight(50, 50));
        System.out.println(fight(10, 10));
    }
}