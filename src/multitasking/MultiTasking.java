/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitasking;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hanane
 */
class process implements Runnable {
    private int id;
    private Random random=new Random();
    private List<Integer> data_sb1=new ArrayList<Integer>() ;
    private List<Integer> data_sb2=new ArrayList<Integer>() ;
    private List<Integer> data_sb3=new ArrayList<Integer>() ;
    long  process_time2=0;
    long  process_time1=0;
    long  process_time3=0;
    Semaphore sem=new Semaphore(1);
    private Object mutex = new Object();
    
    ArrayList buff_sb1=new ArrayList();
    ArrayList buff_sb2=new ArrayList();
    ArrayList buff_sb3=new ArrayList();
    ArrayList schedular=new ArrayList();
       
   
    public ArrayList getSchedular(){
        return schedular;
    }
   public process(int id){
       this.id=id;
      schedular.add("Subkernel: "+id);
   }
     public void Sub_kernel1() {
         synchronized(mutex){
         long start=System.currentTimeMillis();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiTasking.class.getName()).log(Level.SEVERE, null, ex);
        }
        data_sb1.add(random.nextInt());
       
         long end=System.currentTimeMillis();
         process_time1=end-start;
         buff_sb1.add( this.id);
         buff_sb1.add( process_time1);
         buff_sb1.add(data_sb1);
          try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          schedular.add( buff_sb1);
            
         sem.release();
         }
     }     
      public void Sub_kernel2(){
        synchronized(mutex){
          long start=System.currentTimeMillis();
          
           try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiTasking.class.getName()).log(Level.SEVERE, null, ex);
        }
        data_sb2.add(random.nextInt());
      
          
         long end=System.currentTimeMillis();
         process_time2=end-start;
            buff_sb2.add( this.id);
          buff_sb2.add( process_time2);
         buff_sb2.add(data_sb2);
            try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
              
          schedular.add( buff_sb2);
           
         sem.release();
         
     }   } 
     public void Sub_kernel3(){
        synchronized(mutex){
           long start=System.currentTimeMillis();
          
            try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MultiTasking.class.getName()).log(Level.SEVERE, null, ex);
        }
        data_sb3.add(random.nextInt());
       
         long end=System.currentTimeMillis();
          process_time3=end-start;
           buff_sb3.add( this.id);
           buff_sb3.add( process_time3);
         buff_sb3.add(data_sb3);
            try {
            sem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(process.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
          schedular.add( buff_sb3);
            
             
          sem.release();
         
     }   } 
     public void processing(){
      
         if(this.id==0){  
           
           Sub_kernel1();
      
         }else if(this.id==1){
             
             Sub_kernel2();
           
         }else if(this.id==2){
           
             Sub_kernel3();
              
         }
         System.out.println(getSchedular()+"\n");
        
     }

        @Override
        public void run() {
            
            processing();
            
        }
    }
public class MultiTasking {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
     
     ExecutorService executor= Executors.newFixedThreadPool(3);
   
     int i=0;
     while(i<3){
         executor.submit(new process(i));
         //Thread.currentThread().setPriority(1);
         i++;
        
         
     }
     executor.shutdown();
     
     
    
     
        
    }
    
}
