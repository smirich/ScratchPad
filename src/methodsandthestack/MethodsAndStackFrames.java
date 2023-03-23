package methodsandthestack;

public class MethodsAndStackFrames {
    public static void main(String[] args)
    {
        method1(1);
    }

    public static void method1(int arg1)
    {
        System.out.println(arg1);
        arg1++;
        method1(arg1);
    }
}
