
package maint;

import java.util.concurrent.ThreadLocalRandom;


class node
{
    private int data;
    private node Left;
    private node Right;
    
    node(int d)
    {
       this.setData(d);
    }
   
 public int getData() {
        return data;
    }

 
 public void setData(int data) {
        this.data = data;
    }


 public node getLeft() {
        return Left;
    }

  
 public void setLeft(node Left) {
        this.Left = Left;
    }

 
 public node getRight() {
        return Right;
    }


 public void setRight(node Right) {
        this.Right = Right;
    }
    

}

class BinaryTree  {

    int i=0;
 
 public node getRoot() {
        return root;
    }


 public void setRoot(node root) {
        this.root = root;
    }
    
    private node root;
    
    BinaryTree(){}
    
 node addA(node current, int value) {
         
    if (current == null) {
        return new node(value);
    }
 
    if (value < current.getData()) { current.setLeft(addA(current.getLeft() , value));}
    
    else if (value > current.getData()) { current.setRight(addA(current.getRight(), value)); } 
    
    else { return current; }
 
    return current;
    
    }
    
 synchronized public void add(int value) {
        setRoot(addA(getRoot(), value));
    }  
    
 boolean findNodeA(node current, int value) {
    if (current == null) {
        return false;
    } 
    if (value == current.getData()) {
        return true;
    } 
    return value < current.getData()
      ? findNodeA(current.getLeft(), value)
      : findNodeA(current.getRight(), value);
    }

 synchronized  public boolean findNode(int value) {
    return findNodeA(getRoot(), value);
    }
    
 private node deleteA(node current, int value) {
    
    if (current == null) return null;
  
    if (value == current.getData())
    {
        
    if (current.getLeft() == null && current.getRight() == null) return null;

    if (current.getRight() == null) return current.getLeft();
    
    if (current.getLeft() == null) return current.getRight();
    
    int sv = minimumValue(current.getRight()); current.setData(sv); 
     current.setRight(deleteA(current.getRight(),sv));
   
    }
    
    if (value < current.getData()) { current.setLeft(deleteA(current.getLeft(), value)); return current; }
    
    current.setRight(deleteA(current.getRight(), value));  return current; 
    
    }
    
  synchronized  public void delete(int value) {
        setRoot(deleteA(getRoot(), value));
    }
    
    
   synchronized int minimumValue(node root) { return (root.getLeft()== null) ? root.getData() : minimumValue(root.getLeft()); }
    
   synchronized  int maximumValue(node root) { return (root.getRight()== null) ? root.getData() : maximumValue(root.getRight()); }
    
    
 synchronized public void LNR(node node) {
      
    if (node == null) return;
    
        LNR(node.getLeft());
        System.out.print(" " + node.getData());
        LNR(node.getRight());
}
  
}

class ThreadADD extends Thread  
{
    
    BinaryTree root;
    
    ThreadADD(BinaryTree rooT)
    {
        root=rooT;
    }
    
   synchronized void f()
    {
         root.i++;
      int value=ThreadLocalRandom.current().nextInt() % 100 ;
      System.out.println("ADD : "+value);
      root.add(value); 
      
      if( root.i >= 5){root.i=0; System.out.println("");}
    }
    
   synchronized void lol(long l)
   {
    try { Thread.sleep(l); }
    catch(Exception e) { System.out.println(e.getMessage()); }
   }
   
    @Override
    public void run()
    {
      while(true) { lol(1000); f();  }
    }
}
    
class ThreadDEL extends Thread 
{
    
    BinaryTree root;
    
    ThreadDEL (BinaryTree rooT)
    {
        root=rooT;
    }
    
   synchronized void f()
    {
       root.i++;
      int value=ThreadLocalRandom.current().nextInt() % 100 ;
      if(!root.findNode(value)){System.out.println("deleted value : "+value+" , the value is not exist ");
      if( root.i >= 5){root.i=0; System.out.println("");}
      return;}
      root.delete(value);
      System.out.println("deleted value : "+value);
      if( root.i >= 5){root.i=0; System.out.println("");}
    }
    
   synchronized void lol(long l)
   {
    try { Thread.sleep(l); }
    catch(InterruptedException e) { System.out.println(e.getMessage()); }
   }
    
    @Override
    public void run()
    { 
      while(true) { lol(1050); f(); }
    }      
    
}


class ThreadLNR extends Thread 
{
    
    BinaryTree root;
    
    ThreadLNR (BinaryTree rooT)
    {
        root=rooT;
    }
    
    synchronized void f()
    {
         root.i++;
         
      if(root.getRoot()!=null) System.out.print("Print :"); root.LNR(root.getRoot()); System.out.println("");
      if( root.i >= 5){root.i=0; System.out.println("");}
    }
    
   synchronized void lol(long l)
   {
    try { Thread.sleep(l); }
    catch(InterruptedException e) { System.out.println(e.getMessage()); }
   }
    
    @Override
    public void run()
    {
      while(true) { lol(1100); f(); }
    }
}


class ThreadMAX extends Thread 
{
    
    BinaryTree root;
    
    ThreadMAX (BinaryTree rooT)
    {
        root=rooT;
    }
    
   synchronized void f()
    {
        root.i++;
      if(root.getRoot()!=null)  System.out.println( "Maximum Value : "+root.maximumValue(root.getRoot()));
     if( root.i >= 5){root.i=0; System.out.println("");}
    }
    
   synchronized void lol(long l)
   {
    try { Thread.sleep(l); }
    catch(InterruptedException e) { System.out.println(e.getMessage()); }
   }
    
    @Override
    public void run()
    {
     while(true){ lol(1150); f(); }
    }
}

class ThreadMIN extends Thread 
{
    
    BinaryTree root;
    
    ThreadMIN (BinaryTree rooT)
    {
        root=rooT;
    }
    
  synchronized void f()
    {  root.i++;
      if(root.getRoot()!=null)  System.out.println( "Minimum Value : "+root.minimumValue(root.getRoot())); 
     if( root.i >= 5){root.i=0; System.out.println("");}
    }
    
   synchronized void lol(long l)
   {
    try { Thread.sleep(l); }
    catch(InterruptedException e) { System.out.println(e.getMessage()); }
   }
    
    @Override
  public void run()
    {
       while(true){ lol(1200); f(); }
    }
}

public class MainT {


    public static void main(String[] args) throws InterruptedException {
        
        BinaryTree root=new BinaryTree();
        
        ThreadADD A=new ThreadADD(root);
        ThreadDEL B=new ThreadDEL(root);
        ThreadLNR C=new ThreadLNR(root);
        ThreadMIN D=new ThreadMIN(root);
        ThreadMAX E=new ThreadMAX(root);
        
        A.start();
        B.start();
        C.start();
        D.start();
        E.start();
         
         try {
        A.join();
        B.join();
        C.join();
        D.join();
        E.join();
      } catch ( InterruptedException e) {
         System.out.println("Interrupted");
      }
        
        //D.start();
        //E.start();
        
        /*f.start();
        e.start();
        b.start();
        c.start();
        d.start();
        
        
                /*ThreadLNR g= new ThreadLNR(root);
        
        /*g.start();
        
        Thread.sleep(10000);
        
        b.start();*/
        
    /* int value=ThreadLocalRandom.current().nextInt() % 100 ;
     root.add(value);
        
        root.LNR(root.getRoot());
        */
        
    /*root.add(3);
    root.add(4);
    root.add(2);
    root.add(1);
    root.LNR(root.getRoot());*/
     
    }
    
}
