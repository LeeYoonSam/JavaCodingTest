import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import static java.lang.System.arraycopy;

public class CodingQuiz {
    public static void main(String[] args) throws UnsupportedEncodingException {
        // assume v = byte[] with length 1024
//        byte[] v = new byte[1024];
        String data = "  MEMCPY(3) Linux Programmer's Manual MEMCPY(3)\n" +
                "NAME         top\n" +
                "       memcpy - copy memory area\n" +
                "SYNOPSIS         top\n" +
                "\n" +
                "       void *memcpy(void *dest, const void *src, size_t n);\n" +
                "DESCRIPTION         top\n" +
                "       The memcpy() function copies n bytes from memory area src to memory\n" +
                "       area dest.  The memory areas must not overlap.  Use memmove(3) if the\n" +
                "       memory areas do overlap.\n" +
                "RETURN VALUE         top\n" +
                "       The memcpy() function returns a pointer to dest.\n" +
                "ATTRIBUTES         top\n" +
                "       For an explanation of the terms used in this section, see\n" +
                "       attributes(7).\n" +
                "\n" +
                " ┌──────────┬───────────────┬─────────┐\n" +
                " │Interface │ Attribute     │ Value   │\n" +
                " ├──────────┼───────────────┼─────────┤\n" +
                " │memcpy()  │ Thread safety │ MT-Safe │\n" +
                " └──────────┴───────────────┴─────────┘\n";
//        byte[] v = data.getBytes();
//        System.out.println(v.length);
//
//        System.out.println(new String(v, "UTF-8"));


//        memcpy(v, 0, 10, 512);
//        memcpy(v, 20, 10, 512);
//        memcpy(v, 50, 0, 0); // may throw RuntimeException or do nothing
//        memcpy(v, 50, 0, 1);
//        memcpy(v, 50, 0, -1); // may throw RuntimeException or do nothing


        /*

        10
        0 255 123 12 2 4 12 4 55 2
        5 0 3

          */

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int v[] = new int[n];

        for(int i = 0; i < n; i ++) {
            v[i] = in.nextInt();
        }

        int dest = in.nextInt();
        int src = in.nextInt();
        int size = in.nextInt();

        memcpy(v, dest, src, size);

//        memcpy(v, 50, 0, -1); // may throw RuntimeException or do nothing
//        memcpy(v, 10, 20, 512);
//        memcpy(v, 20, 10, 512);
//        memcpy(v, 50, 0, 0); // may throw RuntimeException or do nothing
//        memcpy(v, 50, 0, 1);​

    }

    static void memcpy(int[] v, int dest, int src, int size) {

        if(size < 1) {
            for(int i = 0; i < v.length; i ++ ) {
                System.out.print(v[i] + " ");
            }
            System.out.println();
            throw new RuntimeException();
        }


        arraycopy(v, src, v, dest, size);

        for(int i = 0; i < v.length; i ++ ) {
            System.out.print(v[i] + " ");
        }

    }





    /**
     * Additional questions
     * 만약 해당 함수를 가능한 한 빠르게 구현하고 싶을 경우 어떻게 할 수 있을까? 일반적인 Linux- x86 서버를 기준으로 답변하시오.
     *
     * 4byte(32비트) 단위로 읽으면 loop를 4배로 줄일수 있다.
     */



    /*
    MEMCPY(3)                 Linux Programmer's Manual                MEMCPY(3)
NAME         top
       memcpy - copy memory area
SYNOPSIS         top
       #include <string.h>

       void *memcpy(void *dest, const void *src, size_t n);
DESCRIPTION         top
       The memcpy() function copies n bytes from memory area src to memory
       area dest.  The memory areas must not overlap.  Use memmove(3) if the
       memory areas do overlap.
RETURN VALUE         top
       The memcpy() function returns a pointer to dest.
ATTRIBUTES         top
       For an explanation of the terms used in this section, see
       attributes(7).

       ┌──────────┬───────────────┬─────────┐
       │Interface │ Attribute     │ Value   │
       ├──────────┼───────────────┼─────────┤
       │memcpy()  │ Thread safety │ MT-Safe │
       └──────────┴───────────────┴─────────┘
CONFORMING TO         top
       POSIX.1-2001, POSIX.1-2008, C89, C99, SVr4, 4.3BSD.
NOTES         top
       Failure to observe the requirement that the memory areas do not
       overlap has been the source of significant bugs.  (POSIX and the C
       standards are explicit that employing memcpy() with overlapping areas
       produces undefined behavior.)  Most notably, in glibc 2.13 a
       performance optimization of memcpy() on some platforms (including
       x86-64) included changing the order in which bytes were copied from
       src to dest.

       This change revealed breakages in a number of applications that
       performed copying with overlapping areas.  Under the previous
       implementation, the order in which the bytes were copied had
       fortuitously hidden the bug, which was revealed when the copying
       order was reversed.  In glibc 2.14, a versioned symbol was added so
       that old binaries (i.e., those linked against glibc versions earlier
       than 2.14) employed a memcpy() implementation that safely handles the
       overlapping buffers case (by providing an "older" memcpy()
       implementation that was aliased to memmove(3)).
SEE ALSO         top
       bcopy(3), bstring(3), memccpy(3), memmove(3), mempcpy(3), strcpy(3),
       strncpy(3), wmemcpy(3)
COLOPHON         top
       This page is part of release 4.15 of the Linux man-pages project.  A
       description of the project, information about reporting bugs, and the
       latest version of this page, can be found at
       https://www.kernel.org/doc/man-pages/.
     */
}
