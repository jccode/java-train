package com.github.jccode.alg;

public class LinkListRevert {

    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    static class LinkList {
        Node head;

        public LinkList() {}

        public LinkList(int[] a) {
            if (a != null && a.length != 0) {
                Node n;
                for (int i = a.length - 1; i >= 0; i--) {
                    n = new Node(a[i]);
                    n.next = head;
                    head = n;
                }
            }
        }

        @Override
        public String toString() {
            if (head == null) {
                return "NIL";
            } else {
                StringBuilder sb = new StringBuilder();
                Node n = head;
                while (n != null) {
                    sb.append(n.value).append(" -> ");
                    n  = n.next;
                }
                sb.append("NIL");
                return sb.toString();
            }
        }

        public void reverse() {
            Node p = head, q = head.next, n;
            p.next = null;
            while (q != null) {
                n = q.next;
                q.next = p;
                p = q;
                q = n;
            }
            head = p;
        }

        public void removeNthFromEnd(int n) {
            Node p = head, q = head;
            
        }
    }

    public static void main(String[] args) {
        LinkList link = new LinkList(new int[]{1, 2, 3, 4, 5, 6, 7});
        System.out.println(link);

        link.reverse();
        System.out.println(link);
    }
}
