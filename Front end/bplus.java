package Bplus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Rutuja Rajesh
 */

class records{
static String name,fee,spec,phone,keyf;
}
class node{
int [] a=new int[5];
int size;
node[] next=new node[4];
node parent;
node(){
for(int i = 0; i < 4; i++)
next[i] = null;
parent = null;
size = 0;
}
}

class btree
{
node root=new node();
public node findLeaf(int key, int level)
	{
		node ptr = root;
		node prevptr = null;
		level = 0;
		int i;
		while (ptr != null)
		{
		 i = 0;
		  level++;
		 while (i < ptr.size-1 && key > ptr.a[i])
		 {
			i++;
		 }
		  prevptr = ptr;
		 ptr = ptr.next[i];
		}
		return prevptr;
	}

public void updateKey(node parent, node child, int newkey)
	{
		  if (parent == null)
		  {
		return;
		  }
		  if (parent.size == 0)
		  {
			return;
		  }
		  int oldkey = child.a[child.size-2];

		  for (int i = 0; i < parent.size;i++)
		  {
		  if (parent.a[i] == oldkey)
		  {
			 parent.a[i] = newkey;
			 parent.next[i] = child;
		  }
		  }
	}

public void search(int key)
	{
		   if (root == null)
		   {
			  System.out.print("The tree Does not exist");
			  System.out.print("\n");
		  return;
                   }
                   int level=0;
		  node leaf = findLeaf(key,level);
		  int flg = 0;
		  for (int i = 0; i < leaf.size; i++)
		  {
		  if (leaf.a[i] == key)
		  {
			  flg = 1;
			  System.out.print("The DOCTOR ID ");
			  System.out.print(key);
			  System.out.print(" Exists in the B-Tree at the level ");
			  System.out.print(level);
			  System.out.print("\n");
		  }
		  }
		  if (flg == 0)
		  {
		  System.out.print("The ID Searched for was not found");
		  System.out.print("\n");
		  }
	}

public void insert(int key)
	{
		if (root == null)
		{
		  root = new node();
		  root.a[root.size] = key;
		  root.size++;
                  return;
		}
                int level=0;
		node leaf = findLeaf(key,level);
		int i;
		for (i = 0; i < leaf.size; i++)
		{
		if (leaf.a[i] == key)
		{
			System.out.print("The Record to be inserted already exists");
			System.out.print("\n");
			return;
		}
		}
		promote(leaf,key,null);
		 System.out.print("---------------\n");
		  traverse(root);
		  System.out.print("----------------\n");
	}

void insertIntoNode(node n,int key,node address){
                int i;
                if( n == null)
                return;
                for(i = 0; i < n.size; i++)
                if(n.a[i] == key)
                return;
                    i = n.size-1;
     while(i >= 0 && n.a[i] > key)
                {
                n.a[i+1] = n.a[i];
                n.next[i+1] = n.next[i];
                i--;
                }
                i++;
                n.a[i] = key;
                n.next[i] = address;
                n.size++;
                if( i == n.size-1)
                updateKey(n.parent,n,key);
            }

void promote(node n,int key,node address){
                 if( n == null)
                 return;
                    if(n.size < 4)
                    {
                    insertIntoNode(n,key,address);
                    return;
                    }
                if( n == root)
                {
                root = new node();
                n.parent = root;
                }
                node newptr = split(n);
                node t;
                if(key < n.a[0])
                 t = newptr;
                else
                 t = n;
                insertIntoNode(t,key,address);
          promote(n.parent,n.a[n.size-1],n);
                promote(newptr.parent,newptr.a[newptr.size-1],newptr);
            }

node split(node n){
                int midpoint = (n.size+1)/2;
                int newsize = n.size - midpoint;
          node newptr = new node();
                node child;
                newptr.parent = n.parent;
                int i;
                for(i = 0; i < midpoint; i++)
                {
                newptr.a[i] = n.a[i];
                newptr.next[i] = n.next[i];
                n.a[i] = n.a[i+midpoint];
                n.next[i] = n.next[i+midpoint];
                }
                n.size = midpoint;
                newptr.size = newsize;
                for( i = 0; i < n.size; i++)
                {
                    child = n.next[i];
                if(child!= null)
                    child.parent = n;
                }
                for( i = 0; i < newptr.size; i++)
                {
                    child = newptr.next[i];
                if(child!= null)
                child.parent = newptr;
            }
    return newptr;
}

void traverse(node ptr){
                if(ptr == null)
                return;
                for(int i = 0; i < ptr.size; i++)
                    System.out.print(ptr.a[i]+" ");
                System.out.println();
                for(int i = 0; i < ptr.size;i++)
                    traverse(ptr.next[i]);
}
btree(){
root = null;
}
}


