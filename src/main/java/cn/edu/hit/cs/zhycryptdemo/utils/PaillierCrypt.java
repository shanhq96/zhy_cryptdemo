package cn.edu.hit.cs.zhycryptdemo.utils;

public class PaillierCrypt {

    private int p = 2;
    private int q = 3;

    private int N = p * q;
    private int g = 1 + N;

    private int pk = N;
    private int sk, lambda = get_lcm(p - 1, q - 1);


    public void test() {
        long x = 2;
        long r = 3;

        //encryption
        long x_e = (long) ((Math.pow(g, x) * Math.pow(r, N)) % Math.pow(N, 2));

        // decryption
        long c_pie = (long) (Math.pow(x_e, lambda) % Math.pow(N, 2));
        long x_pie = (c_pie - 1) / N;
        long x_dec = (long) (x_pie * Math.pow(lambda, -1) % N);


        System.out.println(String.format("x %s, x_e %s, x_dec %s", x, x_e, x_dec));
    }


    // 最大公约数
    public static int get_gcd(int n1, int n2) {
        int gcd = 0;
        if (n1 < n2) {// 交换n1、n2的值
            n1 = n1 + n2;
            n2 = n1 - n2;
            n1 = n1 - n2;
        }

        if (n1 % n2 == 0) {
            gcd = n2;
        }

        while (n1 % n2 > 0) {
            n1 = n1 % n2;

            if (n1 < n2) {
                n1 = n1 + n2;
                n2 = n1 - n2;
                n1 = n1 - n2;
            }

            if (n1 % n2 == 0) {
                gcd = n2;
            }
        }
        return gcd;

    }

    // 最小公倍数
    public static int get_lcm(int n1, int n2) {
        return n1 * n2 / get_gcd(n1, n2);
    }
}