public class bplus {
static int countno() throws FileNotFoundException, IOException{
int no1;
no1=0;
try {
          File fle = new File("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt");
          if (fle.createNewFile()){
            System.out.println("File is created!");
            }else{
            System.out.println("File already exists.");
            }
          FileWriter fw = new FileWriter(fle,true);
} catch(IOException ioe){
	   System.out.println("Exception occurred:");
      }
BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt"));
String line;
while((line=reader.readLine())!=null)
{
no1++;
}
no1=no1-1;
reader.close();
return no1;
}

static String name,fee,spec,phone,count;
static records [] recs=new records[100];

    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String[] nodes=new String[100];
btree b = new btree();
int choice=1,key=1,no,rkey;
no=countno();
System.out.println("No. of records: "+no+"\n");
for(int i=0;i<no;i++)
recs[i]=new records();
BufferedReader bbr = new BufferedReader(new FileReader("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt"));
for(int i=0;i<no;i++)
{
String lineRead=bbr.readLine();
String [] st = lineRead.split("\\|");
recs[i].keyf=st[0];
recs[i].name=st[1];
recs[i].fee=st[2];
recs[i].spec=st[3];
recs[i].phone=st[4];

rkey=Integer.valueOf(recs[i].keyf);
b.insert(rkey);
}
bbr.close();

while(true)
{
      System.out.print("Welcome to DOCTOR RECORDS \n");
		System.out.print("1.Insert a record\n");
		System.out.print("2.Search for record details\n");
		System.out.print("3.display the ID values\n");
		System.out.print("4. exit \n");
Scanner sc=new Scanner(System.in);
choice=sc.nextInt();
switch(choice)
{
case 1:
System.out.print("Enter The DOCTOR ID: ");
key=sc.nextInt();
b.insert(key);
System.out.print("Enter the DOCTOR's name: ");
name=sc.next();
System.out.print("Enter the fees: ");
fee=sc.next();
System.out.print("Enter the spec: ");
spec=sc.next();
System.out.print("Enter phone: ");
phone=sc.next();

//TRY TO FIND HOW TO WRITE OBJECTS INTO A FILE
try {
FileWriter fw=new FileWriter("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt",true);
fw.write(""+key);
fw.write("|");
fw.write(name);
fw.write("|");
fw.write(fee);
fw.write("|");
fw.write(spec);
fw.write("|");
fw.write(phone);

BufferedWriter bw = new BufferedWriter(fw);
bw.newLine();
bw.close();
fw.close();
} 
catch(Exception e) 
{
    System.out.println(e);
}
no++;
break;
case 2: System.out.print("Enter The doctor ID to be searched: ");
key=sc.nextInt();

b.search(key);
bbr=new BufferedReader(new FileReader("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt"));
for(int i=0;i<no;i++)
{
String lineRead=bbr.readLine();
String [] t=lineRead.split("\\|");
recs[i].keyf=t[0];
recs[i].name=t[1];
recs[i].fee=t[2];
recs[i].spec=t[3];
recs[i].phone=t[4];

rkey=Integer.valueOf(recs[i].keyf);

if(key==rkey)
{
System.out.println("DOCTOR's Name: "+recs[i].name);
System.out.println("FEES: "+recs[i].fee);
System.out.println("SPEC: "+recs[i].spec);
System.out.println("phone no: "+recs[i].phone+"\n\n");

}
}
bbr.close();
break;
case 3: System.out.println("DOCTOR ID\tDOCTOR Name\tFEES\tSPECIALISATION\tPHONE\n");
bbr=new BufferedReader(new FileReader("C:\\Users\\Rajesh\\Documents\\NetBeansProjects\\fs\\bplus.txt")); 
for(int i=0;i<=no;i++)
{
String lineRead=bbr.readLine();
String [] t=lineRead.split("\\|");
recs[i].keyf=t[0];
recs[i].name=t[1];
recs[i].fee=t[2];
recs[i].spec=t[3];
recs[i].phone=t[4];

System.out.println(recs[i].keyf+"\t\t"+recs[i].name+"\t\t"+recs[i].fee+"\t\t"+recs[i].spec+"\t\t"+recs[i].phone+"\t\t\t");
}
bbr.close();
break;

case 4:return;
}
}

    }
    
}
